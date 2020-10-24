package pl.droidsonroids.gif.transforms;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

public class CornerRadiusTransform implements Transform {
    private float mCornerRadius;
    private final RectF mDstRectF = new RectF();
    private Shader mShader;

    public CornerRadiusTransform(float f) {
        setCornerRadius(f);
    }

    public void setCornerRadius(float f) {
        float max = Math.max(0.0f, f);
        if (max != this.mCornerRadius) {
            this.mCornerRadius = max;
            this.mShader = null;
        }
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public void onBoundsChange(Rect rect) {
        this.mDstRectF.set(rect);
        this.mShader = null;
    }

    public void onDraw(Canvas canvas, Paint paint, Bitmap bitmap) {
        if (this.mCornerRadius == 0.0f) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRectF, paint);
            return;
        }
        if (this.mShader == null) {
            this.mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            matrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
            matrix.preScale(this.mDstRectF.width() / ((float) bitmap.getWidth()), this.mDstRectF.height() / ((float) bitmap.getHeight()));
            this.mShader.setLocalMatrix(matrix);
        }
        paint.setShader(this.mShader);
        RectF rectF = this.mDstRectF;
        float f = this.mCornerRadius;
        canvas.drawRoundRect(rectF, f, f, paint);
    }
}
