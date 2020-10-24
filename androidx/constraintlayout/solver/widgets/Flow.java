package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;

public class Flow extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private int[] mAlignedDimensions = null;
    private ArrayList<WidgetsList> mChainList = new ArrayList<>();
    /* access modifiers changed from: private */
    public ConstraintWidget[] mDisplayedWidgets;
    /* access modifiers changed from: private */
    public int mDisplayedWidgetsCount = 0;
    /* access modifiers changed from: private */
    public float mFirstHorizontalBias = 0.5f;
    /* access modifiers changed from: private */
    public int mFirstHorizontalStyle = -1;
    /* access modifiers changed from: private */
    public float mFirstVerticalBias = 0.5f;
    /* access modifiers changed from: private */
    public int mFirstVerticalStyle = -1;
    /* access modifiers changed from: private */
    public int mHorizontalAlign = 2;
    /* access modifiers changed from: private */
    public float mHorizontalBias = 0.5f;
    /* access modifiers changed from: private */
    public int mHorizontalGap = 0;
    /* access modifiers changed from: private */
    public int mHorizontalStyle = -1;
    /* access modifiers changed from: private */
    public float mLastHorizontalBias = 0.5f;
    /* access modifiers changed from: private */
    public int mLastHorizontalStyle = -1;
    /* access modifiers changed from: private */
    public float mLastVerticalBias = 0.5f;
    /* access modifiers changed from: private */
    public int mLastVerticalStyle = -1;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    /* access modifiers changed from: private */
    public int mVerticalAlign = 2;
    /* access modifiers changed from: private */
    public float mVerticalBias = 0.5f;
    /* access modifiers changed from: private */
    public int mVerticalGap = 0;
    /* access modifiers changed from: private */
    public int mVerticalStyle = -1;
    private int mWrapMode = 0;

    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        Flow flow = (Flow) constraintWidget;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setFirstHorizontalStyle(int i) {
        this.mFirstHorizontalStyle = i;
    }

    public void setFirstVerticalStyle(int i) {
        this.mFirstVerticalStyle = i;
    }

    public void setLastHorizontalStyle(int i) {
        this.mLastHorizontalStyle = i;
    }

    public void setLastVerticalStyle(int i) {
        this.mLastVerticalStyle = i;
    }

    public void setHorizontalStyle(int i) {
        this.mHorizontalStyle = i;
    }

    public void setVerticalStyle(int i) {
        this.mVerticalStyle = i;
    }

    public void setHorizontalBias(float f) {
        this.mHorizontalBias = f;
    }

    public void setVerticalBias(float f) {
        this.mVerticalBias = f;
    }

    public void setFirstHorizontalBias(float f) {
        this.mFirstHorizontalBias = f;
    }

    public void setFirstVerticalBias(float f) {
        this.mFirstVerticalBias = f;
    }

    public void setLastHorizontalBias(float f) {
        this.mLastHorizontalBias = f;
    }

    public void setLastVerticalBias(float f) {
        this.mLastVerticalBias = f;
    }

    public void setHorizontalAlign(int i) {
        this.mHorizontalAlign = i;
    }

    public void setVerticalAlign(int i) {
        this.mVerticalAlign = i;
    }

    public void setWrapMode(int i) {
        this.mWrapMode = i;
    }

    public void setHorizontalGap(int i) {
        this.mHorizontalGap = i;
    }

    public void setVerticalGap(int i) {
        this.mVerticalGap = i;
    }

    public void setMaxElementsWrap(int i) {
        this.mMaxElementsWrap = i;
    }

    /* access modifiers changed from: private */
    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                return 0;
            }
            if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
                int i2 = (int) (constraintWidget.mMatchConstraintPercentWidth * ((float) i));
                if (i2 != constraintWidget.getWidth()) {
                    constraintWidget.setMeasureRequested(true);
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i2, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                }
                return i2;
            } else if (constraintWidget.mMatchConstraintDefaultWidth == 1) {
                return constraintWidget.getWidth();
            } else {
                if (constraintWidget.mMatchConstraintDefaultWidth == 3) {
                    return (int) ((((float) constraintWidget.getHeight()) * constraintWidget.mDimensionRatio) + 0.5f);
                }
            }
        }
        return constraintWidget.getWidth();
    }

    /* access modifiers changed from: private */
    public final int getWidgetHeight(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                return 0;
            }
            if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
                int i2 = (int) (constraintWidget.mMatchConstraintPercentHeight * ((float) i));
                if (i2 != constraintWidget.getHeight()) {
                    constraintWidget.setMeasureRequested(true);
                    measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i2);
                }
                return i2;
            } else if (constraintWidget.mMatchConstraintDefaultHeight == 1) {
                return constraintWidget.getHeight();
            } else {
                if (constraintWidget.mMatchConstraintDefaultHeight == 3) {
                    return (int) ((((float) constraintWidget.getWidth()) * constraintWidget.mDimensionRatio) + 0.5f);
                }
            }
        }
        return constraintWidget.getHeight();
    }

    /* JADX WARNING: type inference failed for: r11v2 */
    /* JADX WARNING: type inference failed for: r11v9 */
    /* JADX WARNING: type inference failed for: r11v17 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00ef  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0108  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measure(int r19, int r20, int r21, int r22) {
        /*
            r18 = this;
            r6 = r18
            r7 = r19
            r8 = r20
            r9 = r21
            r10 = r22
            int r0 = r6.mWidgetsCount
            r11 = 0
            if (r0 <= 0) goto L_0x001c
            boolean r0 = r18.measureChildren()
            if (r0 != 0) goto L_0x001c
            r6.setMeasure(r11, r11)
            r6.needsCallbackFromSolver(r11)
            return
        L_0x001c:
            int r12 = r18.getPaddingLeft()
            int r13 = r18.getPaddingRight()
            int r14 = r18.getPaddingTop()
            int r15 = r18.getPaddingBottom()
            r0 = 2
            int[] r5 = new int[r0]
            int r1 = r8 - r12
            int r1 = r1 - r13
            int r2 = r6.mOrientation
            r4 = 1
            if (r2 != r4) goto L_0x003a
            int r1 = r10 - r14
            int r1 = r1 - r15
        L_0x003a:
            r16 = r1
            int r1 = r6.mOrientation
            r2 = -1
            if (r1 != 0) goto L_0x004e
            int r1 = r6.mHorizontalStyle
            if (r1 != r2) goto L_0x0047
            r6.mHorizontalStyle = r11
        L_0x0047:
            int r1 = r6.mVerticalStyle
            if (r1 != r2) goto L_0x005a
            r6.mVerticalStyle = r11
            goto L_0x005a
        L_0x004e:
            int r1 = r6.mHorizontalStyle
            if (r1 != r2) goto L_0x0054
            r6.mHorizontalStyle = r11
        L_0x0054:
            int r1 = r6.mVerticalStyle
            if (r1 != r2) goto L_0x005a
            r6.mVerticalStyle = r11
        L_0x005a:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r6.mWidgets
            r2 = 0
            r3 = 0
        L_0x005e:
            int r11 = r6.mWidgetsCount
            r0 = 8
            if (r2 >= r11) goto L_0x0074
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r11 = r6.mWidgets
            r11 = r11[r2]
            int r11 = r11.getVisibility()
            if (r11 != r0) goto L_0x0070
            int r3 = r3 + 1
        L_0x0070:
            int r2 = r2 + 1
            r0 = 2
            goto L_0x005e
        L_0x0074:
            int r2 = r6.mWidgetsCount
            if (r3 <= 0) goto L_0x0096
            int r1 = r6.mWidgetsCount
            int r1 = r1 - r3
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = new androidx.constraintlayout.solver.widgets.ConstraintWidget[r1]
            r2 = 0
            r3 = 0
        L_0x007f:
            int r11 = r6.mWidgetsCount
            if (r2 >= r11) goto L_0x0095
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r11 = r6.mWidgets
            r11 = r11[r2]
            int r4 = r11.getVisibility()
            if (r4 == r0) goto L_0x0091
            r1[r3] = r11
            int r3 = r3 + 1
        L_0x0091:
            int r2 = r2 + 1
            r4 = 1
            goto L_0x007f
        L_0x0095:
            r2 = r3
        L_0x0096:
            r6.mDisplayedWidgets = r1
            r6.mDisplayedWidgetsCount = r2
            int r0 = r6.mWrapMode
            if (r0 == 0) goto L_0x00c3
            r4 = 1
            if (r0 == r4) goto L_0x00b6
            r3 = 2
            if (r0 == r3) goto L_0x00a9
            r17 = r5
            r0 = 0
            r11 = 1
            goto L_0x00d0
        L_0x00a9:
            int r3 = r6.mOrientation
            r0 = r18
            r11 = 1
            r4 = r16
            r17 = r5
            r0.measureAligned(r1, r2, r3, r4, r5)
            goto L_0x00cf
        L_0x00b6:
            r17 = r5
            r11 = 1
            int r3 = r6.mOrientation
            r0 = r18
            r4 = r16
            r0.measureChainWrap(r1, r2, r3, r4, r5)
            goto L_0x00cf
        L_0x00c3:
            r17 = r5
            r11 = 1
            int r3 = r6.mOrientation
            r0 = r18
            r4 = r16
            r0.measureNoWrap(r1, r2, r3, r4, r5)
        L_0x00cf:
            r0 = 0
        L_0x00d0:
            r1 = r17[r0]
            int r1 = r1 + r12
            int r1 = r1 + r13
            r2 = r17[r11]
            int r2 = r2 + r14
            int r2 = r2 + r15
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = 1073741824(0x40000000, float:2.0)
            if (r7 != r4) goto L_0x00e0
            r1 = r8
            goto L_0x00eb
        L_0x00e0:
            if (r7 != r3) goto L_0x00e7
            int r1 = java.lang.Math.min(r1, r8)
            goto L_0x00eb
        L_0x00e7:
            if (r7 != 0) goto L_0x00ea
            goto L_0x00eb
        L_0x00ea:
            r1 = 0
        L_0x00eb:
            if (r9 != r4) goto L_0x00ef
            r2 = r10
            goto L_0x00fa
        L_0x00ef:
            if (r9 != r3) goto L_0x00f6
            int r2 = java.lang.Math.min(r2, r10)
            goto L_0x00fa
        L_0x00f6:
            if (r9 != 0) goto L_0x00f9
            goto L_0x00fa
        L_0x00f9:
            r2 = 0
        L_0x00fa:
            r6.setMeasure(r1, r2)
            r6.setWidth(r1)
            r6.setHeight(r2)
            int r1 = r6.mWidgetsCount
            if (r1 <= 0) goto L_0x0108
            goto L_0x0109
        L_0x0108:
            r11 = 0
        L_0x0109:
            r6.needsCallbackFromSolver(r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.measure(int, int, int, int):void");
    }

    private class WidgetsList {
        /* access modifiers changed from: private */
        public ConstraintWidget biggest = null;
        int biggestDimension = 0;
        private ConstraintAnchor mBottom;
        private int mCount = 0;
        private int mHeight = 0;
        private ConstraintAnchor mLeft;
        private int mMax = 0;
        private int mNbMatchConstraintsWidgets = 0;
        private int mOrientation = 0;
        private int mPaddingBottom = 0;
        private int mPaddingLeft = 0;
        private int mPaddingRight = 0;
        private int mPaddingTop = 0;
        private ConstraintAnchor mRight;
        private int mStartIndex = 0;
        private ConstraintAnchor mTop;
        private int mWidth = 0;

        public WidgetsList(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2) {
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = i2;
        }

        public void setup(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2, int i3, int i4, int i5, int i6) {
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = i2;
            this.mPaddingTop = i3;
            this.mPaddingRight = i4;
            this.mPaddingBottom = i5;
            this.mMax = i6;
        }

        public void clear() {
            this.biggestDimension = 0;
            this.biggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        public void setStartIndex(int i) {
            this.mStartIndex = i;
        }

        public int getWidth() {
            if (this.mOrientation == 0) {
                return this.mWidth - Flow.this.mHorizontalGap;
            }
            return this.mWidth;
        }

        public int getHeight() {
            if (this.mOrientation == 1) {
                return this.mHeight - Flow.this.mVerticalGap;
            }
            return this.mHeight;
        }

        public void add(ConstraintWidget constraintWidget) {
            int i = 0;
            if (this.mOrientation == 0) {
                int access$200 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    access$200 = 0;
                }
                int access$000 = Flow.this.mHorizontalGap;
                if (constraintWidget.getVisibility() != 8) {
                    i = access$000;
                }
                this.mWidth += access$200 + i;
                int access$300 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (this.biggest == null || this.biggestDimension < access$300) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = access$300;
                    this.mHeight = access$300;
                }
            } else {
                int access$2002 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                int access$3002 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    access$3002 = 0;
                }
                int access$100 = Flow.this.mVerticalGap;
                if (constraintWidget.getVisibility() != 8) {
                    i = access$100;
                }
                this.mHeight += access$3002 + i;
                if (this.biggest == null || this.biggestDimension < access$2002) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = access$2002;
                    this.mWidth = access$2002;
                }
            }
            this.mCount++;
        }

        /* JADX WARNING: Removed duplicated region for block: B:55:0x00df  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void createConstraints(boolean r17, int r18, boolean r19) {
            /*
                r16 = this;
                r0 = r16
                int r1 = r0.mCount
                r2 = 0
                r3 = 0
            L_0x0006:
                if (r3 >= r1) goto L_0x0027
                int r4 = r0.mStartIndex
                int r4 = r4 + r3
                androidx.constraintlayout.solver.widgets.Flow r5 = androidx.constraintlayout.solver.widgets.Flow.this
                int r5 = r5.mDisplayedWidgetsCount
                if (r4 < r5) goto L_0x0014
                goto L_0x0027
            L_0x0014:
                androidx.constraintlayout.solver.widgets.Flow r4 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r4 = r4.mDisplayedWidgets
                int r5 = r0.mStartIndex
                int r5 = r5 + r3
                r4 = r4[r5]
                if (r4 == 0) goto L_0x0024
                r4.resetAnchors()
            L_0x0024:
                int r3 = r3 + 1
                goto L_0x0006
            L_0x0027:
                if (r1 == 0) goto L_0x035c
                androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.biggest
                if (r3 != 0) goto L_0x002f
                goto L_0x035c
            L_0x002f:
                if (r19 == 0) goto L_0x0035
                if (r18 != 0) goto L_0x0035
                r4 = 1
                goto L_0x0036
            L_0x0035:
                r4 = 0
            L_0x0036:
                r5 = -1
                r6 = 0
                r7 = -1
                r8 = -1
            L_0x003a:
                if (r6 >= r1) goto L_0x0067
                if (r17 == 0) goto L_0x0042
                int r9 = r1 + -1
                int r9 = r9 - r6
                goto L_0x0043
            L_0x0042:
                r9 = r6
            L_0x0043:
                int r10 = r0.mStartIndex
                int r10 = r10 + r9
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mDisplayedWidgetsCount
                if (r10 < r11) goto L_0x004f
                goto L_0x0067
            L_0x004f:
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = r10.mDisplayedWidgets
                int r11 = r0.mStartIndex
                int r11 = r11 + r9
                r9 = r10[r11]
                int r9 = r9.getVisibility()
                if (r9 != 0) goto L_0x0064
                if (r7 != r5) goto L_0x0063
                r7 = r6
            L_0x0063:
                r8 = r6
            L_0x0064:
                int r6 = r6 + 1
                goto L_0x003a
            L_0x0067:
                r6 = 0
                int r9 = r0.mOrientation
                if (r9 != 0) goto L_0x01eb
                androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.biggest
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                int r10 = r10.mVerticalStyle
                r9.setVerticalChainStyle(r10)
                int r10 = r0.mPaddingTop
                if (r18 <= 0) goto L_0x0082
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mVerticalGap
                int r10 = r10 + r11
            L_0x0082:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r0.mTop
                r11.connect(r12, r10)
                if (r19 == 0) goto L_0x0094
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r9.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mBottom
                int r12 = r0.mPaddingBottom
                r10.connect(r11, r12)
            L_0x0094:
                if (r18 <= 0) goto L_0x00a1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.mTop
                androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r10.mOwner
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r10.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                r10.connect(r11, r2)
            L_0x00a1:
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                int r10 = r10.mVerticalAlign
                r11 = 3
                if (r10 != r11) goto L_0x00db
                boolean r10 = r9.hasBaseline()
                if (r10 != 0) goto L_0x00db
                r10 = 0
            L_0x00b1:
                if (r10 >= r1) goto L_0x00db
                if (r17 == 0) goto L_0x00b9
                int r12 = r1 + -1
                int r12 = r12 - r10
                goto L_0x00ba
            L_0x00b9:
                r12 = r10
            L_0x00ba:
                int r13 = r0.mStartIndex
                int r13 = r13 + r12
                androidx.constraintlayout.solver.widgets.Flow r14 = androidx.constraintlayout.solver.widgets.Flow.this
                int r14 = r14.mDisplayedWidgetsCount
                if (r13 < r14) goto L_0x00c6
                goto L_0x00db
            L_0x00c6:
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r13.mDisplayedWidgets
                int r14 = r0.mStartIndex
                int r14 = r14 + r12
                r12 = r13[r14]
                boolean r13 = r12.hasBaseline()
                if (r13 == 0) goto L_0x00d8
                goto L_0x00dc
            L_0x00d8:
                int r10 = r10 + 1
                goto L_0x00b1
            L_0x00db:
                r12 = r9
            L_0x00dc:
                r10 = 0
            L_0x00dd:
                if (r10 >= r1) goto L_0x035c
                if (r17 == 0) goto L_0x00e5
                int r13 = r1 + -1
                int r13 = r13 - r10
                goto L_0x00e6
            L_0x00e5:
                r13 = r10
            L_0x00e6:
                int r14 = r0.mStartIndex
                int r14 = r14 + r13
                androidx.constraintlayout.solver.widgets.Flow r15 = androidx.constraintlayout.solver.widgets.Flow.this
                int r15 = r15.mDisplayedWidgetsCount
                if (r14 < r15) goto L_0x00f3
                goto L_0x035c
            L_0x00f3:
                androidx.constraintlayout.solver.widgets.Flow r14 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r14 = r14.mDisplayedWidgets
                int r15 = r0.mStartIndex
                int r15 = r15 + r13
                r14 = r14[r15]
                if (r10 != 0) goto L_0x0109
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r14.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mLeft
                int r3 = r0.mPaddingLeft
                r14.connect((androidx.constraintlayout.solver.widgets.ConstraintAnchor) r15, (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r11, (int) r3)
            L_0x0109:
                if (r13 != 0) goto L_0x014c
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mHorizontalStyle
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                float r11 = r11.mHorizontalBias
                int r13 = r0.mStartIndex
                if (r13 != 0) goto L_0x0130
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mFirstHorizontalStyle
                if (r13 == r5) goto L_0x0130
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mFirstHorizontalStyle
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                float r11 = r11.mFirstHorizontalBias
                goto L_0x0146
            L_0x0130:
                if (r19 == 0) goto L_0x0146
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mLastHorizontalStyle
                if (r13 == r5) goto L_0x0146
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mLastHorizontalStyle
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                float r11 = r11.mLastHorizontalBias
            L_0x0146:
                r14.setHorizontalChainStyle(r3)
                r14.setHorizontalBiasPercent(r11)
            L_0x014c:
                int r3 = r1 + -1
                if (r10 != r3) goto L_0x0159
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mRight
                int r13 = r0.mPaddingRight
                r14.connect((androidx.constraintlayout.solver.widgets.ConstraintAnchor) r3, (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r11, (int) r13)
            L_0x0159:
                if (r6 == 0) goto L_0x0184
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r6.mRight
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mHorizontalGap
                r3.connect(r11, r13)
                if (r10 != r7) goto L_0x0171
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mLeft
                int r11 = r0.mPaddingLeft
                r3.setGoneMargin(r11)
            L_0x0171:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r6.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r14.mLeft
                r3.connect(r11, r2)
                r3 = 1
                int r11 = r8 + 1
                if (r10 != r11) goto L_0x0184
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r6.mRight
                int r6 = r0.mPaddingRight
                r3.setGoneMargin(r6)
            L_0x0184:
                if (r14 == r9) goto L_0x01e4
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mVerticalAlign
                r6 = 3
                if (r3 != r6) goto L_0x01a5
                boolean r3 = r12.hasBaseline()
                if (r3 == 0) goto L_0x01a5
                if (r14 == r12) goto L_0x01a5
                boolean r3 = r14.hasBaseline()
                if (r3 == 0) goto L_0x01a5
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBaseline
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r12.mBaseline
                r3.connect(r11, r2)
                goto L_0x01e5
            L_0x01a5:
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mVerticalAlign
                if (r3 == 0) goto L_0x01dc
                r11 = 1
                if (r3 == r11) goto L_0x01d4
                if (r4 == 0) goto L_0x01c5
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mTop
                int r13 = r0.mPaddingTop
                r3.connect(r11, r13)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mBottom
                int r13 = r0.mPaddingBottom
                r3.connect(r11, r13)
                goto L_0x01e5
            L_0x01c5:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                r3.connect(r11, r2)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mBottom
                r3.connect(r11, r2)
                goto L_0x01e5
            L_0x01d4:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mBottom
                r3.connect(r11, r2)
                goto L_0x01e5
            L_0x01dc:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                r3.connect(r11, r2)
                goto L_0x01e5
            L_0x01e4:
                r6 = 3
            L_0x01e5:
                int r10 = r10 + 1
                r6 = r14
                r11 = 3
                goto L_0x00dd
            L_0x01eb:
                androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.biggest
                androidx.constraintlayout.solver.widgets.Flow r9 = androidx.constraintlayout.solver.widgets.Flow.this
                int r9 = r9.mHorizontalStyle
                r3.setHorizontalChainStyle(r9)
                int r9 = r0.mPaddingLeft
                if (r18 <= 0) goto L_0x0201
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                int r10 = r10.mHorizontalGap
                int r9 = r9 + r10
            L_0x0201:
                if (r17 == 0) goto L_0x0223
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mRight
                r10.connect(r11, r9)
                if (r19 == 0) goto L_0x0215
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r3.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.mLeft
                int r11 = r0.mPaddingRight
                r9.connect(r10, r11)
            L_0x0215:
                if (r18 <= 0) goto L_0x0242
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r0.mRight
                androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r9.mOwner
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mRight
                r9.connect(r10, r2)
                goto L_0x0242
            L_0x0223:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mLeft
                r10.connect(r11, r9)
                if (r19 == 0) goto L_0x0235
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r3.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.mRight
                int r11 = r0.mPaddingRight
                r9.connect(r10, r11)
            L_0x0235:
                if (r18 <= 0) goto L_0x0242
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r0.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r9.mOwner
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mLeft
                r9.connect(r10, r2)
            L_0x0242:
                r9 = 0
            L_0x0243:
                if (r9 >= r1) goto L_0x035c
                int r10 = r0.mStartIndex
                int r10 = r10 + r9
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mDisplayedWidgetsCount
                if (r10 < r11) goto L_0x0252
                goto L_0x035c
            L_0x0252:
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = r10.mDisplayedWidgets
                int r11 = r0.mStartIndex
                int r11 = r11 + r9
                r10 = r10[r11]
                if (r9 != 0) goto L_0x02a9
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r0.mTop
                int r13 = r0.mPaddingTop
                r10.connect((androidx.constraintlayout.solver.widgets.ConstraintAnchor) r11, (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r12, (int) r13)
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mVerticalStyle
                androidx.constraintlayout.solver.widgets.Flow r12 = androidx.constraintlayout.solver.widgets.Flow.this
                float r12 = r12.mVerticalBias
                int r13 = r0.mStartIndex
                if (r13 != 0) goto L_0x028d
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mFirstVerticalStyle
                if (r13 == r5) goto L_0x028d
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mFirstVerticalStyle
                androidx.constraintlayout.solver.widgets.Flow r12 = androidx.constraintlayout.solver.widgets.Flow.this
                float r12 = r12.mFirstVerticalBias
                goto L_0x02a3
            L_0x028d:
                if (r19 == 0) goto L_0x02a3
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mLastVerticalStyle
                if (r13 == r5) goto L_0x02a3
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mLastVerticalStyle
                androidx.constraintlayout.solver.widgets.Flow r12 = androidx.constraintlayout.solver.widgets.Flow.this
                float r12 = r12.mLastVerticalBias
            L_0x02a3:
                r10.setVerticalChainStyle(r11)
                r10.setVerticalBiasPercent(r12)
            L_0x02a9:
                int r11 = r1 + -1
                if (r9 != r11) goto L_0x02b6
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r0.mBottom
                int r13 = r0.mPaddingBottom
                r10.connect((androidx.constraintlayout.solver.widgets.ConstraintAnchor) r11, (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r12, (int) r13)
            L_0x02b6:
                if (r6 == 0) goto L_0x02e1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r6.mBottom
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mVerticalGap
                r11.connect(r12, r13)
                if (r9 != r7) goto L_0x02ce
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mTop
                int r12 = r0.mPaddingTop
                r11.setGoneMargin(r12)
            L_0x02ce:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r6.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r10.mTop
                r11.connect(r12, r2)
                r11 = 1
                int r12 = r8 + 1
                if (r9 != r12) goto L_0x02e1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mBottom
                int r11 = r0.mPaddingBottom
                r6.setGoneMargin(r11)
            L_0x02e1:
                if (r10 == r3) goto L_0x0356
                r6 = 2
                if (r17 == 0) goto L_0x0313
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mHorizontalAlign
                if (r11 == 0) goto L_0x030b
                r12 = 1
                if (r11 == r12) goto L_0x0303
                if (r11 == r6) goto L_0x02f4
                goto L_0x0356
            L_0x02f4:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x0356
            L_0x0303:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                goto L_0x0356
            L_0x030b:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x0356
            L_0x0313:
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mHorizontalAlign
                if (r11 == 0) goto L_0x034d
                r12 = 1
                if (r11 == r12) goto L_0x0345
                if (r11 == r6) goto L_0x0321
                goto L_0x0357
            L_0x0321:
                if (r4 == 0) goto L_0x0336
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mLeft
                int r13 = r0.mPaddingLeft
                r6.connect(r11, r13)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mRight
                int r13 = r0.mPaddingRight
                r6.connect(r11, r13)
                goto L_0x0357
            L_0x0336:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x0357
            L_0x0345:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x0357
            L_0x034d:
                r12 = 1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                goto L_0x0357
            L_0x0356:
                r12 = 1
            L_0x0357:
                int r9 = r9 + 1
                r6 = r10
                goto L_0x0243
            L_0x035c:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.WidgetsList.createConstraints(boolean, int, boolean):void");
        }

        public void measureMatchConstraints(int i) {
            int i2 = this.mNbMatchConstraintsWidgets;
            if (i2 != 0) {
                int i3 = this.mCount;
                int i4 = i / i2;
                int i5 = 0;
                while (i5 < i3 && this.mStartIndex + i5 < Flow.this.mDisplayedWidgetsCount) {
                    ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i5];
                    if (this.mOrientation == 0) {
                        if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                            Flow.this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i4, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                        }
                    } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                        Flow.this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i4);
                    }
                    i5++;
                }
                recomputeDimensions();
            }
        }

        private void recomputeDimensions() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.biggestDimension = 0;
            int i = this.mCount;
            int i2 = 0;
            while (i2 < i && this.mStartIndex + i2 < Flow.this.mDisplayedWidgetsCount) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i2];
                if (this.mOrientation == 0) {
                    int width = constraintWidget.getWidth();
                    int access$000 = Flow.this.mHorizontalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        access$000 = 0;
                    }
                    this.mWidth += width + access$000;
                    int access$300 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    if (this.biggest == null || this.biggestDimension < access$300) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = access$300;
                        this.mHeight = access$300;
                    }
                } else {
                    int access$200 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                    int access$3002 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    int access$100 = Flow.this.mVerticalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        access$100 = 0;
                    }
                    this.mHeight += access$3002 + access$100;
                    if (this.biggest == null || this.biggestDimension < access$200) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = access$200;
                        this.mWidth = access$200;
                    }
                }
                i2++;
            }
        }
    }

    private void measureChainWrap(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        int i4;
        ConstraintAnchor constraintAnchor;
        int i5;
        int i6;
        int i7;
        ConstraintAnchor constraintAnchor2;
        int i8;
        int i9;
        int i10 = i;
        int i11 = i3;
        if (i10 != 0) {
            this.mChainList.clear();
            WidgetsList widgetsList = new WidgetsList(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
            this.mChainList.add(widgetsList);
            if (i2 == 0) {
                i4 = 0;
                int i12 = 0;
                int i13 = 0;
                while (i13 < i10) {
                    ConstraintWidget constraintWidget = constraintWidgetArr[i13];
                    int widgetWidth = getWidgetWidth(constraintWidget, i11);
                    if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        i4++;
                    }
                    int i14 = i4;
                    boolean z = (i12 == i11 || (this.mHorizontalGap + i12) + widgetWidth > i11) && widgetsList.biggest != null;
                    if (!z && i13 > 0 && (i9 = this.mMaxElementsWrap) > 0 && i13 % i9 == 0) {
                        z = true;
                    }
                    if (z) {
                        widgetsList = new WidgetsList(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
                        widgetsList.setStartIndex(i13);
                        this.mChainList.add(widgetsList);
                    } else if (i13 > 0) {
                        i12 += this.mHorizontalGap + widgetWidth;
                        widgetsList.add(constraintWidget);
                        i13++;
                        i4 = i14;
                    }
                    i12 = widgetWidth;
                    widgetsList.add(constraintWidget);
                    i13++;
                    i4 = i14;
                }
            } else {
                int i15 = 0;
                int i16 = 0;
                int i17 = 0;
                while (i17 < i10) {
                    ConstraintWidget constraintWidget2 = constraintWidgetArr[i17];
                    int widgetHeight = getWidgetHeight(constraintWidget2, i11);
                    if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        i4++;
                    }
                    int i18 = i4;
                    boolean z2 = (i16 == i11 || (this.mVerticalGap + i16) + widgetHeight > i11) && widgetsList.biggest != null;
                    if (!z2 && i17 > 0 && (i8 = this.mMaxElementsWrap) > 0 && i17 % i8 == 0) {
                        z2 = true;
                    }
                    if (z2) {
                        widgetsList = new WidgetsList(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
                        widgetsList.setStartIndex(i17);
                        this.mChainList.add(widgetsList);
                    } else if (i17 > 0) {
                        i16 += this.mVerticalGap + widgetHeight;
                        widgetsList.add(constraintWidget2);
                        i17++;
                        i15 = i18;
                    }
                    i16 = widgetHeight;
                    widgetsList.add(constraintWidget2);
                    i17++;
                    i15 = i18;
                }
            }
            int size = this.mChainList.size();
            ConstraintAnchor constraintAnchor3 = this.mLeft;
            ConstraintAnchor constraintAnchor4 = this.mTop;
            ConstraintAnchor constraintAnchor5 = this.mRight;
            ConstraintAnchor constraintAnchor6 = this.mBottom;
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            boolean z3 = getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            if (i4 > 0 && z3) {
                for (int i19 = 0; i19 < size; i19++) {
                    WidgetsList widgetsList2 = this.mChainList.get(i19);
                    if (i2 == 0) {
                        widgetsList2.measureMatchConstraints(i11 - widgetsList2.getWidth());
                    } else {
                        widgetsList2.measureMatchConstraints(i11 - widgetsList2.getHeight());
                    }
                }
            }
            int i20 = paddingTop;
            int i21 = paddingRight;
            int i22 = 0;
            int i23 = 0;
            int i24 = 0;
            int i25 = paddingLeft;
            ConstraintAnchor constraintAnchor7 = constraintAnchor4;
            ConstraintAnchor constraintAnchor8 = constraintAnchor3;
            int i26 = paddingBottom;
            while (i24 < size) {
                WidgetsList widgetsList3 = this.mChainList.get(i24);
                if (i2 == 0) {
                    if (i24 < size - 1) {
                        constraintAnchor2 = this.mChainList.get(i24 + 1).biggest.mTop;
                        i7 = 0;
                    } else {
                        constraintAnchor2 = this.mBottom;
                        i7 = getPaddingBottom();
                    }
                    ConstraintAnchor constraintAnchor9 = widgetsList3.biggest.mBottom;
                    ConstraintAnchor constraintAnchor10 = constraintAnchor8;
                    ConstraintAnchor constraintAnchor11 = constraintAnchor8;
                    int i27 = i22;
                    ConstraintAnchor constraintAnchor12 = constraintAnchor7;
                    int i28 = i23;
                    ConstraintAnchor constraintAnchor13 = constraintAnchor5;
                    ConstraintAnchor constraintAnchor14 = constraintAnchor5;
                    i5 = i24;
                    widgetsList3.setup(i2, constraintAnchor10, constraintAnchor12, constraintAnchor13, constraintAnchor2, i25, i20, i21, i7, i3);
                    int max = Math.max(i28, widgetsList3.getWidth());
                    i22 = i27 + widgetsList3.getHeight();
                    if (i5 > 0) {
                        i22 += this.mVerticalGap;
                    }
                    constraintAnchor8 = constraintAnchor11;
                    i23 = max;
                    constraintAnchor7 = constraintAnchor9;
                    i20 = 0;
                    constraintAnchor = constraintAnchor14;
                    int i29 = i7;
                    constraintAnchor6 = constraintAnchor2;
                    i26 = i29;
                } else {
                    ConstraintAnchor constraintAnchor15 = constraintAnchor8;
                    int i30 = i22;
                    int i31 = i23;
                    i5 = i24;
                    if (i5 < size - 1) {
                        constraintAnchor = this.mChainList.get(i5 + 1).biggest.mLeft;
                        i6 = 0;
                    } else {
                        constraintAnchor = this.mRight;
                        i6 = getPaddingRight();
                    }
                    ConstraintAnchor constraintAnchor16 = widgetsList3.biggest.mRight;
                    widgetsList3.setup(i2, constraintAnchor15, constraintAnchor7, constraintAnchor, constraintAnchor6, i25, i20, i6, i26, i3);
                    i23 = i31 + widgetsList3.getWidth();
                    int max2 = Math.max(i30, widgetsList3.getHeight());
                    if (i5 > 0) {
                        i23 += this.mHorizontalGap;
                    }
                    i22 = max2;
                    i21 = i6;
                    constraintAnchor8 = constraintAnchor16;
                    i25 = 0;
                }
                i24 = i5 + 1;
                int i32 = i3;
                constraintAnchor5 = constraintAnchor;
            }
            iArr[0] = i23;
            iArr[1] = i22;
        }
    }

    private void measureNoWrap(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        WidgetsList widgetsList;
        int i4 = i;
        if (i4 != 0) {
            if (this.mChainList.size() == 0) {
                widgetsList = new WidgetsList(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
                this.mChainList.add(widgetsList);
            } else {
                WidgetsList widgetsList2 = this.mChainList.get(0);
                widgetsList2.clear();
                widgetsList = widgetsList2;
                int i5 = i2;
                widgetsList.setup(i5, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), i3);
            }
            for (int i6 = 0; i6 < i4; i6++) {
                widgetsList.add(constraintWidgetArr[i6]);
            }
            iArr[0] = widgetsList.getWidth();
            iArr[1] = widgetsList.getHeight();
        }
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x012b A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0068  */
    private void measureAligned(androidx.constraintlayout.solver.widgets.ConstraintWidget[] r17, int r18, int r19, int r20, int[] r21) {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            r4 = r20
            r5 = 0
            if (r3 != 0) goto L_0x0030
            int r6 = r0.mMaxElementsWrap
            if (r6 > 0) goto L_0x002d
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x0014:
            if (r7 >= r2) goto L_0x002d
            if (r7 <= 0) goto L_0x001b
            int r9 = r0.mHorizontalGap
            int r8 = r8 + r9
        L_0x001b:
            r9 = r1[r7]
            if (r9 != 0) goto L_0x0020
            goto L_0x002a
        L_0x0020:
            int r9 = r0.getWidgetWidth(r9, r4)
            int r8 = r8 + r9
            if (r8 <= r4) goto L_0x0028
            goto L_0x002d
        L_0x0028:
            int r6 = r6 + 1
        L_0x002a:
            int r7 = r7 + 1
            goto L_0x0014
        L_0x002d:
            r7 = r6
            r6 = 0
            goto L_0x0051
        L_0x0030:
            int r6 = r0.mMaxElementsWrap
            if (r6 > 0) goto L_0x0050
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x0037:
            if (r7 >= r2) goto L_0x0050
            if (r7 <= 0) goto L_0x003e
            int r9 = r0.mVerticalGap
            int r8 = r8 + r9
        L_0x003e:
            r9 = r1[r7]
            if (r9 != 0) goto L_0x0043
            goto L_0x004d
        L_0x0043:
            int r9 = r0.getWidgetHeight(r9, r4)
            int r8 = r8 + r9
            if (r8 <= r4) goto L_0x004b
            goto L_0x0050
        L_0x004b:
            int r6 = r6 + 1
        L_0x004d:
            int r7 = r7 + 1
            goto L_0x0037
        L_0x0050:
            r7 = 0
        L_0x0051:
            int[] r8 = r0.mAlignedDimensions
            if (r8 != 0) goto L_0x005a
            r8 = 2
            int[] r8 = new int[r8]
            r0.mAlignedDimensions = r8
        L_0x005a:
            r8 = 1
            if (r6 != 0) goto L_0x005f
            if (r3 == r8) goto L_0x0063
        L_0x005f:
            if (r7 != 0) goto L_0x0065
            if (r3 != 0) goto L_0x0065
        L_0x0063:
            r9 = 1
            goto L_0x0066
        L_0x0065:
            r9 = 0
        L_0x0066:
            if (r9 != 0) goto L_0x012b
            if (r3 != 0) goto L_0x0074
            float r6 = (float) r2
            float r10 = (float) r7
            float r6 = r6 / r10
            double r10 = (double) r6
            double r10 = java.lang.Math.ceil(r10)
            int r6 = (int) r10
            goto L_0x007d
        L_0x0074:
            float r7 = (float) r2
            float r10 = (float) r6
            float r7 = r7 / r10
            double r10 = (double) r7
            double r10 = java.lang.Math.ceil(r10)
            int r7 = (int) r10
        L_0x007d:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = r0.mAlignedBiggestElementsInCols
            r11 = 0
            if (r10 == 0) goto L_0x008a
            int r12 = r10.length
            if (r12 >= r7) goto L_0x0086
            goto L_0x008a
        L_0x0086:
            java.util.Arrays.fill(r10, r11)
            goto L_0x008e
        L_0x008a:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = new androidx.constraintlayout.solver.widgets.ConstraintWidget[r7]
            r0.mAlignedBiggestElementsInCols = r10
        L_0x008e:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = r0.mAlignedBiggestElementsInRows
            if (r10 == 0) goto L_0x009a
            int r12 = r10.length
            if (r12 >= r6) goto L_0x0096
            goto L_0x009a
        L_0x0096:
            java.util.Arrays.fill(r10, r11)
            goto L_0x009e
        L_0x009a:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = new androidx.constraintlayout.solver.widgets.ConstraintWidget[r6]
            r0.mAlignedBiggestElementsInRows = r10
        L_0x009e:
            r10 = 0
        L_0x009f:
            if (r10 >= r7) goto L_0x00e7
            r11 = 0
        L_0x00a2:
            if (r11 >= r6) goto L_0x00e4
            int r12 = r11 * r7
            int r12 = r12 + r10
            if (r3 != r8) goto L_0x00ac
            int r12 = r10 * r6
            int r12 = r12 + r11
        L_0x00ac:
            int r13 = r1.length
            if (r12 < r13) goto L_0x00b0
            goto L_0x00e1
        L_0x00b0:
            r12 = r1[r12]
            if (r12 != 0) goto L_0x00b5
            goto L_0x00e1
        L_0x00b5:
            int r13 = r0.getWidgetWidth(r12, r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r14 = r0.mAlignedBiggestElementsInCols
            r15 = r14[r10]
            if (r15 == 0) goto L_0x00c7
            r14 = r14[r10]
            int r14 = r14.getWidth()
            if (r14 >= r13) goto L_0x00cb
        L_0x00c7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r0.mAlignedBiggestElementsInCols
            r13[r10] = r12
        L_0x00cb:
            int r13 = r0.getWidgetHeight(r12, r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r14 = r0.mAlignedBiggestElementsInRows
            r15 = r14[r11]
            if (r15 == 0) goto L_0x00dd
            r14 = r14[r11]
            int r14 = r14.getHeight()
            if (r14 >= r13) goto L_0x00e1
        L_0x00dd:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r0.mAlignedBiggestElementsInRows
            r13[r11] = r12
        L_0x00e1:
            int r11 = r11 + 1
            goto L_0x00a2
        L_0x00e4:
            int r10 = r10 + 1
            goto L_0x009f
        L_0x00e7:
            r10 = 0
            r11 = 0
        L_0x00e9:
            if (r10 >= r7) goto L_0x00fe
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r12 = r0.mAlignedBiggestElementsInCols
            r12 = r12[r10]
            if (r12 == 0) goto L_0x00fb
            if (r10 <= 0) goto L_0x00f6
            int r13 = r0.mHorizontalGap
            int r11 = r11 + r13
        L_0x00f6:
            int r12 = r0.getWidgetWidth(r12, r4)
            int r11 = r11 + r12
        L_0x00fb:
            int r10 = r10 + 1
            goto L_0x00e9
        L_0x00fe:
            r10 = 0
            r12 = 0
        L_0x0100:
            if (r10 >= r6) goto L_0x0115
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r0.mAlignedBiggestElementsInRows
            r13 = r13[r10]
            if (r13 == 0) goto L_0x0112
            if (r10 <= 0) goto L_0x010d
            int r14 = r0.mVerticalGap
            int r12 = r12 + r14
        L_0x010d:
            int r13 = r0.getWidgetHeight(r13, r4)
            int r12 = r12 + r13
        L_0x0112:
            int r10 = r10 + 1
            goto L_0x0100
        L_0x0115:
            r21[r5] = r11
            r21[r8] = r12
            if (r3 != 0) goto L_0x0123
            if (r11 <= r4) goto L_0x0063
            if (r7 <= r8) goto L_0x0063
            int r7 = r7 + -1
            goto L_0x0066
        L_0x0123:
            if (r12 <= r4) goto L_0x0063
            if (r6 <= r8) goto L_0x0063
            int r6 = r6 + -1
            goto L_0x0066
        L_0x012b:
            int[] r1 = r0.mAlignedDimensions
            r1[r5] = r7
            r1[r8] = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.measureAligned(androidx.constraintlayout.solver.widgets.ConstraintWidget[], int, int, int, int[]):void");
    }

    private void createAlignedConstraints(boolean z) {
        ConstraintWidget constraintWidget;
        if (this.mAlignedDimensions != null && this.mAlignedBiggestElementsInCols != null && this.mAlignedBiggestElementsInRows != null) {
            for (int i = 0; i < this.mDisplayedWidgetsCount; i++) {
                this.mDisplayedWidgets[i].resetAnchors();
            }
            int[] iArr = this.mAlignedDimensions;
            int i2 = iArr[0];
            int i3 = iArr[1];
            ConstraintWidget constraintWidget2 = null;
            for (int i4 = 0; i4 < i2; i4++) {
                ConstraintWidget constraintWidget3 = this.mAlignedBiggestElementsInCols[z ? (i2 - i4) - 1 : i4];
                if (!(constraintWidget3 == null || constraintWidget3.getVisibility() == 8)) {
                    if (i4 == 0) {
                        constraintWidget3.connect(constraintWidget3.mLeft, this.mLeft, getPaddingLeft());
                        constraintWidget3.setHorizontalChainStyle(this.mHorizontalStyle);
                        constraintWidget3.setHorizontalBiasPercent(this.mHorizontalBias);
                    }
                    if (i4 == i2 - 1) {
                        constraintWidget3.connect(constraintWidget3.mRight, this.mRight, getPaddingRight());
                    }
                    if (i4 > 0) {
                        constraintWidget3.connect(constraintWidget3.mLeft, constraintWidget2.mRight, this.mHorizontalGap);
                        constraintWidget2.connect(constraintWidget2.mRight, constraintWidget3.mLeft, 0);
                    }
                    constraintWidget2 = constraintWidget3;
                }
            }
            for (int i5 = 0; i5 < i3; i5++) {
                ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInRows[i5];
                if (!(constraintWidget4 == null || constraintWidget4.getVisibility() == 8)) {
                    if (i5 == 0) {
                        constraintWidget4.connect(constraintWidget4.mTop, this.mTop, getPaddingTop());
                        constraintWidget4.setVerticalChainStyle(this.mVerticalStyle);
                        constraintWidget4.setVerticalBiasPercent(this.mVerticalBias);
                    }
                    if (i5 == i3 - 1) {
                        constraintWidget4.connect(constraintWidget4.mBottom, this.mBottom, getPaddingBottom());
                    }
                    if (i5 > 0) {
                        constraintWidget4.connect(constraintWidget4.mTop, constraintWidget2.mBottom, this.mVerticalGap);
                        constraintWidget2.connect(constraintWidget2.mBottom, constraintWidget4.mTop, 0);
                    }
                    constraintWidget2 = constraintWidget4;
                }
            }
            for (int i6 = 0; i6 < i2; i6++) {
                for (int i7 = 0; i7 < i3; i7++) {
                    int i8 = (i7 * i2) + i6;
                    if (this.mOrientation == 1) {
                        i8 = (i6 * i3) + i7;
                    }
                    ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                    if (!(i8 >= constraintWidgetArr.length || (constraintWidget = constraintWidgetArr[i8]) == null || constraintWidget.getVisibility() == 8)) {
                        ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInCols[i6];
                        ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInRows[i7];
                        if (constraintWidget != constraintWidget5) {
                            constraintWidget.connect(constraintWidget.mLeft, constraintWidget5.mLeft, 0);
                            constraintWidget.connect(constraintWidget.mRight, constraintWidget5.mRight, 0);
                        }
                        if (constraintWidget != constraintWidget6) {
                            constraintWidget.connect(constraintWidget.mTop, constraintWidget6.mTop, 0);
                            constraintWidget.connect(constraintWidget.mBottom, constraintWidget6.mBottom, 0);
                        }
                    }
                }
            }
        }
    }

    public void addToSolver(LinearSystem linearSystem, boolean z) {
        super.addToSolver(linearSystem, z);
        boolean isRtl = getParent() != null ? ((ConstraintWidgetContainer) getParent()).isRtl() : false;
        int i = this.mWrapMode;
        if (i != 0) {
            if (i == 1) {
                int size = this.mChainList.size();
                int i2 = 0;
                while (i2 < size) {
                    this.mChainList.get(i2).createConstraints(isRtl, i2, i2 == size + -1);
                    i2++;
                }
            } else if (i == 2) {
                createAlignedConstraints(isRtl);
            }
        } else if (this.mChainList.size() > 0) {
            this.mChainList.get(0).createConstraints(isRtl, 0, true);
        }
        needsCallbackFromSolver(false);
    }
}
