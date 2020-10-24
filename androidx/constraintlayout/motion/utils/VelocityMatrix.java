package androidx.constraintlayout.motion.utils;

import androidx.constraintlayout.motion.widget.KeyCycleOscillator;
import androidx.constraintlayout.motion.widget.SplineSet;

public class VelocityMatrix {
    private static String TAG = "VelocityMatrix";
    float mDRotate;
    float mDScaleX;
    float mDScaleY;
    float mDTranslateX;
    float mDTranslateY;
    float mRotate;

    public void clear() {
        this.mDRotate = 0.0f;
        this.mDTranslateY = 0.0f;
        this.mDTranslateX = 0.0f;
        this.mDScaleY = 0.0f;
        this.mDScaleX = 0.0f;
    }

    public void setRotationVelocity(SplineSet splineSet, float f) {
        if (splineSet != null) {
            this.mDRotate = splineSet.getSlope(f);
            this.mRotate = splineSet.get(f);
        }
    }

    public void setTranslationVelocity(SplineSet splineSet, SplineSet splineSet2, float f) {
        if (splineSet != null) {
            this.mDTranslateX = splineSet.getSlope(f);
        }
        if (splineSet2 != null) {
            this.mDTranslateY = splineSet2.getSlope(f);
        }
    }

    public void setScaleVelocity(SplineSet splineSet, SplineSet splineSet2, float f) {
        if (splineSet != null) {
            this.mDScaleX = splineSet.getSlope(f);
        }
        if (splineSet2 != null) {
            this.mDScaleY = splineSet2.getSlope(f);
        }
    }

    public void setRotationVelocity(KeyCycleOscillator keyCycleOscillator, float f) {
        if (keyCycleOscillator != null) {
            this.mDRotate = keyCycleOscillator.getSlope(f);
        }
    }

    public void setTranslationVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f) {
        if (keyCycleOscillator != null) {
            this.mDTranslateX = keyCycleOscillator.getSlope(f);
        }
        if (keyCycleOscillator2 != null) {
            this.mDTranslateY = keyCycleOscillator2.getSlope(f);
        }
    }

    public void setScaleVelocity(KeyCycleOscillator keyCycleOscillator, KeyCycleOscillator keyCycleOscillator2, float f) {
        if (keyCycleOscillator != null || keyCycleOscillator2 != null) {
            if (keyCycleOscillator == null) {
                this.mDScaleX = keyCycleOscillator.getSlope(f);
            }
            if (keyCycleOscillator2 == null) {
                this.mDScaleY = keyCycleOscillator2.getSlope(f);
            }
        }
    }

    public void applyTransform(float f, float f2, int i, int i2, float[] fArr) {
        int i3 = i;
        float f3 = fArr[0];
        float f4 = fArr[1];
        float f5 = (f - 0.5f) * 2.0f;
        float f6 = (f2 - 0.5f) * 2.0f;
        float f7 = f3 + this.mDTranslateX;
        float f8 = f4 + this.mDTranslateY;
        float f9 = f7 + (this.mDScaleX * f5);
        float f10 = f8 + (this.mDScaleY * f6);
        float radians = (float) Math.toRadians((double) this.mDRotate);
        double d = (double) (((float) (-i3)) * f5);
        double radians2 = (double) ((float) Math.toRadians((double) this.mRotate));
        double sin = Math.sin(radians2);
        Double.isNaN(d);
        double d2 = d * sin;
        double d3 = (double) (((float) i2) * f6);
        double cos = Math.cos(radians2);
        Double.isNaN(d3);
        float f11 = f9 + (((float) (d2 - (cos * d3))) * radians);
        double d4 = (double) (((float) i3) * f5);
        double cos2 = Math.cos(radians2);
        Double.isNaN(d4);
        double d5 = d4 * cos2;
        double sin2 = Math.sin(radians2);
        Double.isNaN(d3);
        fArr[0] = f11;
        fArr[1] = f10 + (radians * ((float) (d5 - (d3 * sin2))));
    }
}
