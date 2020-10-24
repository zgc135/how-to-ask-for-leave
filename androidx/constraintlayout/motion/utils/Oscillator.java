package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

public class Oscillator {
    public static final int BOUNCE = 6;
    public static final int COS_WAVE = 5;
    public static final int REVERSE_SAW_WAVE = 4;
    public static final int SAW_WAVE = 3;
    public static final int SIN_WAVE = 0;
    public static final int SQUARE_WAVE = 1;
    public static String TAG = "Oscillator";
    public static final int TRIANGLE_WAVE = 2;
    double PI2 = 6.283185307179586d;
    double[] mArea;
    private boolean mNormalized = false;
    float[] mPeriod = new float[0];
    double[] mPosition = new double[0];
    int mType;

    public String toString() {
        return "pos =" + Arrays.toString(this.mPosition) + " period=" + Arrays.toString(this.mPeriod);
    }

    public void setType(int i) {
        this.mType = i;
    }

    public void addPoint(double d, float f) {
        int length = this.mPeriod.length + 1;
        int binarySearch = Arrays.binarySearch(this.mPosition, d);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 1;
        }
        this.mPosition = Arrays.copyOf(this.mPosition, length);
        this.mPeriod = Arrays.copyOf(this.mPeriod, length);
        this.mArea = new double[length];
        double[] dArr = this.mPosition;
        System.arraycopy(dArr, binarySearch, dArr, binarySearch + 1, (length - binarySearch) - 1);
        this.mPosition[binarySearch] = d;
        this.mPeriod[binarySearch] = f;
        this.mNormalized = false;
    }

    public void normalize() {
        double d = 0.0d;
        int i = 0;
        while (true) {
            float[] fArr = this.mPeriod;
            if (i >= fArr.length) {
                break;
            }
            double d2 = (double) fArr[i];
            Double.isNaN(d2);
            d += d2;
            i++;
        }
        double d3 = 0.0d;
        int i2 = 1;
        while (true) {
            float[] fArr2 = this.mPeriod;
            if (i2 >= fArr2.length) {
                break;
            }
            int i3 = i2 - 1;
            double[] dArr = this.mPosition;
            double d4 = (double) ((fArr2[i3] + fArr2[i2]) / 2.0f);
            Double.isNaN(d4);
            d3 += (dArr[i2] - dArr[i3]) * d4;
            i2++;
        }
        int i4 = 0;
        while (true) {
            float[] fArr3 = this.mPeriod;
            if (i4 >= fArr3.length) {
                break;
            }
            double d5 = (double) fArr3[i4];
            Double.isNaN(d5);
            fArr3[i4] = (float) (d5 * (d / d3));
            i4++;
        }
        this.mArea[0] = 0.0d;
        int i5 = 1;
        while (true) {
            float[] fArr4 = this.mPeriod;
            if (i5 < fArr4.length) {
                int i6 = i5 - 1;
                double[] dArr2 = this.mPosition;
                double d6 = dArr2[i5] - dArr2[i6];
                double[] dArr3 = this.mArea;
                double d7 = dArr3[i6];
                double d8 = (double) ((fArr4[i6] + fArr4[i5]) / 2.0f);
                Double.isNaN(d8);
                dArr3[i5] = d7 + (d6 * d8);
                i5++;
            } else {
                this.mNormalized = true;
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public double getP(double d) {
        if (d < 0.0d) {
            d = 0.0d;
        } else if (d > 1.0d) {
            d = 1.0d;
        }
        int binarySearch = Arrays.binarySearch(this.mPosition, d);
        if (binarySearch > 0) {
            return 1.0d;
        }
        if (binarySearch == 0) {
            return 0.0d;
        }
        int i = (-binarySearch) - 1;
        float[] fArr = this.mPeriod;
        int i2 = i - 1;
        double d2 = (double) (fArr[i] - fArr[i2]);
        double[] dArr = this.mPosition;
        Double.isNaN(d2);
        double d3 = d2 / (dArr[i] - dArr[i2]);
        double d4 = this.mArea[i2];
        double d5 = (double) fArr[i2];
        Double.isNaN(d5);
        return d4 + ((d5 - (dArr[i2] * d3)) * (d - dArr[i2])) + ((d3 * ((d * d) - (dArr[i2] * dArr[i2]))) / 2.0d);
    }

    public double getValue(double d) {
        double abs;
        switch (this.mType) {
            case 1:
                return Math.signum(0.5d - (getP(d) % 1.0d));
            case 2:
                abs = Math.abs((((getP(d) * 4.0d) + 1.0d) % 4.0d) - 2.0d);
                break;
            case 3:
                return (((getP(d) * 2.0d) + 1.0d) % 2.0d) - 1.0d;
            case 4:
                abs = ((getP(d) * 2.0d) + 1.0d) % 2.0d;
                break;
            case 5:
                return Math.cos(this.PI2 * getP(d));
            case 6:
                double abs2 = 1.0d - Math.abs(((getP(d) * 4.0d) % 4.0d) - 2.0d);
                abs = abs2 * abs2;
                break;
            default:
                return Math.sin(this.PI2 * getP(d));
        }
        return 1.0d - abs;
    }

    /* access modifiers changed from: package-private */
    public double getDP(double d) {
        if (d <= 0.0d) {
            d = 1.0E-5d;
        } else if (d >= 1.0d) {
            d = 0.999999d;
        }
        int binarySearch = Arrays.binarySearch(this.mPosition, d);
        if (binarySearch > 0 || binarySearch == 0) {
            return 0.0d;
        }
        int i = (-binarySearch) - 1;
        float[] fArr = this.mPeriod;
        int i2 = i - 1;
        double d2 = (double) (fArr[i] - fArr[i2]);
        double[] dArr = this.mPosition;
        Double.isNaN(d2);
        double d3 = d2 / (dArr[i] - dArr[i2]);
        double d4 = d * d3;
        double d5 = (double) fArr[i2];
        Double.isNaN(d5);
        return (d5 - (d3 * dArr[i2])) + d4;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x004f, code lost:
        return r10 * 2.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x001f, code lost:
        return r0 * r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0031, code lost:
        return r5 * r10;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public double getSlope(double r10) {
        /*
            r9 = this;
            int r0 = r9.mType
            r1 = 4611686018427387904(0x4000000000000000, double:2.0)
            r3 = 4616189618054758400(0x4010000000000000, double:4.0)
            switch(r0) {
                case 1: goto L_0x006b;
                case 2: goto L_0x0055;
                case 3: goto L_0x0050;
                case 4: goto L_0x0048;
                case 5: goto L_0x0032;
                case 6: goto L_0x0020;
                default: goto L_0x0009;
            }
        L_0x0009:
            double r0 = r9.PI2
            double r2 = r9.getDP(r10)
            double r0 = r0 * r2
            double r2 = r9.PI2
            double r10 = r9.getP(r10)
            double r2 = r2 * r10
            double r10 = java.lang.Math.cos(r2)
        L_0x001d:
            double r0 = r0 * r10
            return r0
        L_0x0020:
            double r5 = r9.getDP(r10)
            double r5 = r5 * r3
            double r10 = r9.getP(r10)
            double r10 = r10 * r3
            double r10 = r10 + r1
            double r10 = r10 % r3
            double r10 = r10 - r1
        L_0x002f:
            double r5 = r5 * r10
            return r5
        L_0x0032:
            double r0 = r9.PI2
            double r0 = -r0
            double r2 = r9.getDP(r10)
            double r0 = r0 * r2
            double r2 = r9.PI2
            double r10 = r9.getP(r10)
            double r2 = r2 * r10
            double r10 = java.lang.Math.sin(r2)
            goto L_0x001d
        L_0x0048:
            double r10 = r9.getDP(r10)
            double r10 = -r10
        L_0x004d:
            double r10 = r10 * r1
            return r10
        L_0x0050:
            double r10 = r9.getDP(r10)
            goto L_0x004d
        L_0x0055:
            double r5 = r9.getDP(r10)
            double r5 = r5 * r3
            double r10 = r9.getP(r10)
            double r10 = r10 * r3
            r7 = 4613937818241073152(0x4008000000000000, double:3.0)
            double r10 = r10 + r7
            double r10 = r10 % r3
            double r10 = r10 - r1
            double r10 = java.lang.Math.signum(r10)
            goto L_0x002f
        L_0x006b:
            r10 = 0
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.utils.Oscillator.getSlope(double):double");
    }
}
