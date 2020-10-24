package pl.droidsonroids.gif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import pl.droidsonroids.gif.GifViewUtils;

public class GifImageView extends ImageView {
    private boolean mFreezesAnimation;

    public GifImageView(Context context) {
        super(context);
    }

    public GifImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        postInit(GifViewUtils.initImageView(this, attributeSet, 0, 0));
    }

    public GifImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        postInit(GifViewUtils.initImageView(this, attributeSet, i, 0));
    }

    public GifImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        postInit(GifViewUtils.initImageView(this, attributeSet, i, i2));
    }

    private void postInit(GifViewUtils.InitResult initResult) {
        this.mFreezesAnimation = initResult.mFreezesAnimation;
        if (initResult.mSourceResId > 0) {
            super.setImageResource(initResult.mSourceResId);
        }
        if (initResult.mBackgroundResId > 0) {
            super.setBackgroundResource(initResult.mBackgroundResId);
        }
    }

    public void setImageURI(Uri uri) {
        if (!GifViewUtils.setGifImageUri(this, uri)) {
            super.setImageURI(uri);
        }
    }

    public void setImageResource(int i) {
        if (!GifViewUtils.setResource(this, true, i)) {
            super.setImageResource(i);
        }
    }

    public void setBackgroundResource(int i) {
        if (!GifViewUtils.setResource(this, false, i)) {
            super.setBackgroundResource(i);
        }
    }

    public Parcelable onSaveInstanceState() {
        Drawable drawable = null;
        Drawable drawable2 = this.mFreezesAnimation ? getDrawable() : null;
        if (this.mFreezesAnimation) {
            drawable = getBackground();
        }
        return new GifViewSavedState(super.onSaveInstanceState(), drawable2, drawable);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof GifViewSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        GifViewSavedState gifViewSavedState = (GifViewSavedState) parcelable;
        super.onRestoreInstanceState(gifViewSavedState.getSuperState());
        gifViewSavedState.restoreState(getDrawable(), 0);
        gifViewSavedState.restoreState(getBackground(), 1);
    }

    public void setFreezesAnimation(boolean z) {
        this.mFreezesAnimation = z;
    }
}
