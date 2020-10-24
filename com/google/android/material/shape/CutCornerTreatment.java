package com.google.android.material.shape;

public class CutCornerTreatment extends CornerTreatment {
    private final float size;

    public CutCornerTreatment(float f) {
        this.size = f;
    }

    public void getCornerPath(float f, float f2, ShapePath shapePath) {
        shapePath.reset(0.0f, this.size * f2);
        double d = (double) f;
        double sin = Math.sin(d);
        double d2 = (double) this.size;
        Double.isNaN(d2);
        double d3 = (double) f2;
        Double.isNaN(d3);
        double cos = Math.cos(d);
        double d4 = (double) this.size;
        Double.isNaN(d4);
        Double.isNaN(d3);
        shapePath.lineTo((float) (sin * d2 * d3), (float) (cos * d4 * d3));
    }
}
