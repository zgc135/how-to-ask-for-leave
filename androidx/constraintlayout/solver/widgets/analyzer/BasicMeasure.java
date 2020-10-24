package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import java.util.ArrayList;

public class BasicMeasure {
    public static final int AT_MOST = Integer.MIN_VALUE;
    private static final boolean DEBUG = false;
    public static final int EXACTLY = 1073741824;
    public static final int FIXED = -3;
    public static final int MATCH_PARENT = -1;
    private static final int MODE_SHIFT = 30;
    public static final int UNSPECIFIED = 0;
    public static final int WRAP_CONTENT = -2;
    private ConstraintWidgetContainer constraintWidgetContainer;
    private Measure mMeasure = new Measure();
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets = new ArrayList<>();

    public static class Measure {
        public ConstraintWidget.DimensionBehaviour horizontalBehavior;
        public int horizontalDimension;
        public int measuredBaseline;
        public boolean measuredHasBaseline;
        public int measuredHeight;
        public boolean measuredNeedsSolverPass;
        public int measuredWidth;
        public boolean useCurrentDimensions;
        public ConstraintWidget.DimensionBehaviour verticalBehavior;
        public int verticalDimension;
    }

    public interface Measurer {
        void didMeasures();

        void measure(ConstraintWidget constraintWidget, Measure measure);
    }

