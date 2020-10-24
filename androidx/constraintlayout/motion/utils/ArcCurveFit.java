package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

class ArcCurveFit extends CurveFit {
    public static final int ARC_START_FLIP = 3;
    public static final int ARC_START_HORIZONTAL = 2;
    public static final int ARC_START_LINEAR = 0;
    public static final int ARC_START_VERTICAL = 1;
    private static final int START_HORIZONTAL = 2;
    private static final int START_LINEAR = 3;
    private static final int START_VERTICAL = 1;
    Arc[] mArcs;
    private final double[] mTime;

    public void getPos(double d, double[] dArr) {
        if (d < this.mArcs[0].mTime1) {
            d = this.mArcs[0].mTime1;
        }
        Arc[] arcArr = this.mArcs;
        if (d > arcArr[arcArr.length - 1].mTime2) {
            Arc[] arcArr2 = this.mArcs;
            d = arcArr2[arcArr2.length - 1].mTime2;
        }
        int i = 0;
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i >= arcArr3.length) {
                return;
            }
            if (d > arcArr3[i].mTime2) {
                i++;
            } else if (this.mArcs[i].linear) {
                dArr[0] = this.mArcs[i].getLinearX(d);
                dArr[1] = this.mArcs[i].getLinearY(d);
                return;
            } else {
                this.mArcs[i].setPoint(d);
                dArr[0] = this.mArcs[i].getX();
                dArr[1] = this.mArcs[i].getY();
                return;
            }
        }
    }

    public void getPos(double d, float[] fArr) {
        if (d < this.mArcs[0].mTime1) {
            d = this.mArcs[0].mTime1;
        } else {
            Arc[] arcArr = this.mArcs;
            if (d > arcArr[arcArr.length - 1].mTime2) {
                Arc[] arcArr2 = this.mArcs;
                d = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        int i = 0;
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i >= arcArr3.length) {
                return;
            }
            if (d > arcArr3[i].mTime2) {
                i++;
            } else if (this.mArcs[i].linear) {
                fArr[0] = (float) this.mArcs[i].getLinearX(d);
                fArr[1] = (float) this.mArcs[i].getLinearY(d);
                return;
            } else {
                this.mArcs[i].setPoint(d);
                fArr[0] = (float) this.mArcs[i].getX();
                fArr[1] = (float) this.mArcs[i].getY();
                return;
            }
        }
    }

    public void getSlope(double d, double[] dArr) {
        if (d < this.mArcs[0].mTime1) {
            d = this.mArcs[0].mTime1;
        } else {
            Arc[] arcArr = this.mArcs;
            if (d > arcArr[arcArr.length - 1].mTime2) {
                Arc[] arcArr2 = this.mArcs;
                d = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        int i = 0;
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i >= arcArr3.length) {
                return;
            }
            if (d > arcArr3[i].mTime2) {
                i++;
            } else if (this.mArcs[i].linear) {
                dArr[0] = this.mArcs[i].getLinearDX(d);
                dArr[1] = this.mArcs[i].getLinearDY(d);
                return;
            } else {
                this.mArcs[i].setPoint(d);
                dArr[0] = this.mArcs[i].getDX();
                dArr[1] = this.mArcs[i].getDY();
                return;
            }
        }
    }

    public double getPos(double d, int i) {
        int i2 = 0;
        if (d < this.mArcs[0].mTime1) {
            d = this.mArcs[0].mTime1;
        } else {
            Arc[] arcArr = this.mArcs;
            if (d > arcArr[arcArr.length - 1].mTime2) {
                Arc[] arcArr2 = this.mArcs;
                d = arcArr2[arcArr2.length - 1].mTime2;
            }
        }
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i2 >= arcArr3.length) {
                return Double.NaN;
            }
            if (d > arcArr3[i2].mTime2) {
                i2++;
            } else if (!this.mArcs[i2].linear) {
                this.mArcs[i2].setPoint(d);
                if (i == 0) {
                    return this.mArcs[i2].getX();
                }
                return this.mArcs[i2].getY();
            } else if (i == 0) {
                return this.mArcs[i2].getLinearX(d);
            } else {
                return this.mArcs[i2].getLinearY(d);
            }
        }
    }

    public double getSlope(double d, int i) {
        int i2 = 0;
        if (d < this.mArcs[0].mTime1) {
            d = this.mArcs[0].mTime1;
        }
        Arc[] arcArr = this.mArcs;
        if (d > arcArr[arcArr.length - 1].mTime2) {
            Arc[] arcArr2 = this.mArcs;
            d = arcArr2[arcArr2.length - 1].mTime2;
        }
        while (true) {
            Arc[] arcArr3 = this.mArcs;
            if (i2 >= arcArr3.length) {
                return Double.NaN;
            }
            if (d > arcArr3[i2].mTime2) {
                i2++;
            } else if (!this.mArcs[i2].linear) {
                this.mArcs[i2].setPoint(d);
                if (i == 0) {
                    return this.mArcs[i2].getDX();
                }
                return this.mArcs[i2].getDY();
            } else if (i == 0) {
                return this.mArcs[i2].getLinearDX(d);
            } else {
                return this.mArcs[i2].getLinearDY(d);
            }
        }
    }

    public double[] getTimePoints() {
        return this.mTime;
    }

    public ArcCurveFit(int[] iArr, double[] dArr, double[][] dArr2) {
        int i;
        double[] dArr3 = dArr;
        this.mTime = dArr3;
        this.mArcs = new Arc[(dArr3.length - 1)];
        int i2 = 0;
        int i3 = 1;
        int i4 = 1;
        while (i2 < this.mArcs.length) {
            int i5 = iArr[i2];
            if (i5 == 0) {
                i = 3;
            } else if (i5 == 1) {
                i3 = 1;
                i = 1;
            } else if (i5 == 2) {
                i3 = 2;
                i = 2;
            } else if (i5 != 3) {
                i = i4;
            } else {
                i3 = i3 == 1 ? 2 : 1;
                i = i3;
            }
            int i6 = i2 + 1;
            this.mArcs[i2] = new Arc(i, dArr3[i2], dArr3[i6], dArr2[i2][0], dArr2[i2][1], dArr2[i6][0], dArr2[i6][1]);
            i4 = i;
            i2 = i6;
        }
    }

    private static class Arc {
        private static final double EPSILON = 0.001d;
        private static final String TAG = "Arc";
        private static double[] ourPercent = new double[91];
        boolean linear = false;
        double mArcDistance;
        double mArcVelocity;
        double mEllipseA;
        double mEllipseB;
        double mEllipseCenterX;
        double mEllipseCenterY;
        double[] mLut;
        double mOneOverDeltaTime;
        double mTime1;
        double mTime2;
        double mTmpCosAngle;
        double mTmpSinAngle;
        boolean mVertical;
        double mX1;
        double mX2;
        double mY1;
        double mY2;

        Arc(int i, double d, double d2, double d3, double d4, double d5, double d6) {
            int i2 = i;
            double d7 = d;
            double d8 = d2;
            double d9 = d3;
            double d10 = d4;
            double d11 = d5;
            double d12 = d6;
            boolean z = false;
            this.mVertical = i2 == 1 ? true : z;
            this.mTime1 = d7;
            this.mTime2 = d8;
            this.mOneOverDeltaTime = 1.0d / (d8 - d7);
            if (3 == i2) {
                this.linear = true;
            }
            double d13 = d11 - d9;
            double d14 = d12 - d10;
            if (this.linear || Math.abs(d13) < EPSILON || Math.abs(d14) < EPSILON) {
                this.linear = true;
                this.mX1 = d9;
                this.mX2 = d11;
                this.mY1 = d10;
                this.mY2 = d12;
                double hypot = Math.hypot(d14, d13);
                this.mArcDistance = hypot;
                this.mArcVelocity = hypot * this.mOneOverDeltaTime;
                double d15 = this.mTime2;
                double d16 = this.mTime1;
                this.mEllipseCenterX = d13 / (d15 - d16);
                this.mEllipseCenterY = d14 / (d15 - d16);
                return;
            }
            this.mLut = new double[101];
            double d17 = (double) (this.mVertical ? -1 : 1);
            Double.isNaN(d17);
            this.mEllipseA = d13 * d17;
            double d18 = (double) (this.mVertical ? 1 : -1);
            Double.isNaN(d18);
            this.mEllipseB = d14 * d18;
            this.mEllipseCenterX = this.mVertical ? d11 : d9;
            this.mEllipseCenterY = this.mVertical ? d10 : d12;
            buildTable(d3, d4, d5, d6);
            this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
        }

        /* access modifiers changed from: package-private */
        public void setPoint(double d) {
            double lookup = lookup((this.mVertical ? this.mTime2 - d : d - this.mTime1) * this.mOneOverDeltaTime) * 1.5707963267948966d;
            this.mTmpSinAngle = Math.sin(lookup);
            this.mTmpCosAngle = Math.cos(lookup);
        }

        /* access modifiers changed from: package-private */
        public double getX() {
            return this.mEllipseCenterX + (this.mEllipseA * this.mTmpSinAngle);
        }

        /* access modifiers changed from: package-private */
        public double getY() {
            return this.mEllipseCenterY + (this.mEllipseB * this.mTmpCosAngle);
        }

        /* access modifiers changed from: package-private */
        public double getDX() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, (-this.mEllipseB) * this.mTmpSinAngle);
            if (this.mVertical) {
                d = -d;
            }
            return d * hypot;
        }

        /* access modifiers changed from: package-private */
        public double getDY() {
            double d = this.mEllipseA * this.mTmpCosAngle;
            double d2 = (-this.mEllipseB) * this.mTmpSinAngle;
            double hypot = this.mArcVelocity / Math.hypot(d, d2);
            return this.mVertical ? (-d2) * hypot : d2 * hypot;
        }

        public double getLinearX(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mX1;
            return d3 + (d2 * (this.mX2 - d3));
        }

        public double getLinearY(double d) {
            double d2 = (d - this.mTime1) * this.mOneOverDeltaTime;
            double d3 = this.mY1;
            return d3 + (d2 * (this.mY2 - d3));
        }

        public double getLinearDX(double d) {
            return this.mEllipseCenterX;
        }

        public double getLinearDY(double d) {
            return this.mEllipseCenterY;
        }

        /* access modifiers changed from: package-private */
        public double lookup(double d) {
            if (d <= 0.0d) {
                return 0.0d;
            }
            if (d >= 1.0d) {
                return 1.0d;
            }
            double[] dArr = this.mLut;
            double length = (double) (dArr.length - 1);
            Double.isNaN(length);
            double d2 = d * length;
            int i = (int) d2;
            double d3 = (double) i;
            Double.isNaN(d3);
            return dArr[i] + ((d2 - d3) * (dArr[i + 1] - dArr[i]));
        }

        private void buildTable(double d, double d2, double d3, double d4) {
            double d5;
            double d6 = d3 - d;
            double d7 = d2 - d4;
            int i = 0;
            double d8 = 0.0d;
            double d9 = 0.0d;
            double d10 = 0.0d;
            while (true) {
                double[] dArr = ourPercent;
                if (i >= dArr.length) {
                    break;
                }
                double d11 = (double) i;
                Double.isNaN(d11);
                double d12 = d8;
                double length = (double) (dArr.length - 1);
                Double.isNaN(length);
                double radians = Math.toRadians((d11 * 90.0d) / length);
                double sin = Math.sin(radians) * d6;
                double cos = Math.cos(radians) * d7;
                if (i > 0) {
                    d5 = d12 + Math.hypot(sin - d9, cos - d10);
                    ourPercent[i] = d5;
                } else {
                    d5 = d12;
                }
                i++;
                d10 = cos;
                double d13 = sin;
                d8 = d5;
                d9 = d13;
            }
            double d14 = d8;
            this.mArcDistance = d14;
            int i2 = 0;
            while (true) {
                double[] dArr2 = ourPercent;
                if (i2 >= dArr2.length) {
                    break;
                }
                dArr2[i2] = dArr2[i2] / d14;
                i2++;
            }
            int i3 = 0;
            while (true) {
                double[] dArr3 = this.mLut;
                if (i3 < dArr3.length) {
                    double d15 = (double) i3;
                    double length2 = (double) (dArr3.length - 1);
                    Double.isNaN(d15);
                    Double.isNaN(length2);
                    double d16 = d15 / length2;
                    int binarySearch = Arrays.binarySearch(ourPercent, d16);
                    if (binarySearch >= 0) {
                        this.mLut[i3] = (double) (binarySearch / (ourPercent.length - 1));
                    } else if (binarySearch == -1) {
                        this.mLut[i3] = 0.0d;
                    } else {
                        int i4 = -binarySearch;
                        int i5 = i4 - 2;
                        double d17 = (double) i5;
                        double[] dArr4 = ourPercent;
                        Double.isNaN(d17);
                        double length3 = (double) (dArr4.length - 1);
                        Double.isNaN(length3);
                        this.mLut[i3] = (d17 + ((d16 - dArr4[i5]) / (dArr4[i4 - 1] - dArr4[i5]))) / length3;
                    }
                    i3++;
                } else {
                    return;
                }
            }
        }
    }
}
