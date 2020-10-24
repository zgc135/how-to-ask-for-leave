package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Surface;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

final class GifInfoHandle {
    private volatile long gifInfoPtr;

    private static native void bindSurface(long j, Surface surface, long[] jArr);

    private static native void free(long j);

    private static native long getAllocationByteCount(long j);

    private static native String getComment(long j);

    private static native int getCurrentFrameIndex(long j);

    private static native int getCurrentLoop(long j);

    private static native int getCurrentPosition(long j);

    private static native int getDuration(long j);

    private static native int getFrameDuration(long j, int i);

    private static native int getHeight(long j);

    private static native int getLoopCount(long j);

    private static native long getMetadataByteCount(long j);

    private static native int getNativeErrorCode(long j);

    private static native int getNumberOfFrames(long j);

    private static native long[] getSavedState(long j);

    private static native long getSourceLength(long j);

    private static native int getWidth(long j);

    private static native void glTexImage2D(long j, int i, int i2);

    private static native void glTexSubImage2D(long j, int i, int i2);

    private static native void initTexImageDescriptor(long j);

    private static native boolean isAnimationCompleted(long j);

    private static native boolean isOpaque(long j);

    static native long openByteArray(byte[] bArr) throws GifIOException;

    static native long openDirectByteBuffer(ByteBuffer byteBuffer) throws GifIOException;

    static native long openFd(FileDescriptor fileDescriptor, long j) throws GifIOException;

    static native long openFile(String str) throws GifIOException;

    static native long openStream(InputStream inputStream) throws GifIOException;

    private static native void postUnbindSurface(long j);

    private static native long renderFrame(long j, Bitmap bitmap);

    private static native boolean reset(long j);

    private static native long restoreRemainder(long j);

    private static native int restoreSavedState(long j, long[] jArr, Bitmap bitmap);

    private static native void saveRemainder(long j);

    private static native void seekToFrame(long j, int i, Bitmap bitmap);

    private static native void seekToFrameGL(long j, int i);

    private static native void seekToTime(long j, int i, Bitmap bitmap);

    private static native void setLoopCount(long j, char c);

    private static native void setOptions(long j, char c, boolean z);

    private static native void setSpeedFactor(long j, float f);

    private static native void startDecoderThread(long j);

    private static native void stopDecoderThread(long j);

    static {
        LibraryLoader.loadLibrary((Context) null);
    }

    GifInfoHandle() {
    }

    GifInfoHandle(FileDescriptor fileDescriptor) throws GifIOException {
        this.gifInfoPtr = openFd(fileDescriptor, 0);
    }

    GifInfoHandle(byte[] bArr) throws GifIOException {
        this.gifInfoPtr = openByteArray(bArr);
    }

    GifInfoHandle(ByteBuffer byteBuffer) throws GifIOException {
        this.gifInfoPtr = openDirectByteBuffer(byteBuffer);
    }

    GifInfoHandle(String str) throws GifIOException {
        this.gifInfoPtr = openFile(str);
    }

    GifInfoHandle(InputStream inputStream) throws GifIOException {
        if (inputStream.markSupported()) {
            this.gifInfoPtr = openStream(inputStream);
            return;
        }
        throw new IllegalArgumentException("InputStream does not support marking");
    }

    GifInfoHandle(AssetFileDescriptor assetFileDescriptor) throws IOException {
        try {
            this.gifInfoPtr = openFd(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset());
        } finally {
            try {
                assetFileDescriptor.close();
            } catch (IOException unused) {
            }
        }
    }

    static GifInfoHandle openUri(ContentResolver contentResolver, Uri uri) throws IOException {
        if ("file".equals(uri.getScheme())) {
            return new GifInfoHandle(uri.getPath());
        }
        return new GifInfoHandle(contentResolver.openAssetFileDescriptor(uri, "r"));
    }

    /* access modifiers changed from: package-private */
    public synchronized long renderFrame(Bitmap bitmap) {
        return renderFrame(this.gifInfoPtr, bitmap);
    }

