package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.widget.MediaController;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import pl.droidsonroids.gif.transforms.CornerRadiusTransform;
import pl.droidsonroids.gif.transforms.Transform;

public class GifDrawable extends Drawable implements Animatable, MediaController.MediaPlayerControl {
    final Bitmap mBuffer;
    private final Rect mDstRect;
    final ScheduledThreadPoolExecutor mExecutor;
    final InvalidationHandler mInvalidationHandler;
    final boolean mIsRenderingTriggeredOnDraw;
    volatile boolean mIsRunning;
    final ConcurrentLinkedQueue<AnimationListener> mListeners;
    final GifInfoHandle mNativeInfoHandle;
    long mNextFrameRenderTime;
    protected final Paint mPaint;
    private final RenderTask mRenderTask;
    ScheduledFuture<?> mRenderTaskSchedule;
    private int mScaledHeight;
    private int mScaledWidth;
    private final Rect mSrcRect;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private PorterDuff.Mode mTintMode;
    private Transform mTransform;

    public boolean canPause() {
        return true;
    }

    public int getAudioSessionId() {
        return 0;
    }

    public int getBufferPercentage() {
        return 100;
    }

    public GifDrawable(Resources resources, int i) throws Resources.NotFoundException, IOException {
        this(resources.openRawResourceFd(i));
        float densityScale = GifViewUtils.getDensityScale(resources, i);
        this.mScaledHeight = (int) (((float) this.mNativeInfoHandle.getHeight()) * densityScale);
        this.mScaledWidth = (int) (((float) this.mNativeInfoHandle.getWidth()) * densityScale);
    }

    public GifDrawable(AssetManager assetManager, String str) throws IOException {
        this(assetManager.openFd(str));
    }

