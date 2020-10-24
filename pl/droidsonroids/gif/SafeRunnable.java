package pl.droidsonroids.gif;

import java.lang.Thread;

abstract class SafeRunnable implements Runnable {
    final GifDrawable mGifDrawable;

    /* access modifiers changed from: package-private */
    public abstract void doWork();

    SafeRunnable(GifDrawable gifDrawable) {
        this.mGifDrawable = gifDrawable;
    }

    public final void run() {
        try {
            if (!this.mGifDrawable.isRecycled()) {
                doWork();
            }
        } catch (Throwable th) {
            Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
            if (defaultUncaughtExceptionHandler != null) {
                defaultUncaughtExceptionHandler.uncaughtException(Thread.currentThread(), th);
            }
            throw th;
        }
    }
}
