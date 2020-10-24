package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

public class Direct {
    private static final boolean APPLY_MATCH_PARENT = false;
    private static final boolean DEBUG = false;
    private static BasicMeasure.Measure measure = new BasicMeasure.Measure();

    public static void solvingPass(ConstraintWidgetContainer constraintWidgetContainer, BasicMeasure.Measurer measurer) {
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = constraintWidgetContainer.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = constraintWidgetContainer.getVerticalDimensionBehaviour();
        constraintWidgetContainer.resetFinalResolution();
        ArrayList<ConstraintWidget> children = constraintWidgetContainer.getChildren();
        int size = children.size();
        for (int i = 0; i < size; i++) {
            children.get(i).resetFinalResolution();
        }
        if (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalHorizontal(0, constraintWidgetContainer.getWidth());
        } else {
            constraintWidgetContainer.setFinalLeft(0);
        }
        boolean z = false;
        boolean z2 = false;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = children.get(i2);
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                if (guideline.getOrientation() == 1) {
                    if (guideline.getRelativeBegin() != -1) {
                        guideline.setFinalValue(guideline.getRelativeBegin());
                    } else if (guideline.getRelativeEnd() != -1 && constraintWidgetContainer.isResolvedHorizontally()) {
                        guideline.setFinalValue(constraintWidgetContainer.getWidth() - guideline.getRelativeEnd());
                    } else if (constraintWidgetContainer.isResolvedHorizontally()) {
                        guideline.setFinalValue((int) ((guideline.getRelativePercent() * ((float) constraintWidgetContainer.getWidth())) + 0.5f));
                    }
                    z = true;
                }
            } else if ((constraintWidget instanceof Barrier) && ((Barrier) constraintWidget).getOrientation() == 0) {
                z2 = true;
            }
        }
        if (z) {
            for (int i3 = 0; i3 < size; i3++) {
                ConstraintWidget constraintWidget2 = children.get(i3);
                if (constraintWidget2 instanceof Guideline) {
                    Guideline guideline2 = (Guideline) constraintWidget2;
                    if (guideline2.getOrientation() == 1) {
                        horizontalSolvingPass(guideline2, measurer);
                    }
                }
            }
        }
        horizontalSolvingPass(constraintWidgetContainer, measurer);
        if (z2) {
            for (int i4 = 0; i4 < size; i4++) {
                ConstraintWidget constraintWidget3 = children.get(i4);
                if (constraintWidget3 instanceof Barrier) {
                    Barrier barrier = (Barrier) constraintWidget3;
                    if (barrier.getOrientation() == 0) {
                        solveBarrier(barrier, measurer, 0);
                    }
                }
            }
        }
        if (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalVertical(0, constraintWidgetContainer.getHeight());
        } else {
            constraintWidgetContainer.setFinalTop(0);
        }
        boolean z3 = false;
        boolean z4 = false;
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget4 = children.get(i5);
            if (constraintWidget4 instanceof Guideline) {
                Guideline guideline3 = (Guideline) constraintWidget4;
                if (guideline3.getOrientation() == 0) {
                    if (guideline3.getRelativeBegin() != -1) {
                        guideline3.setFinalValue(guideline3.getRelativeBegin());
                    } else if (guideline3.getRelativeEnd() != -1 && constraintWidgetContainer.isResolvedVertically()) {
                        guideline3.setFinalValue(constraintWidgetContainer.getHeight() - guideline3.getRelativeEnd());
                    } else if (constraintWidgetContainer.isResolvedVertically()) {
                        guideline3.setFinalValue((int) ((guideline3.getRelativePercent() * ((float) constraintWidgetContainer.getHeight())) + 0.5f));
                    }
                    z3 = true;
                }
            } else if ((constraintWidget4 instanceof Barrier) && ((Barrier) constraintWidget4).getOrientation() == 1) {
                z4 = true;
            }
        }
        if (z3) {
            for (int i6 = 0; i6 < size; i6++) {
                ConstraintWidget constraintWidget5 = children.get(i6);
                if (constraintWidget5 instanceof Guideline) {
                    Guideline guideline4 = (Guideline) constraintWidget5;
                    if (guideline4.getOrientation() == 0) {
                        verticalSolvingPass(guideline4, measurer);
                    }
                }
            }
        }
        verticalSolvingPass(constraintWidgetContainer, measurer);
        if (z4) {
            for (int i7 = 0; i7 < size; i7++) {
                ConstraintWidget constraintWidget6 = children.get(i7);
                if (constraintWidget6 instanceof Barrier) {
                    Barrier barrier2 = (Barrier) constraintWidget6;
                    if (barrier2.getOrientation() == 1) {
                        solveBarrier(barrier2, measurer, 1);
                    }
                }
            }
        }
        for (int i8 = 0; i8 < size; i8++) {
            ConstraintWidget constraintWidget7 = children.get(i8);
            if (constraintWidget7.isMeasureRequested() && canMeasure(constraintWidget7)) {
                ConstraintWidgetContainer.measure(constraintWidget7, measurer, measure, false);
                horizontalSolvingPass(constraintWidget7, measurer);
                verticalSolvingPass(constraintWidget7, measurer);
            }
        }
    }

    private static void solveBarrier(Barrier barrier, BasicMeasure.Measurer measurer, int i) {
        if (!barrier.allSolved()) {
            return;
        }
        if (i == 0) {
            horizontalSolvingPass(barrier, measurer);
        } else {
            verticalSolvingPass(barrier, measurer);
        }
    }

    private static void horizontalSolvingPass(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer) {
        if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && canMeasure(constraintWidget)) {
            ConstraintWidgetContainer.measure(constraintWidget, measurer, new BasicMeasure.Measure(), false);
        }
        ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT);
        ConstraintAnchor anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        if (anchor.getDependents() != null && anchor.hasFinalValue()) {
            Iterator<ConstraintAnchor> it = anchor.getDependents().iterator();
            while (it.hasNext()) {
                ConstraintAnchor next = it.next();
                ConstraintWidget constraintWidget2 = next.mOwner;
                boolean canMeasure = canMeasure(constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && canMeasure) {
                    ConstraintWidgetContainer.measure(constraintWidget2, measurer, new BasicMeasure.Measure(), false);
                }
                if (constraintWidget2.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                    if (!constraintWidget2.isMeasureRequested()) {
                        if (next == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget == null) {
                            int margin = constraintWidget2.mLeft.getMargin() + finalValue;
                            constraintWidget2.setFinalHorizontal(margin, constraintWidget2.getWidth() + margin);
                            horizontalSolvingPass(constraintWidget2, measurer);
                        } else if (next == constraintWidget2.mRight && constraintWidget2.mLeft.mTarget == null) {
                            int margin2 = finalValue - constraintWidget2.mRight.getMargin();
                            constraintWidget2.setFinalHorizontal(margin2 - constraintWidget2.getWidth(), margin2);
                            horizontalSolvingPass(constraintWidget2, measurer);
                        } else if (next == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget != null && constraintWidget2.mRight.mTarget.hasFinalValue() && !constraintWidget2.isInHorizontalChain()) {
                            solveHorizontalCenterConstraints(measurer, constraintWidget2);
                        }
                    }
                } else if (constraintWidget2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((constraintWidget2.getVisibility() == 8 || (constraintWidget2.mMatchConstraintDefaultWidth == 0 && constraintWidget2.getDimensionRatio() == 0.0f)) && !constraintWidget2.isInHorizontalChain() && !constraintWidget2.isInVirtualLayout())) {
                    if (((next == constraintWidget2.mLeft && constraintWidget2.mRight.mTarget != null && constraintWidget2.mRight.mTarget.hasFinalValue()) || (next == constraintWidget2.mRight && constraintWidget2.mLeft.mTarget != null && constraintWidget2.mLeft.mTarget.hasFinalValue())) && !constraintWidget2.isInHorizontalChain()) {
                        solveHorizontalMatchConstraint(constraintWidget, measurer, constraintWidget2);
                    }
                }
            }
        }
        if (!(constraintWidget instanceof Guideline) && anchor2.getDependents() != null && anchor2.hasFinalValue()) {
            Iterator<ConstraintAnchor> it2 = anchor2.getDependents().iterator();
            while (it2.hasNext()) {
                ConstraintAnchor next2 = it2.next();
                ConstraintWidget constraintWidget3 = next2.mOwner;
                boolean canMeasure2 = canMeasure(constraintWidget3);
                if (constraintWidget3.isMeasureRequested() && canMeasure2) {
                    ConstraintWidgetContainer.measure(constraintWidget3, measurer, new BasicMeasure.Measure(), false);
                }
                boolean z = (next2 == constraintWidget3.mLeft && constraintWidget3.mRight.mTarget != null && constraintWidget3.mRight.mTarget.hasFinalValue()) || (next2 == constraintWidget3.mRight && constraintWidget3.mLeft.mTarget != null && constraintWidget3.mLeft.mTarget.hasFinalValue());
                if (constraintWidget3.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure2) {
                    if (!constraintWidget3.isMeasureRequested()) {
                        if (next2 == constraintWidget3.mLeft && constraintWidget3.mRight.mTarget == null) {
                            int margin3 = constraintWidget3.mLeft.getMargin() + finalValue2;
                            constraintWidget3.setFinalHorizontal(margin3, constraintWidget3.getWidth() + margin3);
                            horizontalSolvingPass(constraintWidget3, measurer);
                        } else if (next2 == constraintWidget3.mRight && constraintWidget3.mLeft.mTarget == null) {
                            int margin4 = finalValue2 - constraintWidget3.mRight.getMargin();
                            constraintWidget3.setFinalHorizontal(margin4 - constraintWidget3.getWidth(), margin4);
                            horizontalSolvingPass(constraintWidget3, measurer);
                        } else if (z && !constraintWidget3.isInHorizontalChain()) {
                            solveHorizontalCenterConstraints(measurer, constraintWidget3);
                        }
                    }
                } else if (constraintWidget3.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((constraintWidget3.getVisibility() == 8 || (constraintWidget3.mMatchConstraintDefaultWidth == 0 && constraintWidget3.getDimensionRatio() == 0.0f)) && !constraintWidget3.isInHorizontalChain() && !constraintWidget3.isInVirtualLayout() && z && !constraintWidget3.isInHorizontalChain())) {
                    solveHorizontalMatchConstraint(constraintWidget, measurer, constraintWidget3);
                }
            }
        }
    }

    private static void verticalSolvingPass(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer) {
        if (!(constraintWidget instanceof ConstraintWidgetContainer) && constraintWidget.isMeasureRequested() && canMeasure(constraintWidget)) {
            ConstraintWidgetContainer.measure(constraintWidget, measurer, new BasicMeasure.Measure(), false);
        }
        ConstraintAnchor anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.TOP);
        ConstraintAnchor anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
        int finalValue = anchor.getFinalValue();
        int finalValue2 = anchor2.getFinalValue();
        if (anchor.getDependents() != null && anchor.hasFinalValue()) {
            Iterator<ConstraintAnchor> it = anchor.getDependents().iterator();
            while (it.hasNext()) {
                ConstraintAnchor next = it.next();
                ConstraintWidget constraintWidget2 = next.mOwner;
                boolean canMeasure = canMeasure(constraintWidget2);
                if (constraintWidget2.isMeasureRequested() && canMeasure) {
                    ConstraintWidgetContainer.measure(constraintWidget2, measurer, new BasicMeasure.Measure(), false);
                }
                if (constraintWidget2.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure) {
                    if (!constraintWidget2.isMeasureRequested()) {
                        if (next == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget == null) {
                            int margin = constraintWidget2.mTop.getMargin() + finalValue;
                            constraintWidget2.setFinalVertical(margin, constraintWidget2.getHeight() + margin);
                            verticalSolvingPass(constraintWidget2, measurer);
                        } else if (next == constraintWidget2.mBottom && constraintWidget2.mBottom.mTarget == null) {
                            int margin2 = finalValue - constraintWidget2.mBottom.getMargin();
                            constraintWidget2.setFinalVertical(margin2 - constraintWidget2.getHeight(), margin2);
                            verticalSolvingPass(constraintWidget2, measurer);
                        } else if (next == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget != null && constraintWidget2.mBottom.mTarget.hasFinalValue()) {
                            solveVerticalCenterConstraints(measurer, constraintWidget2);
                        }
                    }
                } else if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((constraintWidget2.getVisibility() == 8 || (constraintWidget2.mMatchConstraintDefaultHeight == 0 && constraintWidget2.getDimensionRatio() == 0.0f)) && !constraintWidget2.isInVerticalChain() && !constraintWidget2.isInVirtualLayout())) {
                    if (((next == constraintWidget2.mTop && constraintWidget2.mBottom.mTarget != null && constraintWidget2.mBottom.mTarget.hasFinalValue()) || (next == constraintWidget2.mBottom && constraintWidget2.mTop.mTarget != null && constraintWidget2.mTop.mTarget.hasFinalValue())) && !constraintWidget2.isInVerticalChain()) {
                        solveVerticalMatchConstraint(constraintWidget, measurer, constraintWidget2);
                    }
                }
            }
        }
        if (!(constraintWidget instanceof Guideline)) {
            if (anchor2.getDependents() != null && anchor2.hasFinalValue()) {
                Iterator<ConstraintAnchor> it2 = anchor2.getDependents().iterator();
                while (it2.hasNext()) {
                    ConstraintAnchor next2 = it2.next();
                    ConstraintWidget constraintWidget3 = next2.mOwner;
                    boolean canMeasure2 = canMeasure(constraintWidget3);
                    if (constraintWidget3.isMeasureRequested() && canMeasure2) {
                        ConstraintWidgetContainer.measure(constraintWidget3, measurer, new BasicMeasure.Measure(), false);
                    }
                    boolean z = (next2 == constraintWidget3.mTop && constraintWidget3.mBottom.mTarget != null && constraintWidget3.mBottom.mTarget.hasFinalValue()) || (next2 == constraintWidget3.mBottom && constraintWidget3.mTop.mTarget != null && constraintWidget3.mTop.mTarget.hasFinalValue());
                    if (constraintWidget3.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure2) {
                        if (!constraintWidget3.isMeasureRequested()) {
                            if (next2 == constraintWidget3.mTop && constraintWidget3.mBottom.mTarget == null) {
                                int margin3 = constraintWidget3.mTop.getMargin() + finalValue2;
                                constraintWidget3.setFinalVertical(margin3, constraintWidget3.getHeight() + margin3);
                                verticalSolvingPass(constraintWidget3, measurer);
                            } else if (next2 == constraintWidget3.mBottom && constraintWidget3.mTop.mTarget == null) {
                                int margin4 = finalValue2 - constraintWidget3.mBottom.getMargin();
                                constraintWidget3.setFinalVertical(margin4 - constraintWidget3.getHeight(), margin4);
                                verticalSolvingPass(constraintWidget3, measurer);
                            } else if (z && !constraintWidget3.isInVerticalChain()) {
                                solveVerticalCenterConstraints(measurer, constraintWidget3);
                            }
                        }
                    } else if (constraintWidget3.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && ((constraintWidget3.getVisibility() == 8 || (constraintWidget3.mMatchConstraintDefaultHeight == 0 && constraintWidget3.getDimensionRatio() == 0.0f)) && !constraintWidget3.isInVerticalChain() && !constraintWidget3.isInVirtualLayout() && z && !constraintWidget3.isInVerticalChain())) {
                        solveVerticalMatchConstraint(constraintWidget, measurer, constraintWidget3);
                    }
                }
            }
            ConstraintAnchor anchor3 = constraintWidget.getAnchor(ConstraintAnchor.Type.BASELINE);
            if (anchor3.getDependents() != null && anchor3.hasFinalValue()) {
                int finalValue3 = anchor3.getFinalValue();
                Iterator<ConstraintAnchor> it3 = anchor3.getDependents().iterator();
                while (it3.hasNext()) {
                    ConstraintAnchor next3 = it3.next();
                    ConstraintWidget constraintWidget4 = next3.mOwner;
                    boolean canMeasure3 = canMeasure(constraintWidget4);
                    if (constraintWidget4.isMeasureRequested() && canMeasure3) {
                        ConstraintWidgetContainer.measure(constraintWidget4, measurer, new BasicMeasure.Measure(), false);
                    }
                    if ((constraintWidget4.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || canMeasure3) && !constraintWidget4.isMeasureRequested() && next3 == constraintWidget4.mBaseline) {
                        constraintWidget4.setFinalBaseline(finalValue3);
                        verticalSolvingPass(constraintWidget4, measurer);
                    }
                }
            }
        }
    }

    private static void solveHorizontalCenterConstraints(BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget) {
        float horizontalBiasPercent = constraintWidget.getHorizontalBiasPercent();
        int finalValue = constraintWidget.mLeft.mTarget.getFinalValue();
        int finalValue2 = constraintWidget.mRight.mTarget.getFinalValue();
        int margin = constraintWidget.mLeft.getMargin() + finalValue;
        int margin2 = finalValue2 - constraintWidget.mRight.getMargin();
        if (finalValue == finalValue2) {
            horizontalBiasPercent = 0.5f;
        } else {
            finalValue = margin;
            finalValue2 = margin2;
        }
        int width = constraintWidget.getWidth();
        int i = (finalValue2 - finalValue) - width;
        if (finalValue > finalValue2) {
            i = (finalValue - finalValue2) - width;
        }
        if (((ConstraintWidgetContainer) constraintWidget.getParent()).isRtl()) {
            horizontalBiasPercent = 1.0f - horizontalBiasPercent;
        }
        int i2 = ((int) ((horizontalBiasPercent * ((float) i)) + 0.5f)) + finalValue;
        int i3 = i2 + width;
        if (finalValue > finalValue2) {
            i3 = i2 - width;
        }
        constraintWidget.setFinalHorizontal(i2, i3);
        horizontalSolvingPass(constraintWidget, measurer);
    }

    private static void solveVerticalCenterConstraints(BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget) {
        float verticalBiasPercent = constraintWidget.getVerticalBiasPercent();
        int finalValue = constraintWidget.mTop.mTarget.getFinalValue();
        int finalValue2 = constraintWidget.mBottom.mTarget.getFinalValue();
        int margin = constraintWidget.mTop.getMargin() + finalValue;
        int margin2 = finalValue2 - constraintWidget.mBottom.getMargin();
        if (finalValue == finalValue2) {
            verticalBiasPercent = 0.5f;
        } else {
            finalValue = margin;
            finalValue2 = margin2;
        }
        int height = constraintWidget.getHeight();
        int i = (finalValue2 - finalValue) - height;
        if (finalValue > finalValue2) {
            i = (finalValue - finalValue2) - height;
        }
        int i2 = (int) ((verticalBiasPercent * ((float) i)) + 0.5f);
        int i3 = finalValue + i2;
        int i4 = i3 + height;
        if (finalValue > finalValue2) {
            i3 = finalValue - i2;
            i4 = i3 - height;
        }
        constraintWidget.setFinalVertical(i3, i4);
        verticalSolvingPass(constraintWidget, measurer);
    }

    private static void solveHorizontalMatchConstraint(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget2) {
        int i;
        float horizontalBiasPercent = constraintWidget2.getHorizontalBiasPercent();
        int finalValue = constraintWidget2.mLeft.mTarget.getFinalValue() + constraintWidget2.mLeft.getMargin();
        int finalValue2 = constraintWidget2.mRight.mTarget.getFinalValue() - constraintWidget2.mRight.getMargin();
        if (finalValue2 >= finalValue) {
            int width = constraintWidget2.getWidth();
            if (constraintWidget2.getVisibility() != 8) {
                if (constraintWidget2.mMatchConstraintDefaultWidth == 2) {
                    if (constraintWidget instanceof ConstraintWidgetContainer) {
                        i = constraintWidget.getWidth();
                    } else {
                        i = constraintWidget.getParent().getWidth();
                    }
                    width = (int) (constraintWidget2.getHorizontalBiasPercent() * 0.5f * ((float) i));
                } else if (constraintWidget2.mMatchConstraintDefaultWidth == 0) {
                    width = finalValue2 - finalValue;
                }
                width = Math.max(constraintWidget2.mMatchConstraintMinWidth, width);
                if (constraintWidget2.mMatchConstraintMaxWidth > 0) {
                    width = Math.min(constraintWidget2.mMatchConstraintMaxWidth, width);
                }
            }
            int i2 = finalValue + ((int) ((horizontalBiasPercent * ((float) ((finalValue2 - finalValue) - width))) + 0.5f));
            constraintWidget2.setFinalHorizontal(i2, width + i2);
            horizontalSolvingPass(constraintWidget2, measurer);
        }
    }

    private static void solveVerticalMatchConstraint(ConstraintWidget constraintWidget, BasicMeasure.Measurer measurer, ConstraintWidget constraintWidget2) {
        int i;
        float verticalBiasPercent = constraintWidget2.getVerticalBiasPercent();
        int finalValue = constraintWidget2.mTop.mTarget.getFinalValue() + constraintWidget2.mTop.getMargin();
        int finalValue2 = constraintWidget2.mBottom.mTarget.getFinalValue() - constraintWidget2.mBottom.getMargin();
        if (finalValue2 >= finalValue) {
            int height = constraintWidget2.getHeight();
            if (constraintWidget2.getVisibility() != 8) {
                if (constraintWidget2.mMatchConstraintDefaultHeight == 2) {
                    if (constraintWidget instanceof ConstraintWidgetContainer) {
                        i = constraintWidget.getHeight();
                    } else {
                        i = constraintWidget.getParent().getHeight();
                    }
                    height = (int) (verticalBiasPercent * 0.5f * ((float) i));
                } else if (constraintWidget2.mMatchConstraintDefaultHeight == 0) {
                    height = finalValue2 - finalValue;
                }
                height = Math.max(constraintWidget2.mMatchConstraintMinHeight, height);
                if (constraintWidget2.mMatchConstraintMaxHeight > 0) {
                    height = Math.min(constraintWidget2.mMatchConstraintMaxHeight, height);
                }
            }
            int i2 = finalValue + ((int) ((verticalBiasPercent * ((float) ((finalValue2 - finalValue) - height))) + 0.5f));
            constraintWidget2.setFinalVertical(i2, height + i2);
            verticalSolvingPass(constraintWidget2, measurer);
        }
    }

    private static boolean canMeasure(ConstraintWidget constraintWidget) {
        ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = constraintWidget.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour = constraintWidget.getVerticalDimensionBehaviour();
        ConstraintWidgetContainer constraintWidgetContainer = constraintWidget.getParent() != null ? (ConstraintWidgetContainer) constraintWidget.getParent() : null;
        if (constraintWidgetContainer != null) {
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour2 = constraintWidgetContainer.getHorizontalDimensionBehaviour();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        if (constraintWidgetContainer != null) {
            ConstraintWidget.DimensionBehaviour verticalDimensionBehaviour2 = constraintWidgetContainer.getVerticalDimensionBehaviour();
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
        }
        boolean z = horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (horizontalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0 && constraintWidget.mDimensionRatio == 0.0f && constraintWidget.hasDanglingDimension(0)) || constraintWidget.isResolvedHorizontally();
        boolean z2 = verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.FIXED || verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (verticalDimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0 && constraintWidget.mDimensionRatio == 0.0f && constraintWidget.hasDanglingDimension(1)) || constraintWidget.isResolvedVertically();
        if (constraintWidget.mDimensionRatio > 0.0f && (z || z2)) {
            return true;
        }
        if (!z || !z2) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0054, code lost:
        r6 = r6.mTarget.getFinalValue() + r3.mListAnchors[r21].getMargin();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01d0, code lost:
        if (r4.mListAnchors[r21].mTarget.mOwner == r1) goto L_0x01d4;
     */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0147  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean solveChain(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r18, androidx.constraintlayout.solver.LinearSystem r19, int r20, int r21, androidx.constraintlayout.solver.widgets.ChainHead r22, boolean r23, boolean r24, boolean r25) {
        /*
            r0 = 0
            if (r25 == 0) goto L_0x0004
            return r0
        L_0x0004:
            if (r20 != 0) goto L_0x000d
            boolean r1 = r18.isResolvedHorizontally()
            if (r1 != 0) goto L_0x0014
            return r0
        L_0x000d:
            boolean r1 = r18.isResolvedVertically()
            if (r1 != 0) goto L_0x0014
            return r0
        L_0x0014:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r22.getFirst()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r22.getLast()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r22.getFirstVisibleWidget()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r22.getLastVisibleWidget()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r22.getHead()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r1.mListAnchors
            r6 = r6[r21]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            int r7 = r21 + 1
            r2 = r2[r7]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r6.mTarget
            if (r8 == 0) goto L_0x0224
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r2.mTarget
            if (r8 != 0) goto L_0x003c
            goto L_0x0224
        L_0x003c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r6.mTarget
            boolean r8 = r8.hasFinalValue()
            if (r8 == 0) goto L_0x0224
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r2.mTarget
            boolean r8 = r8.hasFinalValue()
            if (r8 != 0) goto L_0x004e
            goto L_0x0224
        L_0x004e:
            if (r3 == 0) goto L_0x0224
            if (r4 != 0) goto L_0x0054
            goto L_0x0224
        L_0x0054:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            int r6 = r6.getFinalValue()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r8 = r3.mListAnchors
            r8 = r8[r21]
            int r8 = r8.getMargin()
            int r6 = r6 + r8
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            int r2 = r2.getFinalValue()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r8 = r4.mListAnchors
            r8 = r8[r7]
            int r8 = r8.getMargin()
            int r2 = r2 - r8
            int r8 = r2 - r6
            if (r8 > 0) goto L_0x0077
            return r0
        L_0x0077:
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r9 = new androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure
            r9.<init>()
            r12 = r1
            r10 = 0
            r11 = 0
            r13 = 0
            r14 = 0
        L_0x0081:
            r16 = 0
            if (r10 != 0) goto L_0x00fa
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r15 = r12.mListAnchors
            r15 = r15[r21]
            boolean r15 = canMeasure(r12)
            if (r15 != 0) goto L_0x0090
            return r0
        L_0x0090:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r15 = r12.mListDimensionBehaviors
            r15 = r15[r20]
            r17 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r1) goto L_0x009b
            return r0
        L_0x009b:
            boolean r1 = r12.isMeasureRequested()
            if (r1 == 0) goto L_0x00a8
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r1 = r18.getMeasurer()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer.measure(r12, r1, r9, r0)
        L_0x00a8:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r21]
            int r1 = r1.getMargin()
            int r14 = r14 + r1
            if (r20 != 0) goto L_0x00b8
            int r1 = r12.getWidth()
            goto L_0x00bc
        L_0x00b8:
            int r1 = r12.getHeight()
        L_0x00bc:
            int r14 = r14 + r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r7]
            int r1 = r1.getMargin()
            int r14 = r14 + r1
            int r13 = r13 + 1
            int r1 = r12.getVisibility()
            r15 = 8
            if (r1 == r15) goto L_0x00d2
            int r11 = r11 + 1
        L_0x00d2:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r7]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x00f1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r1.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r15 = r1.mListAnchors
            r15 = r15[r21]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mTarget
            if (r15 == 0) goto L_0x00f1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r15 = r1.mListAnchors
            r15 = r15[r21]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r15.mOwner
            if (r15 == r12) goto L_0x00ef
            goto L_0x00f1
        L_0x00ef:
            r16 = r1
        L_0x00f1:
            if (r16 == 0) goto L_0x00f6
            r12 = r16
            goto L_0x00f7
        L_0x00f6:
            r10 = 1
        L_0x00f7:
            r1 = r17
            goto L_0x0081
        L_0x00fa:
            r17 = r1
            if (r11 != 0) goto L_0x00ff
            return r0
        L_0x00ff:
            if (r11 == r13) goto L_0x0102
            return r0
        L_0x0102:
            if (r8 >= r14) goto L_0x0105
            return r0
        L_0x0105:
            int r8 = r8 - r14
            r1 = 2
            if (r23 == 0) goto L_0x010e
            int r9 = r11 + 1
            int r8 = r8 / r9
        L_0x010c:
            r9 = 1
            goto L_0x0115
        L_0x010e:
            if (r24 == 0) goto L_0x010c
            if (r11 <= r1) goto L_0x010c
            int r8 = r8 / r11
            r9 = 1
            int r8 = r8 - r9
        L_0x0115:
            if (r11 != r9) goto L_0x0147
            if (r20 != 0) goto L_0x011e
            float r0 = r5.getHorizontalBiasPercent()
            goto L_0x0122
        L_0x011e:
            float r0 = r5.getVerticalBiasPercent()
        L_0x0122:
            r1 = 1056964608(0x3f000000, float:0.5)
            float r2 = (float) r6
            float r2 = r2 + r1
            float r1 = (float) r8
            float r1 = r1 * r0
            float r2 = r2 + r1
            int r0 = (int) r2
            if (r20 != 0) goto L_0x0136
            int r1 = r3.getWidth()
            int r1 = r1 + r0
            r3.setFinalHorizontal(r0, r1)
            goto L_0x013e
        L_0x0136:
            int r1 = r3.getHeight()
            int r1 = r1 + r0
            r3.setFinalVertical(r0, r1)
        L_0x013e:
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r0 = r18.getMeasurer()
            horizontalSolvingPass(r3, r0)
            r0 = 1
            return r0
        L_0x0147:
            if (r23 == 0) goto L_0x01dc
            int r6 = r6 + r8
            r1 = r17
            r9 = 0
        L_0x014d:
            if (r9 != 0) goto L_0x0223
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r1.mListAnchors
            r2 = r2[r21]
            int r2 = r1.getVisibility()
            r3 = 8
            if (r2 != r3) goto L_0x0175
            if (r20 != 0) goto L_0x0168
            r1.setFinalHorizontal(r6, r6)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r2 = r18.getMeasurer()
            horizontalSolvingPass(r1, r2)
            goto L_0x0172
        L_0x0168:
            r1.setFinalVertical(r6, r6)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r2 = r18.getMeasurer()
            verticalSolvingPass(r1, r2)
        L_0x0172:
            r2 = r19
            goto L_0x01b3
        L_0x0175:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r1.mListAnchors
            r2 = r2[r21]
            int r2 = r2.getMargin()
            int r6 = r6 + r2
            if (r20 != 0) goto L_0x0194
            int r2 = r1.getWidth()
            int r2 = r2 + r6
            r1.setFinalHorizontal(r6, r2)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r2 = r18.getMeasurer()
            horizontalSolvingPass(r1, r2)
            int r2 = r1.getWidth()
            goto L_0x01a7
        L_0x0194:
            int r2 = r1.getHeight()
            int r2 = r2 + r6
            r1.setFinalVertical(r6, r2)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r2 = r18.getMeasurer()
            verticalSolvingPass(r1, r2)
            int r2 = r1.getHeight()
        L_0x01a7:
            int r6 = r6 + r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r1.mListAnchors
            r2 = r2[r7]
            int r2 = r2.getMargin()
            int r6 = r6 + r2
            int r6 = r6 + r8
            goto L_0x0172
        L_0x01b3:
            r1.addToSolver(r2, r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r1.mListAnchors
            r4 = r4[r7]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x01d2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r4.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r4.mListAnchors
            r5 = r5[r21]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x01d2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r4.mListAnchors
            r5 = r5[r21]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            if (r5 == r1) goto L_0x01d4
        L_0x01d2:
            r4 = r16
        L_0x01d4:
            if (r4 == 0) goto L_0x01d9
            r1 = r4
            goto L_0x014d
        L_0x01d9:
            r9 = 1
            goto L_0x014d
        L_0x01dc:
            if (r24 == 0) goto L_0x0223
            if (r11 != r1) goto L_0x0222
            if (r20 != 0) goto L_0x0202
            int r0 = r3.getWidth()
            int r0 = r0 + r6
            r3.setFinalHorizontal(r6, r0)
            int r0 = r4.getWidth()
            int r0 = r2 - r0
            r4.setFinalHorizontal(r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r0 = r18.getMeasurer()
            horizontalSolvingPass(r3, r0)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r0 = r18.getMeasurer()
            horizontalSolvingPass(r4, r0)
            goto L_0x0221
        L_0x0202:
            int r0 = r3.getHeight()
            int r0 = r0 + r6
            r3.setFinalVertical(r6, r0)
            int r0 = r4.getHeight()
            int r0 = r2 - r0
            r4.setFinalVertical(r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r0 = r18.getMeasurer()
            verticalSolvingPass(r3, r0)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r0 = r18.getMeasurer()
            verticalSolvingPass(r4, r0)
        L_0x0221:
            r0 = 1
        L_0x0222:
            return r0
        L_0x0223:
            r0 = 1
        L_0x0224:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.Direct.solveChain(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead, boolean, boolean, boolean):boolean");
    }
}