    public void updateHierarchy(ConstraintWidgetContainer constraintWidgetContainer2) {
        this.mVariableDimensionsWidgets.clear();
        int size = constraintWidgetContainer2.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = (ConstraintWidget) constraintWidgetContainer2.mChildren.get(i);
            if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                this.mVariableDimensionsWidgets.add(constraintWidget);
            }
        }
        constraintWidgetContainer2.invalidateGraph();
    }

    public BasicMeasure(ConstraintWidgetContainer constraintWidgetContainer2) {
        this.constraintWidgetContainer = constraintWidgetContainer2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0096, code lost:
        if (r5.isInHorizontalChain() == false) goto L_0x009a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void measureChildren(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r12) {
        /*
            r11 = this;
            java.util.ArrayList r0 = r12.mChildren
            int r0 = r0.size()
            r1 = 64
            boolean r1 = r12.optimizeFor(r1)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r2 = r12.getMeasurer()
            r3 = 0
            r4 = 0
        L_0x0012:
            if (r4 >= r0) goto L_0x00b1
            java.util.ArrayList r5 = r12.mChildren
            java.lang.Object r5 = r5.get(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r5
            boolean r6 = r5 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r6 == 0) goto L_0x0022
            goto L_0x00ad
        L_0x0022:
            boolean r6 = r5 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r6 == 0) goto L_0x0028
            goto L_0x00ad
        L_0x0028:
            boolean r6 = r5.isInVirtualLayout()
            if (r6 == 0) goto L_0x0030
            goto L_0x00ad
        L_0x0030:
            if (r1 == 0) goto L_0x004b
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r6 = r5.horizontalRun
            if (r6 == 0) goto L_0x004b
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r6 = r5.verticalRun
            if (r6 == 0) goto L_0x004b
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r6 = r5.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            boolean r6 = r6.resolved
            if (r6 == 0) goto L_0x004b
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r6 = r5.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            boolean r6 = r6.resolved
            if (r6 == 0) goto L_0x004b
            goto L_0x00ad
        L_0x004b:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = r5.getDimensionBehaviour(r3)
            r7 = 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = r5.getDimensionBehaviour(r7)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 != r9) goto L_0x0066
            int r9 = r5.mMatchConstraintDefaultWidth
            if (r9 == r7) goto L_0x0066
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r8 != r9) goto L_0x0066
            int r9 = r5.mMatchConstraintDefaultHeight
            if (r9 == r7) goto L_0x0066
            r9 = 1
            goto L_0x0067
        L_0x0066:
            r9 = 0
        L_0x0067:
            if (r9 != 0) goto L_0x0099
            boolean r10 = r12.optimizeFor(r7)
            if (r10 == 0) goto L_0x0099
            boolean r10 = r5 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
            if (r10 != 0) goto L_0x0099
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 != r10) goto L_0x0086
            int r10 = r5.mMatchConstraintDefaultWidth
            if (r10 != 0) goto L_0x0086
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r8 == r10) goto L_0x0086
            boolean r10 = r5.isInHorizontalChain()
            if (r10 != 0) goto L_0x0086
            r9 = 1
        L_0x0086:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r8 != r10) goto L_0x0099
            int r8 = r5.mMatchConstraintDefaultHeight
            if (r8 != 0) goto L_0x0099
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 == r8) goto L_0x0099
            boolean r6 = r5.isInHorizontalChain()
            if (r6 != 0) goto L_0x0099
            goto L_0x009a
        L_0x0099:
            r7 = r9
        L_0x009a:
            if (r7 == 0) goto L_0x009d
            goto L_0x00ad
        L_0x009d:
            r11.measure(r2, r5, r3)
            androidx.constraintlayout.solver.Metrics r5 = r12.mMetrics
            if (r5 == 0) goto L_0x00ad
            androidx.constraintlayout.solver.Metrics r5 = r12.mMetrics
            long r6 = r5.measuredWidgets
            r8 = 1
            long r6 = r6 + r8
            r5.measuredWidgets = r6
        L_0x00ad:
            int r4 = r4 + 1
            goto L_0x0012
        L_0x00b1:
            r2.didMeasures()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.measureChildren(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer):void");
    }

    private void solveLinearSystem(ConstraintWidgetContainer constraintWidgetContainer2, String str, int i, int i2) {
        int minWidth = constraintWidgetContainer2.getMinWidth();
        int minHeight = constraintWidgetContainer2.getMinHeight();
        constraintWidgetContainer2.setMinWidth(0);
        constraintWidgetContainer2.setMinHeight(0);
        constraintWidgetContainer2.setWidth(i);
        constraintWidgetContainer2.setHeight(i2);
        constraintWidgetContainer2.setMinWidth(minWidth);
        constraintWidgetContainer2.setMinHeight(minHeight);
        this.constraintWidgetContainer.layout();
    }

    public long solverMeasure(ConstraintWidgetContainer constraintWidgetContainer2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        int i10;
        boolean z;
        int i11;
        boolean z2;
        boolean z3;
        boolean z4;
        int i12;
        Measurer measurer;
        boolean z5;
        int i13;
        int i14;
        int i15;
        boolean z6;
        boolean z7;
        boolean z8;
        ConstraintWidgetContainer constraintWidgetContainer3 = constraintWidgetContainer2;
        int i16 = i;
        int i17 = i4;
        int i18 = i6;
        Measurer measurer2 = constraintWidgetContainer2.getMeasurer();
        int size = constraintWidgetContainer3.mChildren.size();
        int width = constraintWidgetContainer2.getWidth();
        int height = constraintWidgetContainer2.getHeight();
        boolean enabled = Optimizer.enabled(i16, 128);
        boolean z9 = enabled || Optimizer.enabled(i16, 64);
        if (z9) {
            int i19 = 0;
            while (true) {
                if (i19 >= size) {
                    break;
                }
                ConstraintWidget constraintWidget = (ConstraintWidget) constraintWidgetContainer3.mChildren.get(i19);
                boolean z10 = (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && constraintWidget.getDimensionRatio() > 0.0f;
                if ((!constraintWidget.isInHorizontalChain() || !z10) && ((!constraintWidget.isInVerticalChain() || !z10) && !(constraintWidget instanceof VirtualLayout) && !constraintWidget.isInHorizontalChain() && !constraintWidget.isInVerticalChain())) {
                    i19++;
                }
            }
            z9 = false;
        }
        if (z9 && LinearSystem.sMetrics != null) {
            LinearSystem.sMetrics.measures++;
        }
        boolean z11 = z9 & ((i17 == 1073741824 && i18 == 1073741824) || enabled);
        if (z11) {
            int min = Math.min(constraintWidgetContainer2.getMaxWidth(), i5);
            int min2 = Math.min(constraintWidgetContainer2.getMaxHeight(), i7);
            if (i17 == 1073741824 && constraintWidgetContainer2.getWidth() != min) {
                constraintWidgetContainer3.setWidth(min);
                constraintWidgetContainer2.invalidateGraph();
            }
            if (i18 == 1073741824 && constraintWidgetContainer2.getHeight() != min2) {
                constraintWidgetContainer3.setHeight(min2);
                constraintWidgetContainer2.invalidateGraph();
            }
            if (i17 == 1073741824 && i18 == 1073741824) {
                z = constraintWidgetContainer3.directMeasure(enabled);
                i10 = 2;
            } else {
                boolean directMeasureSetup = constraintWidgetContainer3.directMeasureSetup(enabled);
                if (i17 == 1073741824) {
                    z8 = directMeasureSetup & constraintWidgetContainer3.directMeasureWithOrientation(enabled, 0);
                    i10 = 1;
                } else {
                    z8 = directMeasureSetup;
                    i10 = 0;
                }
                if (i18 == 1073741824) {
                    z = constraintWidgetContainer3.directMeasureWithOrientation(enabled, 1) & z8;
                    i10++;
                } else {
                    z = z8;
                }
            }
            if (z) {
                constraintWidgetContainer3.updateFromRuns(i17 == 1073741824, i18 == 1073741824);
            }
        } else {
            z = false;
            i10 = 0;
        }
        if (z && i10 == 2) {
            return 0;
        }
        int optimizationLevel = constraintWidgetContainer2.getOptimizationLevel();
        if (size > 0) {
            measureChildren(constraintWidgetContainer2);
        }
        int size2 = this.mVariableDimensionsWidgets.size();
        if (size > 0) {
            solveLinearSystem(constraintWidgetContainer3, "First pass", width, height);
        }
        if (size2 > 0) {
            boolean z12 = constraintWidgetContainer2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            boolean z13 = constraintWidgetContainer2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
            int max = Math.max(constraintWidgetContainer2.getWidth(), this.constraintWidgetContainer.getMinWidth());
            int max2 = Math.max(constraintWidgetContainer2.getHeight(), this.constraintWidgetContainer.getMinHeight());
            int i20 = 0;
            boolean z14 = false;
            while (i20 < size2) {
                ConstraintWidget constraintWidget2 = this.mVariableDimensionsWidgets.get(i20);
                if (!(constraintWidget2 instanceof VirtualLayout)) {
                    i13 = optimizationLevel;
                    i15 = width;
                    i14 = height;
                } else {
                    int width2 = constraintWidget2.getWidth();
                    i13 = optimizationLevel;
                    int height2 = constraintWidget2.getHeight();
                    i15 = width;
                    boolean measure = z14 | measure(measurer2, constraintWidget2, true);
                    if (constraintWidgetContainer3.mMetrics != null) {
                        z6 = measure;
                        i14 = height;
                        constraintWidgetContainer3.mMetrics.measuredMatchWidgets++;
                    } else {
                        z6 = measure;
                        i14 = height;
                    }
                    int width3 = constraintWidget2.getWidth();
                    int height3 = constraintWidget2.getHeight();
                    if (width3 != width2) {
                        constraintWidget2.setWidth(width3);
                        if (z12 && constraintWidget2.getRight() > max) {
                            max = Math.max(max, constraintWidget2.getRight() + constraintWidget2.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                        }
                        z7 = true;
                    } else {
                        z7 = z6;
                    }
                    if (height3 != height2) {
                        constraintWidget2.setHeight(height3);
                        if (z13 && constraintWidget2.getBottom() > max2) {
                            max2 = Math.max(max2, constraintWidget2.getBottom() + constraintWidget2.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                        }
                        z7 = true;
                    }
                    z14 = z7 | ((VirtualLayout) constraintWidget2).needSolverPass();
                }
                i20++;
                optimizationLevel = i13;
                width = i15;
                height = i14;
            }
            int i21 = optimizationLevel;
            int i22 = width;
            int i23 = height;
            int i24 = 0;
            int i25 = 2;
            while (i24 < i25) {
                int i26 = 0;
                while (i26 < size2) {
                    ConstraintWidget constraintWidget3 = this.mVariableDimensionsWidgets.get(i26);
                    if ((!(constraintWidget3 instanceof Helper) || (constraintWidget3 instanceof VirtualLayout)) && !(constraintWidget3 instanceof Guideline) && constraintWidget3.getVisibility() != 8 && ((!z11 || !constraintWidget3.horizontalRun.dimension.resolved || !constraintWidget3.verticalRun.dimension.resolved) && !(constraintWidget3 instanceof VirtualLayout))) {
                        int width4 = constraintWidget3.getWidth();
                        int height4 = constraintWidget3.getHeight();
                        int baselineDistance = constraintWidget3.getBaselineDistance();
                        z5 = z11;
                        z2 |= measure(measurer2, constraintWidget3, true);
                        if (constraintWidgetContainer3.mMetrics != null) {
                            i12 = size2;
                            measurer = measurer2;
                            constraintWidgetContainer3.mMetrics.measuredMatchWidgets++;
                        } else {
                            i12 = size2;
                            measurer = measurer2;
                        }
                        int width5 = constraintWidget3.getWidth();
                        int height5 = constraintWidget3.getHeight();
                        if (width5 != width4) {
                            constraintWidget3.setWidth(width5);
                            if (z12 && constraintWidget3.getRight() > max) {
                                max = Math.max(max, constraintWidget3.getRight() + constraintWidget3.getAnchor(ConstraintAnchor.Type.RIGHT).getMargin());
                            }
                            z2 = true;
                        }
                        if (height5 != height4) {
                            constraintWidget3.setHeight(height5);
                            if (z13 && constraintWidget3.getBottom() > max2) {
                                max2 = Math.max(max2, constraintWidget3.getBottom() + constraintWidget3.getAnchor(ConstraintAnchor.Type.BOTTOM).getMargin());
                            }
                            z2 = true;
                        }
                        if (constraintWidget3.hasBaseline() && baselineDistance != constraintWidget3.getBaselineDistance()) {
                            z2 = true;
                        }
                    } else {
                        z5 = z11;
                        i12 = size2;
                        measurer = measurer2;
                    }
                    i26++;
                    size2 = i12;
                    z11 = z5;
                    measurer2 = measurer;
                }
                boolean z15 = z11;
                int i27 = size2;
                Measurer measurer3 = measurer2;
                if (!z2) {
                    break;
                }
                solveLinearSystem(constraintWidgetContainer3, "intermediate pass", i22, i23);
                i24++;
                z11 = z15;
                measurer2 = measurer3;
                i25 = 2;
                z14 = false;
                size2 = i27;
            }
            int i28 = i22;
            int i29 = i23;
            if (z2) {
                solveLinearSystem(constraintWidgetContainer3, "2nd pass", i28, i29);
                if (constraintWidgetContainer2.getWidth() < max) {
                    constraintWidgetContainer3.setWidth(max);
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (constraintWidgetContainer2.getHeight() < max2) {
                    constraintWidgetContainer3.setHeight(max2);
                    z4 = true;
                } else {
                    z4 = z3;
                }
                if (z4) {
                    solveLinearSystem(constraintWidgetContainer3, "3rd pass", i28, i29);
                }
            }
            i11 = i21;
        } else {
            i11 = optimizationLevel;
        }
        constraintWidgetContainer3.setOptimizationLevel(i11);
        return 0;
    }

    private boolean measure(Measurer measurer, ConstraintWidget constraintWidget, boolean z) {
        this.mMeasure.horizontalBehavior = constraintWidget.getHorizontalDimensionBehaviour();
        this.mMeasure.verticalBehavior = constraintWidget.getVerticalDimensionBehaviour();
        this.mMeasure.horizontalDimension = constraintWidget.getWidth();
        this.mMeasure.verticalDimension = constraintWidget.getHeight();
        this.mMeasure.measuredNeedsSolverPass = false;
        this.mMeasure.useCurrentDimensions = z;
        boolean z2 = this.mMeasure.horizontalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z3 = this.mMeasure.verticalBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        boolean z4 = z2 && constraintWidget.mDimensionRatio > 0.0f;
        boolean z5 = z3 && constraintWidget.mDimensionRatio > 0.0f;
        if (z4 && constraintWidget.mResolvedMatchConstraintDefault[0] == 4) {
            this.mMeasure.horizontalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (z5 && constraintWidget.mResolvedMatchConstraintDefault[1] == 4) {
            this.mMeasure.verticalBehavior = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        measurer.measure(constraintWidget, this.mMeasure);
        constraintWidget.setWidth(this.mMeasure.measuredWidth);
        constraintWidget.setHeight(this.mMeasure.measuredHeight);
        constraintWidget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        constraintWidget.setBaselineDistance(this.mMeasure.measuredBaseline);
        this.mMeasure.useCurrentDimensions = false;
        return this.mMeasure.measuredNeedsSolverPass;
    }
}
