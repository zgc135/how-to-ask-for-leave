package pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public abstract class InputSource {
    /* access modifiers changed from: package-private */
    public abstract GifInfoHandle open() throws IOException;

    private InputSource() {
    }

    /* access modifiers changed from: package-private */
    public final GifDrawable build(GifDrawable gifDrawable, ScheduledThreadPoolExecutor scheduledThreadPoolExecutor, boolean z, GifOptions gifOptions) throws IOException {
        return new GifDrawable(createHandleWith(gifOptions), gifDrawable, scheduledThreadPoolExecutor, z);
    }

    /* access modifiers changed from: package-private */
    public final GifInfoHandle createHandleWith(GifOptions gifOptions) throws IOException {
        GifInfoHandle open = open();
        open.setOptions(gifOptions.inSampleSize, gifOptions.inIsOpaque);
        return open;
    }

    public static final class DirectByteBufferSource extends InputSource {
        private final ByteBuffer byteBuffer;

        public DirectByteBufferSource(ByteBuffer byteBuffer2) {
            super();
            this.byteBuffer = byteBuffer2;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws GifIOException {
            return new GifInfoHandle(this.byteBuffer);
        }
    }

    public static final class ByteArraySource extends InputSource {
        private final byte[] bytes;

        public ByteArraySource(byte[] bArr) {
            super();
            this.bytes = bArr;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws GifIOException {
            return new GifInfoHandle(this.bytes);
        }
    }

    public static final class FileSource extends InputSource {
        private final String mPath;

        public FileSource(File file) {
            super();
            this.mPath = file.getPath();
        }

        public FileSource(String str) {
            super();
            this.mPath = str;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws GifIOException {
            return new GifInfoHandle(this.mPath);
        }
    }

    public static final class UriSource extends InputSource {
        private final ContentResolver mContentResolver;
        private final Uri mUri;

        public UriSource(ContentResolver contentResolver, Uri uri) {
            super();
            this.mContentResolver = contentResolver;
            this.mUri = uri;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws IOException {
            return GifInfoHandle.openUri(this.mContentResolver, this.mUri);
        }
    }

    public static final class AssetSource extends InputSource {
        private final AssetManager mAssetManager;
        private final String mAssetName;

        public AssetSource(AssetManager assetManager, String str) {
            super();
            this.mAssetManager = assetManager;
            this.mAssetName = str;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mAssetManager.openFd(this.mAssetName));
        }
    }

    public static final class FileDescriptorSource extends InputSource {
        private final FileDescriptor mFd;

        public FileDescriptorSource(FileDescriptor fileDescriptor) {
            super();
            this.mFd = fileDescriptor;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mFd);
        }
    }

    public static final class InputStreamSource extends InputSource {
        private final InputStream inputStream;

        public InputStreamSource(InputStream inputStream2) {
            super();
            this.inputStream = inputStream2;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.inputStream);
        }
    }

    public static class ResourcesSource extends InputSource {
        private final int mResourceId;
        private final Resources mResources;

        public ResourcesSource(Resources resources, int i) {
            super();
            this.mResources = resources;
            this.mResourceId = i;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mResources.openRawResourceFd(this.mResourceId));
        }
    }

    public static class AssetFileDescriptorSource extends InputSource {
        private final AssetFileDescriptor mAssetFileDescriptor;

        public AssetFileDescriptorSource(AssetFileDescriptor assetFileDescriptor) {
            super();
            this.mAssetFileDescriptor = assetFileDescriptor;
        }

        /* access modifiers changed from: package-private */
        public GifInfoHandle open() throws IOException {
            return new GifInfoHandle(this.mAssetFileDescriptor);
        }
    }
}