    /* access modifiers changed from: package-private */
    public void bindSurface(Surface surface, long[] jArr) {
        bindSurface(this.gifInfoPtr, surface, jArr);
    }

    /* access modifiers changed from: package-private */
    public synchronized void recycle() {
        free(this.gifInfoPtr);
        this.gifInfoPtr = 0;
    }

    /* access modifiers changed from: package-private */
    public synchronized long restoreRemainder() {
        return restoreRemainder(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean reset() {
        return reset(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized void saveRemainder() {
        saveRemainder(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized String getComment() {
        return getComment(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getLoopCount() {
        return getLoopCount(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public void setLoopCount(int i) {
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException("Loop count of range <0, 65535>");
        }
        synchronized (this) {
            setLoopCount(this.gifInfoPtr, (char) i);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized long getSourceLength() {
        return getSourceLength(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getNativeErrorCode() {
        return getNativeErrorCode(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public void setSpeedFactor(float f) {
        if (f <= 0.0f || Float.isNaN(f)) {
            throw new IllegalArgumentException("Speed factor is not positive");
        }
        if (f < 4.656613E-10f) {
            f = 4.656613E-10f;
        }
        synchronized (this) {
            setSpeedFactor(this.gifInfoPtr, f);
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized int getDuration() {
        return getDuration(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getCurrentPosition() {
        return getCurrentPosition(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getCurrentFrameIndex() {
        return getCurrentFrameIndex(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getCurrentLoop() {
        return getCurrentLoop(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized void seekToTime(int i, Bitmap bitmap) {
        seekToTime(this.gifInfoPtr, i, bitmap);
    }

    /* access modifiers changed from: package-private */
    public synchronized void seekToFrame(int i, Bitmap bitmap) {
        seekToFrame(this.gifInfoPtr, i, bitmap);
    }

    /* access modifiers changed from: package-private */
    public synchronized long getAllocationByteCount() {
        return getAllocationByteCount(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized long getMetadataByteCount() {
        return getMetadataByteCount(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isRecycled() {
        return this.gifInfoPtr == 0;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }

    /* access modifiers changed from: package-private */
    public synchronized void postUnbindSurface() {
        postUnbindSurface(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isAnimationCompleted() {
        return isAnimationCompleted(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized long[] getSavedState() {
        return getSavedState(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int restoreSavedState(long[] jArr, Bitmap bitmap) {
        return restoreSavedState(this.gifInfoPtr, jArr, bitmap);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getFrameDuration(int i) {
        throwIfFrameIndexOutOfBounds(i);
        return getFrameDuration(this.gifInfoPtr, i);
    }

    /* access modifiers changed from: package-private */
    public void setOptions(char c, boolean z) {
        setOptions(this.gifInfoPtr, c, z);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getWidth() {
        return getWidth(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getHeight() {
        return getHeight(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized int getNumberOfFrames() {
        return getNumberOfFrames(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public synchronized boolean isOpaque() {
        return isOpaque(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public void glTexImage2D(int i, int i2) {
        glTexImage2D(this.gifInfoPtr, i, i2);
    }

    /* access modifiers changed from: package-private */
    public void glTexSubImage2D(int i, int i2) {
        glTexSubImage2D(this.gifInfoPtr, i, i2);
    }

    /* access modifiers changed from: package-private */
    public void startDecoderThread() {
        startDecoderThread(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public void stopDecoderThread() {
        stopDecoderThread(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public void initTexImageDescriptor() {
        initTexImageDescriptor(this.gifInfoPtr);
    }

    /* access modifiers changed from: package-private */
    public void seekToFrameGL(int i) {
        throwIfFrameIndexOutOfBounds(i);
        seekToFrameGL(this.gifInfoPtr, i);
    }

    private void throwIfFrameIndexOutOfBounds(int i) {
        float numberOfFrames = (float) getNumberOfFrames(this.gifInfoPtr);
        if (i < 0 || ((float) i) >= numberOfFrames) {
            throw new IndexOutOfBoundsException("Frame index is not in range <0;" + numberOfFrames + '>');
        }
    }
}