    public GifDrawable(String str) throws IOException {
        this(new GifInfoHandle(str), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(File file) throws IOException {
        this(file.getPath());
    }

    public GifDrawable(InputStream inputStream) throws IOException {
        this(new GifInfoHandle(inputStream), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(AssetFileDescriptor assetFileDescriptor) throws IOException {
        this(new GifInfoHandle(assetFileDescriptor), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(FileDescriptor fileDescriptor) throws IOException {
        this(new GifInfoHandle(fileDescriptor), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(byte[] bArr) throws IOException {
        this(new GifInfoHandle(bArr), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(ByteBuffer byteBuffer) throws IOException {
        this(new GifInfoHandle(byteBuffer), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(ContentResolver contentResolver, Uri uri) throws IOException {
        this(GifInfoHandle.openUri(contentResolver, uri), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    protected GifDrawable(InputSource inputSource, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z, GifOptions gifOptions) throws IOException {
        this(inputSource.createHandleWith(gifOptions), gifDrawable, scheduledThreadPoolExecutor, z);
    }

    GifDrawable(GifInfoHandle gifInfoHandle, GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z) {
        this.mIsRunning = true;
        this.mNextFrameRenderTime = Long.MIN_VALUE;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mListeners = new ConcurrentLinkedQueue<>();
        this.mRenderTask = new RenderTask(this);
        this.mIsRenderingTriggeredOnDraw = z;
        this.mExecutor = scheduledThreadPoolExecutor == null ? GifRenderingExecutor.getInstance() : scheduledThreadPoolExecutor;
        this.mNativeInfoHandle = gifInfoHandle;
        Bitmap bitmap = null;
        if (gifDrawable != null) {
            synchronized (gifDrawable.mNativeInfoHandle) {
                if (!gifDrawable.mNativeInfoHandle.isRecycled() && gifDrawable.mNativeInfoHandle.getHeight() >= this.mNativeInfoHandle.getHeight() && gifDrawable.mNativeInfoHandle.getWidth() >= this.mNativeInfoHandle.getWidth()) {
                    gifDrawable.shutdown();
                    Bitmap bitmap2 = gifDrawable.mBuffer;
                    bitmap2.eraseColor(0);
                    bitmap = bitmap2;
                }
            }
        }
        if (bitmap == null) {
            this.mBuffer = Bitmap.createBitmap(this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            this.mBuffer = bitmap;
        }
        if (Build.VERSION.SDK_INT >= 12) {
            this.mBuffer.setHasAlpha(!gifInfoHandle.isOpaque());
        }
        this.mSrcRect = new Rect(0, 0, this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight());
        this.mInvalidationHandler = new InvalidationHandler(this);
        this.mRenderTask.doWork();
        this.mScaledWidth = this.mNativeInfoHandle.getWidth();
        this.mScaledHeight = this.mNativeInfoHandle.getHeight();
    }

    public void recycle() {
        shutdown();
        this.mBuffer.recycle();
    }

    private void shutdown() {
        this.mIsRunning = false;
        this.mInvalidationHandler.removeMessages(-1);
        this.mNativeInfoHandle.recycle();
    }

    public boolean isRecycled() {
        return this.mNativeInfoHandle.isRecycled();
    }

    public int getIntrinsicHeight() {
        return this.mScaledHeight;
    }

    public int getIntrinsicWidth() {
        return this.mScaledWidth;
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return (!this.mNativeInfoHandle.isOpaque() || this.mPaint.getAlpha() < 255) ? -2 : -1;
    }

    public void start() {
        synchronized (this) {
            if (!this.mIsRunning) {
                this.mIsRunning = true;
                startAnimation(this.mNativeInfoHandle.restoreRemainder());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void startAnimation(long j) {
        if (this.mIsRenderingTriggeredOnDraw) {
            this.mNextFrameRenderTime = 0;
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return;
        }
        cancelPendingRenderTask();
        this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, Math.max(j, 0), TimeUnit.MILLISECONDS);
    }

    public void reset() {
        this.mExecutor.execute(new SafeRunnable(this) {
            public void doWork() {
                if (GifDrawable.this.mNativeInfoHandle.reset()) {
                    GifDrawable.this.start();
                }
            }
        });
    }

    public void stop() {
        synchronized (this) {
            if (this.mIsRunning) {
                this.mIsRunning = false;
                cancelPendingRenderTask();
                this.mNativeInfoHandle.saveRemainder();
            }
        }
    }

    private void cancelPendingRenderTask() {
        ScheduledFuture<?> scheduledFuture = this.mRenderTaskSchedule;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.mInvalidationHandler.removeMessages(-1);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public String getComment() {
        return this.mNativeInfoHandle.getComment();
    }

    public int getLoopCount() {
        return this.mNativeInfoHandle.getLoopCount();
    }

    public void setLoopCount(int i) {
        this.mNativeInfoHandle.setLoopCount(i);
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, error: %d", new Object[]{Integer.valueOf(this.mNativeInfoHandle.getWidth()), Integer.valueOf(this.mNativeInfoHandle.getHeight()), Integer.valueOf(this.mNativeInfoHandle.getNumberOfFrames()), Integer.valueOf(this.mNativeInfoHandle.getNativeErrorCode())});
    }

    public int getNumberOfFrames() {
        return this.mNativeInfoHandle.getNumberOfFrames();
    }

    public GifError getError() {
        return GifError.fromCode(this.mNativeInfoHandle.getNativeErrorCode());
    }

    public static GifDrawable createFromResource(Resources resources, int i) {
        try {
            return new GifDrawable(resources, i);
        } catch (IOException unused) {
            return null;
        }
    }

    public void setSpeed(float f) {
        this.mNativeInfoHandle.setSpeedFactor(f);
    }

    public void pause() {
        stop();
    }

    public int getDuration() {
        return this.mNativeInfoHandle.getDuration();
    }

    public int getCurrentPosition() {
        return this.mNativeInfoHandle.getCurrentPosition();
    }

    public void seekTo(final int i) {
        if (i >= 0) {
            this.mExecutor.execute(new SafeRunnable(this) {
                public void doWork() {
                    GifDrawable.this.mNativeInfoHandle.seekToTime(i, GifDrawable.this.mBuffer);
                    this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Position is not positive");
    }

    public void seekToFrame(final int i) {
        if (i >= 0) {
            this.mExecutor.execute(new SafeRunnable(this) {
                public void doWork() {
                    GifDrawable.this.mNativeInfoHandle.seekToFrame(i, GifDrawable.this.mBuffer);
                    GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
                }
            });
            return;
        }
        throw new IndexOutOfBoundsException("Frame index is not positive");
    }

    public Bitmap seekToFrameAndGet(int i) {
        Bitmap currentFrame;
        if (i >= 0) {
            synchronized (this.mNativeInfoHandle) {
                this.mNativeInfoHandle.seekToFrame(i, this.mBuffer);
                currentFrame = getCurrentFrame();
            }
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return currentFrame;
        }
        throw new IndexOutOfBoundsException("Frame index is not positive");
    }

    public Bitmap seekToPositionAndGet(int i) {
        Bitmap currentFrame;
        if (i >= 0) {
            synchronized (this.mNativeInfoHandle) {
                this.mNativeInfoHandle.seekToTime(i, this.mBuffer);
                currentFrame = getCurrentFrame();
            }
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return currentFrame;
        }
        throw new IllegalArgumentException("Position is not positive");
    }

    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    public int getFrameByteCount() {
        return this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
    }

    public long getAllocationByteCount() {
        int i;
        long allocationByteCount = this.mNativeInfoHandle.getAllocationByteCount();
        if (Build.VERSION.SDK_INT >= 19) {
            i = this.mBuffer.getAllocationByteCount();
        } else {
            i = getFrameByteCount();
        }
        return allocationByteCount + ((long) i);
    }

    public long getMetadataAllocationByteCount() {
        return this.mNativeInfoHandle.getMetadataByteCount();
    }

    public long getInputSourceByteCount() {
        return this.mNativeInfoHandle.getSourceLength();
    }

    public void getPixels(int[] iArr) {
        this.mBuffer.getPixels(iArr, 0, this.mNativeInfoHandle.getWidth(), 0, 0, this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight());
    }

    public int getPixel(int i, int i2) {
        if (i >= this.mNativeInfoHandle.getWidth()) {
            throw new IllegalArgumentException("x must be < width");
        } else if (i2 < this.mNativeInfoHandle.getHeight()) {
            return this.mBuffer.getPixel(i, i2);
        } else {
            throw new IllegalArgumentException("y must be < height");
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.mDstRect.set(rect);
        Transform transform = this.mTransform;
        if (transform != null) {
            transform.onBoundsChange(rect);
        }
    }

    public void draw(Canvas canvas) {
        boolean z;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            z = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            z = true;
        }
        Transform transform = this.mTransform;
        if (transform == null) {
            canvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
        } else {
            transform.onDraw(canvas, this.mPaint, this.mBuffer);
        }
        if (z) {
            this.mPaint.setColorFilter((ColorFilter) null);
        }
        if (this.mIsRenderingTriggeredOnDraw && this.mIsRunning) {
            long j = this.mNextFrameRenderTime;
            if (j != Long.MIN_VALUE) {
                long max = Math.max(0, j - SystemClock.uptimeMillis());
                this.mNextFrameRenderTime = Long.MIN_VALUE;
                this.mExecutor.remove(this.mRenderTask);
                this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, max, TimeUnit.MILLISECONDS);
            }
        }
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    @Deprecated
    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    public void addAnimationListener(AnimationListener animationListener) {
        this.mListeners.add(animationListener);
    }

    public boolean removeAnimationListener(AnimationListener animationListener) {
        return this.mListeners.remove(animationListener);
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public Bitmap getCurrentFrame() {
        Bitmap bitmap = this.mBuffer;
        Bitmap copy = bitmap.copy(bitmap.getConfig(), this.mBuffer.isMutable());
        if (Build.VERSION.SDK_INT >= 12) {
            copy.setHasAlpha(this.mBuffer.hasAlpha());
        }
        return copy;
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = updateTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    public void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = updateTintFilter(this.mTint, mode);
        invalidateSelf();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mTint;
        if (colorStateList == null || (mode = this.mTintMode) == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(colorStateList, mode);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.mTint;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            boolean r0 = super.isStateful()
            if (r0 != 0) goto L_0x0013
            android.content.res.ColorStateList r0 = r1.mTint
            if (r0 == 0) goto L_0x0011
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r0 = 0
            goto L_0x0014
        L_0x0013:
            r0 = 1
        L_0x0014:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: pl.droidsonroids.gif.GifDrawable.isStateful():boolean");
    }

    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!this.mIsRenderingTriggeredOnDraw) {
            if (z) {
                if (z2) {
                    reset();
                }
                if (visible) {
                    start();
                }
            } else if (visible) {
                stop();
            }
        }
        return visible;
    }

    public int getCurrentFrameIndex() {
        return this.mNativeInfoHandle.getCurrentFrameIndex();
    }

    public int getCurrentLoop() {
        int currentLoop = this.mNativeInfoHandle.getCurrentLoop();
        return (currentLoop == 0 || currentLoop < this.mNativeInfoHandle.getLoopCount()) ? currentLoop : currentLoop - 1;
    }

    public boolean isAnimationCompleted() {
        return this.mNativeInfoHandle.isAnimationCompleted();
    }

    public int getFrameDuration(int i) {
        return this.mNativeInfoHandle.getFrameDuration(i);
    }

    public void setCornerRadius(float f) {
        this.mTransform = new CornerRadiusTransform(f);
    }

    public float getCornerRadius() {
        Transform transform = this.mTransform;
        if (transform instanceof CornerRadiusTransform) {
            return ((CornerRadiusTransform) transform).getCornerRadius();
        }
        return 0.0f;
    }

    public void setTransform(Transform transform) {
        this.mTransform = transform;
    }

    public Transform getTransform() {
        return this.mTransform;
    }
}
