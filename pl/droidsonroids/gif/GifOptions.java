package pl.droidsonroids.gif;

public class GifOptions {
    boolean inIsOpaque;
    char inSampleSize;

    public GifOptions() {
        reset();
    }

    private void reset() {
        this.inSampleSize = 1;
        this.inIsOpaque = false;
    }

    public void setInSampleSize(int i) {
        if (i < 1 || i > 65535) {
            this.inSampleSize = 1;
        } else {
            this.inSampleSize = (char) i;
        }
    }

    public void setInIsOpaque(boolean z) {
        this.inIsOpaque = z;
    }

    /* access modifiers changed from: package-private */
    public void setFrom(GifOptions gifOptions) {
        if (gifOptions == null) {
            reset();
            return;
        }
        this.inIsOpaque = gifOptions.inIsOpaque;
        this.inSampleSize = gifOptions.inSampleSize;
    }
}
