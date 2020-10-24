package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;

public class VerticalWidgetRun extends WidgetRun {
    public DependencyNode baseline = new DependencyNode(this);
    DimensionDependency baselineDimension = null;

    public VerticalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.type = DependencyNode.Type.TOP;
        this.end.type = DependencyNode.Type.BOTTOM;
        this.baseline.type = DependencyNode.Type.BASELINE;
        this.orientation = 1;
    }

    public String toString() {
        return "VerticalRun " + this.widget.getDebugName();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.baseline.clear();
        this.baseline.resolved = false;
        this.dimension.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultHeight == 0) {
            return true;
        }
        return false;
    }

    /* renamed from: androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType[] r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.RunType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType = r0
                androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType r1 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.RunType.START     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType r1 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.RunType.END     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType r1 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.RunType.CENTER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun.AnonymousClass1.<clinit>():void");
        }
    }

    public void update(Dependency dependency) {
        int i;
        float f;
        float f2;
        float f3;
        int i2 = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()];
        if (i2 == 1) {
            updateRunStart(dependency);
        } else if (i2 == 2) {
            updateRunEnd(dependency);
        } else if (i2 == 3) {
            updateRunCenter(dependency, this.widget.mTop, this.widget.mBottom, 1);
            return;
        }
        if (this.dimension.readyToSolve && !this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i3 = this.widget.mMatchConstraintDefaultHeight;
            if (i3 == 2) {
                ConstraintWidget parent = this.widget.getParent();
                if (parent != null && parent.verticalRun.dimension.resolved) {
                    float f4 = this.widget.mMatchConstraintPercentHeight;
                    this.dimension.resolve((int) ((((float) parent.verticalRun.dimension.value) * f4) + 0.5f));
                }
            } else if (i3 == 3 && this.widget.horizontalRun.dimension.resolved) {
                int dimensionRatioSide = this.widget.getDimensionRatioSide();
                if (dimensionRatioSide == -1) {
                    f2 = (float) this.widget.horizontalRun.dimension.value;
                    f3 = this.widget.getDimensionRatio();
                } else if (dimensionRatioSide == 0) {
                    f = ((float) this.widget.horizontalRun.dimension.value) * this.widget.getDimensionRatio();
                    i = (int) (f + 0.5f);
                    this.dimension.resolve(i);
                } else if (dimensionRatioSide != 1) {
                    i = 0;
                    this.dimension.resolve(i);
                } else {
                    f2 = (float) this.widget.horizontalRun.dimension.value;
                    f3 = this.widget.getDimensionRatio();
                }
                f = f2 / f3;
                i = (int) (f + 0.5f);
                this.dimension.resolve(i);
            }
        }
        if (this.start.readyToSolve && this.end.readyToSolve) {
            if (this.start.resolved && this.end.resolved && this.dimension.resolved) {
                return;
            }
            if (this.dimension.resolved || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth != 0 || this.widget.isInVerticalChain()) {
                if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                    int i4 = (this.end.targets.get(0).value + this.end.margin) - (this.start.targets.get(0).value + this.start.margin);
                    if (i4 < this.dimension.wrapValue) {
                        this.dimension.resolve(i4);
                    } else {
                        this.dimension.resolve(this.dimension.wrapValue);
                    }
                }
                if (this.dimension.resolved && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                    DependencyNode dependencyNode = this.start.targets.get(0);
                    DependencyNode dependencyNode2 = this.end.targets.get(0);
                    int i5 = dependencyNode.value + this.start.margin;
                    int i6 = dependencyNode2.value + this.end.margin;
                    float verticalBiasPercent = this.widget.getVerticalBiasPercent();
                    if (dependencyNode == dependencyNode2) {
                        i5 = dependencyNode.value;
                        i6 = dependencyNode2.value;
                        verticalBiasPercent = 0.5f;
                    }
                    this.start.resolve((int) (((float) i5) + 0.5f + (((float) ((i6 - i5) - this.dimension.value)) * verticalBiasPercent)));
                    this.end.resolve(this.start.value + this.dimension.value);
                    return;
                }
                return;
            }
            int i7 = this.start.targets.get(0).value + this.start.margin;
            int i8 = this.end.targets.get(0).value + this.end.margin;
            this.start.resolve(i7);
            this.end.resolve(i8);
            this.dimension.resolve(i8 - i7);
        }
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.widget.measured) {
            this.dimension.resolve(this.widget.getHeight());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getVerticalDimensionBehaviour();
            if (this.widget.hasBaseline()) {
                this.baselineDimension = new BaselineDimensionDependency(this);
            }
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.widget.getParent()) != null && parent2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int height = (parent2.getHeight() - this.widget.mTop.getMargin()) - this.widget.mBottom.getMargin();
                    addTarget(this.start, parent2.verticalRun.start, this.widget.mTop.getMargin());
                    addTarget(this.end, parent2.verticalRun.end, -this.widget.mBottom.getMargin());
                    this.dimension.resolve(height);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getHeight());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.widget.getParent()) != null && parent.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
            addTarget(this.start, parent.verticalRun.start, this.widget.mTop.getMargin());
            addTarget(this.end, parent.verticalRun.end, -this.widget.mBottom.getMargin());
            return;
        }
        if (!this.dimension.resolved || !this.widget.measured) {
            if (this.dimension.resolved || this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                this.dimension.addDependency(this);
            } else {
                int i = this.widget.mMatchConstraintDefaultHeight;
                if (i == 2) {
                    ConstraintWidget parent3 = this.widget.getParent();
                    if (parent3 != null) {
                        DimensionDependency dimensionDependency = parent3.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency);
                        dimensionDependency.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                    }
                } else if (i == 3 && !this.widget.isInVerticalChain() && this.widget.mMatchConstraintDefaultWidth != 3) {
                    DimensionDependency dimensionDependency2 = this.widget.horizontalRun.dimension;
                    this.dimension.targets.add(dimensionDependency2);
                    dimensionDependency2.dependencies.add(this.dimension);
                    this.dimension.delegateToWidgetRun = true;
                    this.dimension.dependencies.add(this.start);
                    this.dimension.dependencies.add(this.end);
                }
            }
            if (this.widget.mListAnchors[2].mTarget != null && this.widget.mListAnchors[3].mTarget != null) {
                if (this.widget.isInVerticalChain()) {
                    this.start.margin = this.widget.mListAnchors[2].getMargin();
                    this.end.margin = -this.widget.mListAnchors[3].getMargin();
                } else {
                    DependencyNode target = getTarget(this.widget.mListAnchors[2]);
                    DependencyNode target2 = getTarget(this.widget.mListAnchors[3]);
                    target.addDependency(this);
                    target2.addDependency(this);
                    this.mRunType = WidgetRun.RunType.CENTER;
                }
                if (this.widget.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.baselineDimension);
                }
            } else if (this.widget.mListAnchors[2].mTarget != null) {
                DependencyNode target3 = getTarget(this.widget.mListAnchors[2]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.widget.mListAnchors[2].getMargin());
                    addTarget(this.end, this.start, 1, this.dimension);
                    if (this.widget.hasBaseline()) {
                        addTarget(this.baseline, this.start, 1, this.baselineDimension);
                    }
                    if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.getDimensionRatio() > 0.0f && this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
                        this.dimension.targets.add(this.widget.horizontalRun.dimension);
                        this.dimension.updateDelegate = this;
                    }
                }
            } else if (this.widget.mListAnchors[3].mTarget != null) {
                DependencyNode target4 = getTarget(this.widget.mListAnchors[3]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.widget.mListAnchors[3].getMargin());
                    addTarget(this.start, this.end, -1, this.dimension);
                    if (this.widget.hasBaseline()) {
                        addTarget(this.baseline, this.start, 1, this.baselineDimension);
                    }
                }
            } else if (this.widget.mListAnchors[4].mTarget != null) {
                DependencyNode target5 = getTarget(this.widget.mListAnchors[4]);
                if (target5 != null) {
                    addTarget(this.baseline, target5, 0);
                    addTarget(this.start, this.baseline, -1, this.baselineDimension);
                    addTarget(this.end, this.start, 1, this.dimension);
                }
            } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null) {
                addTarget(this.start, this.widget.getParent().verticalRun.start, this.widget.getY());
                addTarget(this.end, this.start, 1, this.dimension);
                if (this.widget.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.baselineDimension);
                }
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.widget.getDimensionRatio() > 0.0f && this.widget.horizontalRun.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.widget.horizontalRun.dimension.dependencies.add(this.dimension);
                    this.dimension.targets.add(this.widget.horizontalRun.dimension);
                    this.dimension.updateDelegate = this;
                }
            }
            if (this.dimension.targets.size() == 0) {
                this.dimension.readyToSolve = true;
            }
        } else if (this.widget.mListAnchors[2].mTarget != null && this.widget.mListAnchors[3].mTarget != null) {
            if (this.widget.isInVerticalChain()) {
                this.start.margin = this.widget.mListAnchors[2].getMargin();
                this.end.margin = -this.widget.mListAnchors[3].getMargin();
            } else {
                DependencyNode target6 = getTarget(this.widget.mListAnchors[2]);
                if (target6 != null) {
                    addTarget(this.start, target6, this.widget.mListAnchors[2].getMargin());
                }
                DependencyNode target7 = getTarget(this.widget.mListAnchors[3]);
                if (target7 != null) {
                    addTarget(this.end, target7, -this.widget.mListAnchors[3].getMargin());
                }
                this.start.delegateToWidgetRun = true;
                this.end.delegateToWidgetRun = true;
            }
            if (this.widget.hasBaseline()) {
                addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
            }
        } else if (this.widget.mListAnchors[2].mTarget != null) {
            DependencyNode target8 = getTarget(this.widget.mListAnchors[2]);
            if (target8 != null) {
                addTarget(this.start, target8, this.widget.mListAnchors[2].getMargin());
                addTarget(this.end, this.start, this.dimension.value);
                if (this.widget.hasBaseline()) {
                    addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
                }
            }
        } else if (this.widget.mListAnchors[3].mTarget != null) {
            DependencyNode target9 = getTarget(this.widget.mListAnchors[3]);
            if (target9 != null) {
                addTarget(this.end, target9, -this.widget.mListAnchors[3].getMargin());
                addTarget(this.start, this.end, -this.dimension.value);
            }
            if (this.widget.hasBaseline()) {
                addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
            }
        } else if (this.widget.mListAnchors[4].mTarget != null) {
            DependencyNode target10 = getTarget(this.widget.mListAnchors[4]);
            if (target10 != null) {
                addTarget(this.baseline, target10, 0);
                addTarget(this.start, this.baseline, -this.widget.getBaselineDistance());
                addTarget(this.end, this.start, this.dimension.value);
            }
        } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
            addTarget(this.start, this.widget.getParent().verticalRun.start, this.widget.getY());
            addTarget(this.end, this.start, this.dimension.value);
            if (this.widget.hasBaseline()) {
                addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
            }
        }
    }

    public void applyToWidget() {
        if (this.start.resolved) {
            this.widget.setY(this.start.value);
        }
    }
}
