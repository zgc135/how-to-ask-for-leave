package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.WidgetRun;

public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    public String toString() {
        return "HorizontalRun " + this.widget.getDebugName();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
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
        this.dimension.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        if (this.widget.measured) {
            this.dimension.resolve(this.widget.getWidth());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getHorizontalDimensionBehaviour();
            if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (((parent2 = this.widget.getParent()) != null && parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
                    int width = (parent2.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    addTarget(this.start, parent2.horizontalRun.start, this.widget.mLeft.getMargin());
                    addTarget(this.end, parent2.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(width);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (((parent = this.widget.getParent()) != null && parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) || parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_PARENT)) {
            addTarget(this.start, parent.horizontalRun.start, this.widget.mLeft.getMargin());
            addTarget(this.end, parent.horizontalRun.end, -this.widget.mRight.getMargin());
            return;
        }
        if (!this.dimension.resolved || !this.widget.measured) {
            if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                int i = this.widget.mMatchConstraintDefaultWidth;
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
                } else if (i == 3) {
                    if (this.widget.mMatchConstraintDefaultHeight == 3) {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        this.widget.verticalRun.start.updateDelegate = this;
                        this.widget.verticalRun.end.updateDelegate = this;
                        this.dimension.updateDelegate = this;
                        if (this.widget.isInVerticalChain()) {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            this.widget.verticalRun.dimension.updateDelegate = this;
                            this.dimension.targets.add(this.widget.verticalRun.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            this.widget.verticalRun.end.dependencies.add(this.dimension);
                        } else if (this.widget.isInHorizontalChain()) {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                            this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                        } else {
                            this.widget.verticalRun.dimension.targets.add(this.dimension);
                        }
                    } else {
                        DimensionDependency dimensionDependency2 = this.widget.verticalRun.dimension;
                        this.dimension.targets.add(dimensionDependency2);
                        dimensionDependency2.dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        this.dimension.delegateToWidgetRun = true;
                        this.dimension.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        this.end.targets.add(this.dimension);
                    }
                }
            }
            if (this.widget.mListAnchors[0].mTarget == null || this.widget.mListAnchors[1].mTarget == null) {
                if (this.widget.mListAnchors[0].mTarget != null) {
                    DependencyNode target = getTarget(this.widget.mListAnchors[0]);
                    if (target != null) {
                        addTarget(this.start, target, this.widget.mListAnchors[0].getMargin());
                        addTarget(this.end, this.start, 1, this.dimension);
                    }
                } else if (this.widget.mListAnchors[1].mTarget != null) {
                    DependencyNode target2 = getTarget(this.widget.mListAnchors[1]);
                    if (target2 != null) {
                        addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                        addTarget(this.start, this.end, -1, this.dimension);
                    }
                } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null) {
                    addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                    addTarget(this.end, this.start, 1, this.dimension);
                }
            } else if (this.widget.isInHorizontalChain()) {
                this.start.margin = this.widget.mListAnchors[0].getMargin();
                this.end.margin = -this.widget.mListAnchors[1].getMargin();
            } else {
                DependencyNode target3 = getTarget(this.widget.mListAnchors[0]);
                DependencyNode target4 = getTarget(this.widget.mListAnchors[1]);
                target3.addDependency(this);
                target4.addDependency(this);
                this.mRunType = WidgetRun.RunType.CENTER;
            }
        } else if (this.widget.mListAnchors[0].mTarget == null || this.widget.mListAnchors[1].mTarget == null) {
            if (this.widget.mListAnchors[0].mTarget != null) {
                DependencyNode target5 = getTarget(this.widget.mListAnchors[0]);
                if (target5 != null) {
                    addTarget(this.start, target5, this.widget.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, this.dimension.value);
                }
            } else if (this.widget.mListAnchors[1].mTarget != null) {
                DependencyNode target6 = getTarget(this.widget.mListAnchors[1]);
                if (target6 != null) {
                    addTarget(this.end, target6, -this.widget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -this.dimension.value);
                }
            } else if (!(this.widget instanceof Helper) && this.widget.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
                addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                addTarget(this.end, this.start, this.dimension.value);
            }
        } else if (this.widget.isInHorizontalChain()) {
            this.start.margin = this.widget.mListAnchors[0].getMargin();
            this.end.margin = -this.widget.mListAnchors[1].getMargin();
        } else {
            DependencyNode target7 = getTarget(this.widget.mListAnchors[0]);
            if (target7 != null) {
                addTarget(this.start, target7, this.widget.mListAnchors[0].getMargin());
            }
            DependencyNode target8 = getTarget(this.widget.mListAnchors[1]);
            if (target8 != null) {
                addTarget(this.end, target8, -this.widget.mListAnchors[1].getMargin());
            }
            this.start.delegateToWidgetRun = true;
            this.end.delegateToWidgetRun = true;
        }
    }

    private void computeInsetRatio(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        if (i5 == -1) {
            int i8 = (int) ((((float) i7) * f) + 0.5f);
            int i9 = (int) ((((float) i6) / f) + 0.5f);
            if (i8 <= i6 && i7 <= i7) {
                iArr[0] = i8;
                iArr[1] = i7;
            } else if (i6 <= i6 && i9 <= i7) {
                iArr[0] = i6;
                iArr[1] = i9;
            }
        } else if (i5 == 0) {
            iArr[0] = (int) ((((float) i7) * f) + 0.5f);
            iArr[1] = i7;
        } else if (i5 == 1) {
            iArr[0] = i6;
            iArr[1] = (int) ((((float) i6) * f) + 0.5f);
        }
    }

    /* renamed from: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun$1  reason: invalid class name */
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
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun.AnonymousClass1.<clinit>():void");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:105:0x02e9, code lost:
        if (r14 != 1) goto L_0x0357;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(androidx.constraintlayout.solver.widgets.analyzer.Dependency r17) {
        /*
            r16 = this;
            r8 = r16
            int[] r0 = androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun.AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$widgets$analyzer$WidgetRun$RunType
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType r1 = r8.mRunType
            int r1 = r1.ordinal()
            r0 = r0[r1]
            r1 = 2
            r2 = 3
            r9 = 1
            r10 = 0
            if (r0 == r9) goto L_0x002b
            if (r0 == r1) goto L_0x0025
            if (r0 == r2) goto L_0x0017
            goto L_0x0030
        L_0x0017:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mRight
            r3 = r17
            r8.updateRunCenter(r3, r0, r1, r10)
            return
        L_0x0025:
            r3 = r17
            r16.updateRunEnd(r17)
            goto L_0x0030
        L_0x002b:
            r3 = r17
            r16.updateRunStart(r17)
        L_0x0030:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            boolean r0 = r0.resolved
            r11 = 1056964608(0x3f000000, float:0.5)
            if (r0 != 0) goto L_0x0357
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r8.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r3) goto L_0x0357
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            int r0 = r0.mMatchConstraintDefaultWidth
            if (r0 == r1) goto L_0x0333
            if (r0 == r2) goto L_0x0048
            goto L_0x0357
        L_0x0048:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            int r0 = r0.mMatchConstraintDefaultHeight
            r1 = -1
            if (r0 == 0) goto L_0x009f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            int r0 = r0.mMatchConstraintDefaultHeight
            if (r0 != r2) goto L_0x0056
            goto L_0x009f
        L_0x0056:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            int r0 = r0.getDimensionRatioSide()
            if (r0 == r1) goto L_0x0085
            if (r0 == 0) goto L_0x0074
            if (r0 == r9) goto L_0x0064
            r0 = 0
            goto L_0x0098
        L_0x0064:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r0 = r0.value
            float r0 = (float) r0
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r8.widget
            float r1 = r1.getDimensionRatio()
            goto L_0x0094
        L_0x0074:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r0 = r0.value
            float r0 = (float) r0
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r8.widget
            float r1 = r1.getDimensionRatio()
            float r0 = r0 / r1
            goto L_0x0096
        L_0x0085:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r0 = r0.value
            float r0 = (float) r0
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r8.widget
            float r1 = r1.getDimensionRatio()
        L_0x0094:
            float r0 = r0 * r1
        L_0x0096:
            float r0 = r0 + r11
            int r0 = (int) r0
        L_0x0098:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r8.dimension
            r1.resolve(r0)
            goto L_0x0357
        L_0x009f:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r12 = r0.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r13 = r0.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            if (r0 == 0) goto L_0x00b5
            r0 = 1
            goto L_0x00b6
        L_0x00b5:
            r0 = 0
        L_0x00b6:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x00c0
            r2 = 1
            goto L_0x00c1
        L_0x00c0:
            r2 = 0
        L_0x00c1:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x00cb
            r3 = 1
            goto L_0x00cc
        L_0x00cb:
            r3 = 0
        L_0x00cc:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mBottom
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x00d6
            r4 = 1
            goto L_0x00d7
        L_0x00d6:
            r4 = 0
        L_0x00d7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r8.widget
            int r14 = r5.getDimensionRatioSide()
            if (r0 == 0) goto L_0x022d
            if (r2 == 0) goto L_0x022d
            if (r3 == 0) goto L_0x022d
            if (r4 == 0) goto L_0x022d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            float r15 = r0.getDimensionRatio()
            boolean r0 = r12.resolved
            if (r0 == 0) goto L_0x0150
            boolean r0 = r13.resolved
            if (r0 == 0) goto L_0x0150
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            boolean r0 = r0.readyToSolve
            if (r0 == 0) goto L_0x014f
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            boolean r0 = r0.readyToSolve
            if (r0 != 0) goto L_0x0100
            goto L_0x014f
        L_0x0100:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.start
            int r1 = r1.margin
            int r2 = r0 + r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.end
            int r1 = r1.margin
            int r3 = r0 - r1
            int r0 = r12.value
            int r1 = r12.margin
            int r4 = r0 + r1
            int r0 = r13.value
            int r1 = r13.margin
            int r5 = r0 - r1
            int[] r1 = tempDimensions
            r0 = r16
            r6 = r15
            r7 = r14
            r0.computeInsetRatio(r1, r2, r3, r4, r5, r6, r7)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            int[] r1 = tempDimensions
            r1 = r1[r10]
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int[] r1 = tempDimensions
            r1 = r1[r9]
            r0.resolve(r1)
        L_0x014f:
            return
        L_0x0150:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x01b6
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x01b6
            boolean r0 = r12.readyToSolve
            if (r0 == 0) goto L_0x01b5
            boolean r0 = r13.readyToSolve
            if (r0 != 0) goto L_0x0165
            goto L_0x01b5
        L_0x0165:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.start
            int r1 = r1.margin
            int r2 = r0 + r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.end
            int r1 = r1.margin
            int r3 = r0 - r1
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r12.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            int r1 = r12.margin
            int r4 = r0 + r1
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r13.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            int r1 = r13.margin
            int r5 = r0 - r1
            int[] r1 = tempDimensions
            r0 = r16
            r6 = r15
            r7 = r14
            r0.computeInsetRatio(r1, r2, r3, r4, r5, r6, r7)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            int[] r1 = tempDimensions
            r1 = r1[r10]
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int[] r1 = tempDimensions
            r1 = r1[r9]
            r0.resolve(r1)
            goto L_0x01b6
        L_0x01b5:
            return
        L_0x01b6:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            boolean r0 = r0.readyToSolve
            if (r0 == 0) goto L_0x022c
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            boolean r0 = r0.readyToSolve
            if (r0 == 0) goto L_0x022c
            boolean r0 = r12.readyToSolve
            if (r0 == 0) goto L_0x022c
            boolean r0 = r13.readyToSolve
            if (r0 != 0) goto L_0x01cb
            goto L_0x022c
        L_0x01cb:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.start
            int r1 = r1.margin
            int r2 = r0 + r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.end
            int r1 = r1.margin
            int r3 = r0 - r1
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r12.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            int r1 = r12.margin
            int r4 = r0 + r1
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r13.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            int r0 = r0.value
            int r1 = r13.margin
            int r5 = r0 - r1
            int[] r1 = tempDimensions
            r0 = r16
            r6 = r15
            r7 = r14
            r0.computeInsetRatio(r1, r2, r3, r4, r5, r6, r7)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            int[] r1 = tempDimensions
            r1 = r1[r10]
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int[] r1 = tempDimensions
            r1 = r1[r9]
            r0.resolve(r1)
            goto L_0x0357
        L_0x022c:
            return
        L_0x022d:
            if (r0 == 0) goto L_0x02b8
            if (r3 == 0) goto L_0x02b8
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            boolean r0 = r0.readyToSolve
            if (r0 == 0) goto L_0x02b7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            boolean r0 = r0.readyToSolve
            if (r0 != 0) goto L_0x023f
            goto L_0x02b7
        L_0x023f:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            float r0 = r0.getDimensionRatio()
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r2 = r2.targets
            java.lang.Object r2 = r2.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r2
            int r2 = r2.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r8.start
            int r3 = r3.margin
            int r2 = r2 + r3
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r3 = r3.targets
            java.lang.Object r3 = r3.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r3
            int r3 = r3.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r8.end
            int r4 = r4.margin
            int r3 = r3 - r4
            if (r14 == r1) goto L_0x0293
            if (r14 == 0) goto L_0x0293
            if (r14 == r9) goto L_0x026f
            goto L_0x0357
        L_0x026f:
            int r3 = r3 - r2
            int r1 = r8.getLimitedDimension(r3, r10)
            float r2 = (float) r1
            float r2 = r2 / r0
            float r2 = r2 + r11
            int r2 = (int) r2
            int r3 = r8.getLimitedDimension(r2, r9)
            if (r2 == r3) goto L_0x0283
            float r1 = (float) r3
            float r1 = r1 * r0
            float r1 = r1 + r11
            int r1 = (int) r1
        L_0x0283:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            r0.resolve(r3)
            goto L_0x0357
        L_0x0293:
            int r3 = r3 - r2
            int r1 = r8.getLimitedDimension(r3, r10)
            float r2 = (float) r1
            float r2 = r2 * r0
            float r2 = r2 + r11
            int r2 = (int) r2
            int r3 = r8.getLimitedDimension(r2, r9)
            if (r2 == r3) goto L_0x02a7
            float r1 = (float) r3
            float r1 = r1 / r0
            float r1 = r1 + r11
            int r1 = (int) r1
        L_0x02a7:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            r0.resolve(r3)
            goto L_0x0357
        L_0x02b7:
            return
        L_0x02b8:
            if (r2 == 0) goto L_0x0357
            if (r4 == 0) goto L_0x0357
            boolean r0 = r12.readyToSolve
            if (r0 == 0) goto L_0x0332
            boolean r0 = r13.readyToSolve
            if (r0 != 0) goto L_0x02c5
            goto L_0x0332
        L_0x02c5:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            float r0 = r0.getDimensionRatio()
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r2 = r12.targets
            java.lang.Object r2 = r2.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r2
            int r2 = r2.value
            int r3 = r12.margin
            int r2 = r2 + r3
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r3 = r13.targets
            java.lang.Object r3 = r3.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r3
            int r3 = r3.value
            int r4 = r13.margin
            int r3 = r3 - r4
            if (r14 == r1) goto L_0x030f
            if (r14 == 0) goto L_0x02ec
            if (r14 == r9) goto L_0x030f
            goto L_0x0357
        L_0x02ec:
            int r3 = r3 - r2
            int r1 = r8.getLimitedDimension(r3, r9)
            float r2 = (float) r1
            float r2 = r2 * r0
            float r2 = r2 + r11
            int r2 = (int) r2
            int r3 = r8.getLimitedDimension(r2, r10)
            if (r2 == r3) goto L_0x0300
            float r1 = (float) r3
            float r1 = r1 / r0
            float r1 = r1 + r11
            int r1 = (int) r1
        L_0x0300:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            r0.resolve(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            r0.resolve(r1)
            goto L_0x0357
        L_0x030f:
            int r3 = r3 - r2
            int r1 = r8.getLimitedDimension(r3, r9)
            float r2 = (float) r1
            float r2 = r2 / r0
            float r2 = r2 + r11
            int r2 = (int) r2
            int r3 = r8.getLimitedDimension(r2, r10)
            if (r2 == r3) goto L_0x0323
            float r1 = (float) r3
            float r1 = r1 * r0
            float r1 = r1 + r11
            int r1 = (int) r1
        L_0x0323:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            r0.resolve(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            r0.resolve(r1)
            goto L_0x0357
        L_0x0332:
            return
        L_0x0333:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.getParent()
            if (r0 == 0) goto L_0x0357
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r1 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r1.dimension
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x0357
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r8.widget
            float r1 = r1.mMatchConstraintPercentWidth
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r0 = r0.value
            float r0 = (float) r0
            float r0 = r0 * r1
            float r0 = r0 + r11
            int r0 = (int) r0
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r8.dimension
            r1.resolve(r0)
        L_0x0357:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            boolean r0 = r0.readyToSolve
            if (r0 == 0) goto L_0x0483
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            boolean r0 = r0.readyToSolve
            if (r0 != 0) goto L_0x0365
            goto L_0x0483
        L_0x0365:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x0378
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x0378
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x0378
            return
        L_0x0378:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            boolean r0 = r0.resolved
            if (r0 != 0) goto L_0x03c6
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r8.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r1) goto L_0x03c6
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            int r0 = r0.mMatchConstraintDefaultWidth
            if (r0 != 0) goto L_0x03c6
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.widget
            boolean r0 = r0.isInHorizontalChain()
            if (r0 != 0) goto L_0x03c6
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r1 = r1.targets
            java.lang.Object r1 = r1.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r8.start
            int r2 = r2.margin
            int r0 = r0 + r2
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r8.end
            int r2 = r2.margin
            int r1 = r1 + r2
            int r2 = r1 - r0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r8.start
            r3.resolve(r0)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            r0.resolve(r2)
            return
        L_0x03c6:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            boolean r0 = r0.resolved
            if (r0 != 0) goto L_0x042c
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = r8.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r1) goto L_0x042c
            int r0 = r8.matchConstraintsType
            if (r0 != r9) goto L_0x042c
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x042c
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x042c
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r1 = r1.targets
            java.lang.Object r1 = r1.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            int r0 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r8.start
            int r2 = r2.margin
            int r0 = r0 + r2
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r8.end
            int r2 = r2.margin
            int r1 = r1 + r2
            int r1 = r1 - r0
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            int r0 = r0.wrapValue
            int r0 = java.lang.Math.min(r1, r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r8.widget
            int r1 = r1.mMatchConstraintMaxWidth
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r8.widget
            int r2 = r2.mMatchConstraintMinWidth
            int r0 = java.lang.Math.max(r2, r0)
            if (r1 <= 0) goto L_0x0427
            int r0 = java.lang.Math.min(r1, r0)
        L_0x0427:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r8.dimension
            r1.resolve(r0)
        L_0x042c:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            boolean r0 = r0.resolved
            if (r0 != 0) goto L_0x0433
            return
        L_0x0433:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r0 = r0.targets
            java.lang.Object r0 = r0.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.end
            java.util.List<androidx.constraintlayout.solver.widgets.analyzer.DependencyNode> r1 = r1.targets
            java.lang.Object r1 = r1.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            int r2 = r0.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r8.start
            int r3 = r3.margin
            int r2 = r2 + r3
            int r3 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r8.end
            int r4 = r4.margin
            int r3 = r3 + r4
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r8.widget
            float r4 = r4.getHorizontalBiasPercent()
            if (r0 != r1) goto L_0x0463
            int r2 = r0.value
            int r3 = r1.value
            r4 = 1056964608(0x3f000000, float:0.5)
        L_0x0463:
            int r3 = r3 - r2
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r8.dimension
            int r0 = r0.value
            int r3 = r3 - r0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.start
            float r1 = (float) r2
            float r1 = r1 + r11
            float r2 = (float) r3
            float r2 = r2 * r4
            float r1 = r1 + r2
            int r1 = (int) r1
            r0.resolve(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r8.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r8.start
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r8.dimension
            int r2 = r2.value
            int r1 = r1 + r2
            r0.resolve(r1)
        L_0x0483:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun.update(androidx.constraintlayout.solver.widgets.analyzer.Dependency):void");
    }

    public void applyToWidget() {
        if (this.start.resolved) {
            this.widget.setX(this.start.value);
        }
    }
}
