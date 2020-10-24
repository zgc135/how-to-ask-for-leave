package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun extends WidgetRun {
    private int chainStyle;
    ArrayList<WidgetRun> widgets = new ArrayList<>();

    public ChainRun(ConstraintWidget constraintWidget, int i) {
        super(constraintWidget);
        this.orientation = i;
        build();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChainRun ");
        sb.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        String sb2 = sb.toString();
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            sb2 = ((sb2 + "<") + it.next()) + "> ";
        }
        return sb2;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        int size = this.widgets.size();
        for (int i = 0; i < size; i++) {
            if (!this.widgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public long getWrapDimension() {
        int size = this.widgets.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            WidgetRun widgetRun = this.widgets.get(i);
            j = j + ((long) widgetRun.start.margin) + widgetRun.getWrapDimension() + ((long) widgetRun.end.margin);
        }
        return j;
    }

    private void build() {
        ConstraintWidget constraintWidget;
        ConstraintWidget constraintWidget2 = this.widget;
        ConstraintWidget previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
        while (true) {
            ConstraintWidget constraintWidget3 = previousChainMember;
            constraintWidget = constraintWidget2;
            constraintWidget2 = constraintWidget3;
            if (constraintWidget2 == null) {
                break;
            }
            previousChainMember = constraintWidget2.getPreviousChainMember(this.orientation);
        }
        this.widget = constraintWidget;
        this.widgets.add(constraintWidget.getRun(this.orientation));
        ConstraintWidget nextChainMember = constraintWidget.getNextChainMember(this.orientation);
        while (nextChainMember != null) {
            this.widgets.add(nextChainMember.getRun(this.orientation));
            nextChainMember = nextChainMember.getNextChainMember(this.orientation);
        }
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            WidgetRun next = it.next();
            if (this.orientation == 0) {
                next.widget.horizontalChainRun = this;
            } else if (this.orientation == 1) {
                next.widget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.getParent()).isRtl()) && this.widgets.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.widgets;
            this.widget = arrayList.get(arrayList.size() - 1).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00cf, code lost:
        if (r2.dimension.resolved != false) goto L_0x00d1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(androidx.constraintlayout.solver.widgets.analyzer.Dependency r23) {
        /*
            r22 = this;
            r0 = r22
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x0437
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.end
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x0010
            goto L_0x0437
        L_0x0010:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r0.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r1.getParent()
            if (r1 == 0) goto L_0x0023
            boolean r3 = r1 instanceof androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
            if (r3 == 0) goto L_0x0023
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r1 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r1
            boolean r1 = r1.isRtl()
            goto L_0x0024
        L_0x0023:
            r1 = 0
        L_0x0024:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r0.end
            int r3 = r3.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r0.start
            int r4 = r4.value
            int r3 = r3 - r4
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r4 = r0.widgets
            int r4 = r4.size()
            r5 = 0
        L_0x0034:
            r6 = -1
            r7 = 8
            if (r5 >= r4) goto L_0x004c
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r8 = r0.widgets
            java.lang.Object r8 = r8.get(r5)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r8 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r8
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r8.widget
            int r8 = r8.getVisibility()
            if (r8 != r7) goto L_0x004d
            int r5 = r5 + 1
            goto L_0x0034
        L_0x004c:
            r5 = -1
        L_0x004d:
            int r8 = r4 + -1
            r9 = r8
        L_0x0050:
            if (r9 < 0) goto L_0x0066
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r10 = r0.widgets
            java.lang.Object r10 = r10.get(r9)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r10 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r10
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r10.widget
            int r10 = r10.getVisibility()
            if (r10 != r7) goto L_0x0065
            int r9 = r9 + -1
            goto L_0x0050
        L_0x0065:
            r6 = r9
        L_0x0066:
            r9 = 0
        L_0x0067:
            r11 = 2
            r12 = 1
            if (r9 >= r11) goto L_0x0106
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
        L_0x0072:
            if (r13 >= r4) goto L_0x00f8
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r2 = r0.widgets
            java.lang.Object r2 = r2.get(r13)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r2 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r2.widget
            int r11 = r11.getVisibility()
            if (r11 != r7) goto L_0x0086
            goto L_0x00f1
        L_0x0086:
            int r16 = r16 + 1
            if (r13 <= 0) goto L_0x0091
            if (r13 < r5) goto L_0x0091
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r11 = r2.start
            int r11 = r11.margin
            int r14 = r14 + r11
        L_0x0091:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r11 = r2.dimension
            int r11 = r11.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = r2.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r7 == r10) goto L_0x009d
            r7 = 1
            goto L_0x009e
        L_0x009d:
            r7 = 0
        L_0x009e:
            if (r7 == 0) goto L_0x00be
            int r10 = r0.orientation
            if (r10 != 0) goto L_0x00af
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r2.widget
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r10 = r10.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r10.dimension
            boolean r10 = r10.resolved
            if (r10 != 0) goto L_0x00af
            return
        L_0x00af:
            int r10 = r0.orientation
            if (r10 != r12) goto L_0x00d2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r2.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r10 = r10.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r10.dimension
            boolean r10 = r10.resolved
            if (r10 != 0) goto L_0x00d2
            return
        L_0x00be:
            int r10 = r2.matchConstraintsType
            if (r10 != r12) goto L_0x00cb
            if (r9 != 0) goto L_0x00cb
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r7 = r2.dimension
            int r11 = r7.wrapValue
            int r15 = r15 + 1
            goto L_0x00d1
        L_0x00cb:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r2.dimension
            boolean r10 = r10.resolved
            if (r10 == 0) goto L_0x00d2
        L_0x00d1:
            r7 = 1
        L_0x00d2:
            if (r7 != 0) goto L_0x00e6
            int r15 = r15 + 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r2.widget
            float[] r7 = r7.mWeight
            int r10 = r0.orientation
            r7 = r7[r10]
            r10 = 0
            int r11 = (r7 > r10 ? 1 : (r7 == r10 ? 0 : -1))
            if (r11 < 0) goto L_0x00e7
            float r17 = r17 + r7
            goto L_0x00e7
        L_0x00e6:
            int r14 = r14 + r11
        L_0x00e7:
            if (r13 >= r8) goto L_0x00f1
            if (r13 >= r6) goto L_0x00f1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            int r2 = r2.margin
            int r2 = -r2
            int r14 = r14 + r2
        L_0x00f1:
            int r13 = r13 + 1
            r7 = 8
            r11 = 2
            goto L_0x0072
        L_0x00f8:
            if (r14 < r3) goto L_0x0103
            if (r15 != 0) goto L_0x00fd
            goto L_0x0103
        L_0x00fd:
            int r9 = r9 + 1
            r7 = 8
            goto L_0x0067
        L_0x0103:
            r2 = r16
            goto L_0x010b
        L_0x0106:
            r2 = 0
            r14 = 0
            r15 = 0
            r17 = 0
        L_0x010b:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r7 = r0.start
            int r7 = r7.value
            if (r1 == 0) goto L_0x0115
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r7 = r0.end
            int r7 = r7.value
        L_0x0115:
            r9 = 1056964608(0x3f000000, float:0.5)
            if (r14 <= r3) goto L_0x012c
            r10 = 1073741824(0x40000000, float:2.0)
            if (r1 == 0) goto L_0x0125
            int r11 = r14 - r3
            float r11 = (float) r11
            float r11 = r11 / r10
            float r11 = r11 + r9
            int r10 = (int) r11
            int r7 = r7 + r10
            goto L_0x012c
        L_0x0125:
            int r11 = r14 - r3
            float r11 = (float) r11
            float r11 = r11 / r10
            float r11 = r11 + r9
            int r10 = (int) r11
            int r7 = r7 - r10
        L_0x012c:
            if (r15 <= 0) goto L_0x0238
            int r10 = r3 - r14
            float r10 = (float) r10
            float r11 = (float) r15
            float r11 = r10 / r11
            float r11 = r11 + r9
            int r11 = (int) r11
            r13 = 0
            r16 = 0
        L_0x0139:
            if (r13 >= r4) goto L_0x01ed
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r12 = r0.widgets
            java.lang.Object r12 = r12.get(r13)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r12 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r12
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r12.widget
            int r9 = r9.getVisibility()
            r18 = r11
            r11 = 8
            if (r9 != r11) goto L_0x0151
            goto L_0x01d8
        L_0x0151:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r12.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r9 != r11) goto L_0x01d8
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r9 = r12.dimension
            boolean r9 = r9.resolved
            if (r9 != 0) goto L_0x01d8
            r9 = 0
            int r11 = (r17 > r9 ? 1 : (r17 == r9 ? 0 : -1))
            if (r11 <= 0) goto L_0x0173
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r12.widget
            float[] r11 = r11.mWeight
            int r9 = r0.orientation
            r9 = r11[r9]
            float r9 = r9 * r10
            float r9 = r9 / r17
            r11 = 1056964608(0x3f000000, float:0.5)
            float r9 = r9 + r11
            int r9 = (int) r9
            goto L_0x0175
        L_0x0173:
            r9 = r18
        L_0x0175:
            int r11 = r0.orientation
            if (r11 != 0) goto L_0x01a6
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r12.widget
            int r11 = r11.mMatchConstraintMaxWidth
            r19 = r10
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r12.widget
            int r10 = r10.mMatchConstraintMinWidth
            r20 = r14
            int r14 = r12.matchConstraintsType
            r21 = r7
            r7 = 1
            if (r14 != r7) goto L_0x0195
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r7 = r12.dimension
            int r7 = r7.wrapValue
            int r7 = java.lang.Math.min(r9, r7)
            goto L_0x0196
        L_0x0195:
            r7 = r9
        L_0x0196:
            int r7 = java.lang.Math.max(r10, r7)
            if (r11 <= 0) goto L_0x01a0
            int r7 = java.lang.Math.min(r11, r7)
        L_0x01a0:
            if (r7 == r9) goto L_0x01d2
            int r16 = r16 + 1
            r9 = r7
            goto L_0x01d2
        L_0x01a6:
            r21 = r7
            r19 = r10
            r20 = r14
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r12.widget
            int r7 = r7.mMatchConstraintMaxHeight
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r12.widget
            int r10 = r10.mMatchConstraintMinHeight
            int r11 = r12.matchConstraintsType
            r14 = 1
            if (r11 != r14) goto L_0x01c2
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r11 = r12.dimension
            int r11 = r11.wrapValue
            int r11 = java.lang.Math.min(r9, r11)
            goto L_0x01c3
        L_0x01c2:
            r11 = r9
        L_0x01c3:
            int r10 = java.lang.Math.max(r10, r11)
            if (r7 <= 0) goto L_0x01cd
            int r10 = java.lang.Math.min(r7, r10)
        L_0x01cd:
            if (r10 == r9) goto L_0x01d2
            int r16 = r16 + 1
            r9 = r10
        L_0x01d2:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r7 = r12.dimension
            r7.resolve(r9)
            goto L_0x01de
        L_0x01d8:
            r21 = r7
            r19 = r10
            r20 = r14
        L_0x01de:
            int r13 = r13 + 1
            r11 = r18
            r10 = r19
            r14 = r20
            r7 = r21
            r9 = 1056964608(0x3f000000, float:0.5)
            r12 = 1
            goto L_0x0139
        L_0x01ed:
            r21 = r7
            r20 = r14
            if (r16 <= 0) goto L_0x0229
            int r15 = r15 - r16
            r7 = 0
            r9 = 0
        L_0x01f7:
            if (r7 >= r4) goto L_0x0227
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r10 = r0.widgets
            java.lang.Object r10 = r10.get(r7)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r10 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r10
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r10.widget
            int r11 = r11.getVisibility()
            r12 = 8
            if (r11 != r12) goto L_0x020c
            goto L_0x0224
        L_0x020c:
            if (r7 <= 0) goto L_0x0215
            if (r7 < r5) goto L_0x0215
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r11 = r10.start
            int r11 = r11.margin
            int r9 = r9 + r11
        L_0x0215:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r11 = r10.dimension
            int r11 = r11.value
            int r9 = r9 + r11
            if (r7 >= r8) goto L_0x0224
            if (r7 >= r6) goto L_0x0224
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r10.end
            int r10 = r10.margin
            int r10 = -r10
            int r9 = r9 + r10
        L_0x0224:
            int r7 = r7 + 1
            goto L_0x01f7
        L_0x0227:
            r14 = r9
            goto L_0x022b
        L_0x0229:
            r14 = r20
        L_0x022b:
            int r7 = r0.chainStyle
            r9 = 2
            if (r7 != r9) goto L_0x0236
            if (r16 != 0) goto L_0x0236
            r7 = 0
            r0.chainStyle = r7
            goto L_0x023e
        L_0x0236:
            r7 = 0
            goto L_0x023e
        L_0x0238:
            r21 = r7
            r20 = r14
            r7 = 0
            r9 = 2
        L_0x023e:
            if (r14 <= r3) goto L_0x0242
            r0.chainStyle = r9
        L_0x0242:
            if (r2 <= 0) goto L_0x024a
            if (r15 != 0) goto L_0x024a
            if (r5 != r6) goto L_0x024a
            r0.chainStyle = r9
        L_0x024a:
            int r9 = r0.chainStyle
            r10 = 1
            if (r9 != r10) goto L_0x02ed
            if (r2 <= r10) goto L_0x0255
            int r3 = r3 - r14
            int r2 = r2 - r10
            int r3 = r3 / r2
            goto L_0x025c
        L_0x0255:
            if (r2 != r10) goto L_0x025b
            int r3 = r3 - r14
            r2 = 2
            int r3 = r3 / r2
            goto L_0x025c
        L_0x025b:
            r3 = 0
        L_0x025c:
            if (r15 <= 0) goto L_0x025f
            r3 = 0
        L_0x025f:
            r7 = r21
            r2 = 0
        L_0x0262:
            if (r2 >= r4) goto L_0x0437
            if (r1 == 0) goto L_0x026b
            int r9 = r2 + 1
            int r9 = r4 - r9
            goto L_0x026c
        L_0x026b:
            r9 = r2
        L_0x026c:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r10 = r0.widgets
            java.lang.Object r9 = r10.get(r9)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r9 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r9
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r9.widget
            int r10 = r10.getVisibility()
            r11 = 8
            if (r10 != r11) goto L_0x0289
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            r10.resolve(r7)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.end
            r9.resolve(r7)
            goto L_0x02e9
        L_0x0289:
            if (r2 <= 0) goto L_0x0290
            if (r1 == 0) goto L_0x028f
            int r7 = r7 - r3
            goto L_0x0290
        L_0x028f:
            int r7 = r7 + r3
        L_0x0290:
            if (r2 <= 0) goto L_0x02a1
            if (r2 < r5) goto L_0x02a1
            if (r1 == 0) goto L_0x029c
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            int r10 = r10.margin
            int r7 = r7 - r10
            goto L_0x02a1
        L_0x029c:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            int r10 = r10.margin
            int r7 = r7 + r10
        L_0x02a1:
            if (r1 == 0) goto L_0x02a9
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.end
            r10.resolve(r7)
            goto L_0x02ae
        L_0x02a9:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            r10.resolve(r7)
        L_0x02ae:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r9.dimension
            int r10 = r10.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r9.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r12) goto L_0x02c1
            int r11 = r9.matchConstraintsType
            r12 = 1
            if (r11 != r12) goto L_0x02c1
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r9.dimension
            int r10 = r10.wrapValue
        L_0x02c1:
            if (r1 == 0) goto L_0x02c5
            int r7 = r7 - r10
            goto L_0x02c6
        L_0x02c5:
            int r7 = r7 + r10
        L_0x02c6:
            if (r1 == 0) goto L_0x02ce
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            r10.resolve(r7)
            goto L_0x02d3
        L_0x02ce:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.end
            r10.resolve(r7)
        L_0x02d3:
            r10 = 1
            r9.resolved = r10
            if (r2 >= r8) goto L_0x02e9
            if (r2 >= r6) goto L_0x02e9
            if (r1 == 0) goto L_0x02e3
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.end
            int r9 = r9.margin
            int r9 = -r9
            int r7 = r7 - r9
            goto L_0x02e9
        L_0x02e3:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.end
            int r9 = r9.margin
            int r9 = -r9
            int r7 = r7 + r9
        L_0x02e9:
            int r2 = r2 + 1
            goto L_0x0262
        L_0x02ed:
            if (r9 != 0) goto L_0x0383
            int r3 = r3 - r14
            r9 = 1
            int r2 = r2 + r9
            int r3 = r3 / r2
            if (r15 <= 0) goto L_0x02f6
            r3 = 0
        L_0x02f6:
            r7 = r21
            r2 = 0
        L_0x02f9:
            if (r2 >= r4) goto L_0x0437
            if (r1 == 0) goto L_0x0302
            int r9 = r2 + 1
            int r9 = r4 - r9
            goto L_0x0303
        L_0x0302:
            r9 = r2
        L_0x0303:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r10 = r0.widgets
            java.lang.Object r9 = r10.get(r9)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r9 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r9
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r9.widget
            int r10 = r10.getVisibility()
            r11 = 8
            if (r10 != r11) goto L_0x0320
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            r10.resolve(r7)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.end
            r9.resolve(r7)
            goto L_0x037f
        L_0x0320:
            if (r1 == 0) goto L_0x0324
            int r7 = r7 - r3
            goto L_0x0325
        L_0x0324:
            int r7 = r7 + r3
        L_0x0325:
            if (r2 <= 0) goto L_0x0336
            if (r2 < r5) goto L_0x0336
            if (r1 == 0) goto L_0x0331
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            int r10 = r10.margin
            int r7 = r7 - r10
            goto L_0x0336
        L_0x0331:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            int r10 = r10.margin
            int r7 = r7 + r10
        L_0x0336:
            if (r1 == 0) goto L_0x033e
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.end
            r10.resolve(r7)
            goto L_0x0343
        L_0x033e:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            r10.resolve(r7)
        L_0x0343:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r9.dimension
            int r10 = r10.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r9.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r12) goto L_0x035a
            int r11 = r9.matchConstraintsType
            r12 = 1
            if (r11 != r12) goto L_0x035a
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r11 = r9.dimension
            int r11 = r11.wrapValue
            int r10 = java.lang.Math.min(r10, r11)
        L_0x035a:
            if (r1 == 0) goto L_0x035e
            int r7 = r7 - r10
            goto L_0x035f
        L_0x035e:
            int r7 = r7 + r10
        L_0x035f:
            if (r1 == 0) goto L_0x0367
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.start
            r10.resolve(r7)
            goto L_0x036c
        L_0x0367:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r9.end
            r10.resolve(r7)
        L_0x036c:
            if (r2 >= r8) goto L_0x037f
            if (r2 >= r6) goto L_0x037f
            if (r1 == 0) goto L_0x0379
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.end
            int r9 = r9.margin
            int r9 = -r9
            int r7 = r7 - r9
            goto L_0x037f
        L_0x0379:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.end
            int r9 = r9.margin
            int r9 = -r9
            int r7 = r7 + r9
        L_0x037f:
            int r2 = r2 + 1
            goto L_0x02f9
        L_0x0383:
            r2 = 2
            if (r9 != r2) goto L_0x0437
            int r2 = r0.orientation
            if (r2 != 0) goto L_0x0391
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            float r2 = r2.getHorizontalBiasPercent()
            goto L_0x0397
        L_0x0391:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            float r2 = r2.getVerticalBiasPercent()
        L_0x0397:
            if (r1 == 0) goto L_0x039d
            r9 = 1065353216(0x3f800000, float:1.0)
            float r2 = r9 - r2
        L_0x039d:
            int r3 = r3 - r14
            float r3 = (float) r3
            float r3 = r3 * r2
            r2 = 1056964608(0x3f000000, float:0.5)
            float r3 = r3 + r2
            int r2 = (int) r3
            if (r2 < 0) goto L_0x03a9
            if (r15 <= 0) goto L_0x03aa
        L_0x03a9:
            r2 = 0
        L_0x03aa:
            if (r1 == 0) goto L_0x03af
            int r2 = r21 - r2
            goto L_0x03b1
        L_0x03af:
            int r2 = r21 + r2
        L_0x03b1:
            r3 = r2
            r2 = 0
        L_0x03b3:
            if (r2 >= r4) goto L_0x0437
            if (r1 == 0) goto L_0x03bc
            int r7 = r2 + 1
            int r7 = r4 - r7
            goto L_0x03bd
        L_0x03bc:
            r7 = r2
        L_0x03bd:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r9 = r0.widgets
            java.lang.Object r7 = r9.get(r7)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r7 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r7
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r7.widget
            int r9 = r9.getVisibility()
            r10 = 8
            if (r9 != r10) goto L_0x03db
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.start
            r9.resolve(r3)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r7 = r7.end
            r7.resolve(r3)
            r12 = 1
            goto L_0x0433
        L_0x03db:
            if (r2 <= 0) goto L_0x03ec
            if (r2 < r5) goto L_0x03ec
            if (r1 == 0) goto L_0x03e7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.start
            int r9 = r9.margin
            int r3 = r3 - r9
            goto L_0x03ec
        L_0x03e7:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.start
            int r9 = r9.margin
            int r3 = r3 + r9
        L_0x03ec:
            if (r1 == 0) goto L_0x03f4
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.end
            r9.resolve(r3)
            goto L_0x03f9
        L_0x03f4:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.start
            r9.resolve(r3)
        L_0x03f9:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r9 = r7.dimension
            int r9 = r9.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r7.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 != r12) goto L_0x040d
            int r11 = r7.matchConstraintsType
            r12 = 1
            if (r11 != r12) goto L_0x040e
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r9 = r7.dimension
            int r9 = r9.wrapValue
            goto L_0x040e
        L_0x040d:
            r12 = 1
        L_0x040e:
            if (r1 == 0) goto L_0x0412
            int r3 = r3 - r9
            goto L_0x0413
        L_0x0412:
            int r3 = r3 + r9
        L_0x0413:
            if (r1 == 0) goto L_0x041b
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.start
            r9.resolve(r3)
            goto L_0x0420
        L_0x041b:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r7.end
            r9.resolve(r3)
        L_0x0420:
            if (r2 >= r8) goto L_0x0433
            if (r2 >= r6) goto L_0x0433
            if (r1 == 0) goto L_0x042d
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r7 = r7.end
            int r7 = r7.margin
            int r7 = -r7
            int r3 = r3 - r7
            goto L_0x0433
        L_0x042d:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r7 = r7.end
            int r7 = r7.margin
            int r7 = -r7
            int r3 = r3 + r7
        L_0x0433:
            int r2 = r2 + 1
            goto L_0x03b3
        L_0x0437:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.ChainRun.update(androidx.constraintlayout.solver.widgets.analyzer.Dependency):void");
    }

    public void applyToWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).applyToWidget();
        }
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            WidgetRun widgetRun = this.widgets.get(i);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int size = this.widgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.widgets.get(size);
            if (widgetRun.widget.getVisibility() != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int size = this.widgets.size();
        if (size >= 1) {
            ConstraintWidget constraintWidget = this.widgets.get(0).widget;
            ConstraintWidget constraintWidget2 = this.widgets.get(size - 1).widget;
            if (this.orientation == 0) {
                ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
                ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
                DependencyNode target = getTarget(constraintAnchor, 0);
                int margin = constraintAnchor.getMargin();
                ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
                if (firstVisibleWidget != null) {
                    margin = firstVisibleWidget.mLeft.getMargin();
                }
                if (target != null) {
                    addTarget(this.start, target, margin);
                }
                DependencyNode target2 = getTarget(constraintAnchor2, 0);
                int margin2 = constraintAnchor2.getMargin();
                ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
                if (lastVisibleWidget != null) {
                    margin2 = lastVisibleWidget.mRight.getMargin();
                }
                if (target2 != null) {
                    addTarget(this.end, target2, -margin2);
                }
            } else {
                ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
                ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
                DependencyNode target3 = getTarget(constraintAnchor3, 1);
                int margin3 = constraintAnchor3.getMargin();
                ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
                if (firstVisibleWidget2 != null) {
                    margin3 = firstVisibleWidget2.mTop.getMargin();
                }
                if (target3 != null) {
                    addTarget(this.start, target3, margin3);
                }
                DependencyNode target4 = getTarget(constraintAnchor4, 1);
                int margin4 = constraintAnchor4.getMargin();
                ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
                if (lastVisibleWidget2 != null) {
                    margin4 = lastVisibleWidget2.mBottom.getMargin();
                }
                if (target4 != null) {
                    addTarget(this.end, target4, -margin4);
                }
            }
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
        }
    }
}
