package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.ArrayList;

public class Grouping {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_GROUPING = false;

    public static boolean validInGroup(ConstraintWidget.DimensionBehaviour dimensionBehaviour, ConstraintWidget.DimensionBehaviour dimensionBehaviour2, ConstraintWidget.DimensionBehaviour dimensionBehaviour3, ConstraintWidget.DimensionBehaviour dimensionBehaviour4) {
        return (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour3 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && dimensionBehaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) || (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (dimensionBehaviour4 == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && dimensionBehaviour2 != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT));
    }

    /* JADX WARNING: Removed duplicated region for block: B:178:0x035d  */
    /* JADX WARNING: Removed duplicated region for block: B:189:0x0399  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x039e A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean simpleSolvingPass(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r16, androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.Measurer r17) {
        /*
            r0 = r16
            java.util.ArrayList r1 = r16.getChildren()
            int r2 = r1.size()
            r3 = 0
            r4 = 0
        L_0x000c:
            if (r4 >= r2) goto L_0x0033
            java.lang.Object r5 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = r16.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = r16.getVerticalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = r5.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = r5.getVerticalDimensionBehaviour()
            boolean r6 = validInGroup(r6, r7, r8, r9)
            if (r6 != 0) goto L_0x002b
            return r3
        L_0x002b:
            boolean r5 = r5 instanceof androidx.constraintlayout.solver.widgets.Flow
            if (r5 == 0) goto L_0x0030
            return r3
        L_0x0030:
            int r4 = r4 + 1
            goto L_0x000c
        L_0x0033:
            androidx.constraintlayout.solver.Metrics r4 = r0.mMetrics
            if (r4 == 0) goto L_0x0040
            androidx.constraintlayout.solver.Metrics r4 = r0.mMetrics
            long r5 = r4.grouping
            r7 = 1
            long r5 = r5 + r7
            r4.grouping = r5
        L_0x0040:
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 0
        L_0x0047:
            if (r5 >= r2) goto L_0x011f
            java.lang.Object r13 = r1.get(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r13
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = r16.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r15 = r16.getVerticalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r13.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = r13.getVerticalDimensionBehaviour()
            boolean r4 = validInGroup(r14, r15, r4, r12)
            if (r4 != 0) goto L_0x006d
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r4 = r0.mMeasure
            r12 = r17
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer.measure(r13, r12, r4, r3)
            goto L_0x006f
        L_0x006d:
            r12 = r17
        L_0x006f:
            boolean r4 = r13 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r4 == 0) goto L_0x0097
            r14 = r13
            androidx.constraintlayout.solver.widgets.Guideline r14 = (androidx.constraintlayout.solver.widgets.Guideline) r14
            int r15 = r14.getOrientation()
            if (r15 != 0) goto L_0x0086
            if (r8 != 0) goto L_0x0083
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
        L_0x0083:
            r8.add(r14)
        L_0x0086:
            int r15 = r14.getOrientation()
            r3 = 1
            if (r15 != r3) goto L_0x0097
            if (r6 != 0) goto L_0x0094
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
        L_0x0094:
            r6.add(r14)
        L_0x0097:
            boolean r3 = r13 instanceof androidx.constraintlayout.solver.widgets.HelperWidget
            if (r3 == 0) goto L_0x00db
            boolean r3 = r13 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r3 == 0) goto L_0x00c4
            r3 = r13
            androidx.constraintlayout.solver.widgets.Barrier r3 = (androidx.constraintlayout.solver.widgets.Barrier) r3
            int r14 = r3.getOrientation()
            if (r14 != 0) goto L_0x00b2
            if (r7 != 0) goto L_0x00af
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x00af:
            r7.add(r3)
        L_0x00b2:
            int r14 = r3.getOrientation()
            r15 = 1
            if (r14 != r15) goto L_0x00db
            if (r9 != 0) goto L_0x00c0
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
        L_0x00c0:
            r9.add(r3)
            goto L_0x00db
        L_0x00c4:
            r3 = r13
            androidx.constraintlayout.solver.widgets.HelperWidget r3 = (androidx.constraintlayout.solver.widgets.HelperWidget) r3
            if (r7 != 0) goto L_0x00ce
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
        L_0x00ce:
            r7.add(r3)
            if (r9 != 0) goto L_0x00d8
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
        L_0x00d8:
            r9.add(r3)
        L_0x00db:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 != 0) goto L_0x00f7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 != 0) goto L_0x00f7
            if (r4 != 0) goto L_0x00f7
            boolean r3 = r13 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r3 != 0) goto L_0x00f7
            if (r10 != 0) goto L_0x00f4
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
        L_0x00f4:
            r10.add(r13)
        L_0x00f7:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 != 0) goto L_0x011a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.mBottom
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 != 0) goto L_0x011a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r13.mBaseline
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 != 0) goto L_0x011a
            if (r4 != 0) goto L_0x011a
            boolean r3 = r13 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r3 != 0) goto L_0x011a
            if (r11 != 0) goto L_0x0117
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r11 = r3
        L_0x0117:
            r11.add(r13)
        L_0x011a:
            int r5 = r5 + 1
            r3 = 0
            goto L_0x0047
        L_0x011f:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            if (r6 == 0) goto L_0x013c
            java.util.Iterator r4 = r6.iterator()
        L_0x012a:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x013c
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.Guideline r5 = (androidx.constraintlayout.solver.widgets.Guideline) r5
            r6 = 0
            r12 = 0
            findDependents(r5, r6, r3, r12)
            goto L_0x012a
        L_0x013c:
            r6 = 0
            r12 = 0
            if (r7 == 0) goto L_0x015d
            java.util.Iterator r4 = r7.iterator()
        L_0x0144:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x015d
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.HelperWidget r5 = (androidx.constraintlayout.solver.widgets.HelperWidget) r5
            androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup r7 = findDependents(r5, r6, r3, r12)
            r5.addDependents(r3, r6, r7)
            r7.cleanup(r3)
            r6 = 0
            r12 = 0
            goto L_0x0144
        L_0x015d:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x0185
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x0171:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0185
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 0
            r7 = 0
            findDependents(r5, r6, r3, r7)
            goto L_0x0171
        L_0x0185:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x01ad
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x0199:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x01ad
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 0
            r7 = 0
            findDependents(r5, r6, r3, r7)
            goto L_0x0199
        L_0x01ad:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x01d5
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x01c1:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x01d5
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 0
            r7 = 0
            findDependents(r5, r6, r3, r7)
            goto L_0x01c1
        L_0x01d5:
            r6 = 0
            r7 = 0
            if (r10 == 0) goto L_0x01ed
            java.util.Iterator r4 = r10.iterator()
        L_0x01dd:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x01ed
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r5
            findDependents(r5, r6, r3, r7)
            goto L_0x01dd
        L_0x01ed:
            if (r8 == 0) goto L_0x0204
            java.util.Iterator r4 = r8.iterator()
        L_0x01f3:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0204
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.Guideline r5 = (androidx.constraintlayout.solver.widgets.Guideline) r5
            r6 = 1
            findDependents(r5, r6, r3, r7)
            goto L_0x01f3
        L_0x0204:
            r6 = 1
            if (r9 == 0) goto L_0x0224
            java.util.Iterator r4 = r9.iterator()
        L_0x020b:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0224
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.HelperWidget r5 = (androidx.constraintlayout.solver.widgets.HelperWidget) r5
            androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup r8 = findDependents(r5, r6, r3, r7)
            r5.addDependents(r3, r6, r8)
            r8.cleanup(r3)
            r6 = 1
            r7 = 0
            goto L_0x020b
        L_0x0224:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x024c
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x0238:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x024c
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 1
            r7 = 0
            findDependents(r5, r6, r3, r7)
            goto L_0x0238
        L_0x024c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x0274
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x0260:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0274
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 1
            r7 = 0
            findDependents(r5, r6, r3, r7)
            goto L_0x0260
        L_0x0274:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x029c
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x0288:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x029c
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 1
            r7 = 0
            findDependents(r5, r6, r3, r7)
            goto L_0x0288
        L_0x029c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            java.util.HashSet r5 = r4.getDependents()
            if (r5 == 0) goto L_0x02c4
            java.util.HashSet r4 = r4.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x02b0:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x02c4
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = (androidx.constraintlayout.solver.widgets.ConstraintAnchor) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            r6 = 1
            r12 = 0
            findDependents(r5, r6, r3, r12)
            goto L_0x02b0
        L_0x02c4:
            r6 = 1
            r12 = 0
            if (r11 == 0) goto L_0x02dc
            java.util.Iterator r4 = r11.iterator()
        L_0x02cc:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x02dc
            java.lang.Object r5 = r4.next()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r5
            findDependents(r5, r6, r3, r12)
            goto L_0x02cc
        L_0x02dc:
            r4 = 0
        L_0x02dd:
            if (r4 >= r2) goto L_0x0309
            java.lang.Object r5 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r5
            boolean r6 = r5.oppositeDimensionsTied()
            if (r6 == 0) goto L_0x0306
            int r6 = r5.horizontalGroup
            androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup r6 = findGroup(r3, r6)
            int r5 = r5.verticalGroup
            androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup r5 = findGroup(r3, r5)
            if (r6 == 0) goto L_0x0306
            if (r5 == 0) goto L_0x0306
            r7 = 0
            r6.moveTo(r7, r5)
            r7 = 2
            r5.setOrientation(r7)
            r3.remove(r6)
        L_0x0306:
            int r4 = r4 + 1
            goto L_0x02dd
        L_0x0309:
            int r1 = r3.size()
            r2 = 1
            if (r1 > r2) goto L_0x0312
            r1 = 0
            return r1
        L_0x0312:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = r16.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r1 != r2) goto L_0x0354
            java.util.Iterator r1 = r3.iterator()
            r2 = r12
            r6 = 0
        L_0x0320:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x0345
            java.lang.Object r4 = r1.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup r4 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup) r4
            int r5 = r4.getOrientation()
            r7 = 1
            if (r5 != r7) goto L_0x0334
            goto L_0x0320
        L_0x0334:
            r5 = 0
            r4.setAuthoritative(r5)
            androidx.constraintlayout.solver.LinearSystem r7 = r16.getSystem()
            int r7 = r4.measureWrap((androidx.constraintlayout.solver.LinearSystem) r7, (int) r5)
            if (r7 <= r6) goto L_0x0320
            r2 = r4
            r6 = r7
            goto L_0x0320
        L_0x0345:
            if (r2 == 0) goto L_0x0354
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r0.setHorizontalDimensionBehaviour(r1)
            r0.setWidth(r6)
            r1 = 1
            r2.setAuthoritative(r1)
            goto L_0x0355
        L_0x0354:
            r2 = r12
        L_0x0355:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = r16.getVerticalDimensionBehaviour()
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r1 != r4) goto L_0x0399
            java.util.Iterator r1 = r3.iterator()
            r3 = r12
            r6 = 0
        L_0x0363:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x0388
            java.lang.Object r4 = r1.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup r4 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetGroup) r4
            int r5 = r4.getOrientation()
            if (r5 != 0) goto L_0x0376
            goto L_0x0363
        L_0x0376:
            r5 = 0
            r4.setAuthoritative(r5)
            androidx.constraintlayout.solver.LinearSystem r7 = r16.getSystem()
            r8 = 1
            int r7 = r4.measureWrap((androidx.constraintlayout.solver.LinearSystem) r7, (int) r8)
            if (r7 <= r6) goto L_0x0363
            r3 = r4
            r6 = r7
            goto L_0x0363
        L_0x0388:
            r5 = 0
            r8 = 1
            if (r3 == 0) goto L_0x039b
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r0.setVerticalDimensionBehaviour(r1)
            r0.setHeight(r6)
            r3.setAuthoritative(r8)
            r4 = r3
            goto L_0x039c
        L_0x0399:
            r5 = 0
            r8 = 1
        L_0x039b:
            r4 = r12
        L_0x039c:
            if (r2 != 0) goto L_0x03a3
            if (r4 == 0) goto L_0x03a1
            goto L_0x03a3
        L_0x03a1:
            r3 = 0
            goto L_0x03a4
        L_0x03a3:
            r3 = 1
        L_0x03a4:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.Grouping.simpleSolvingPass(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer):boolean");
    }

    private static WidgetGroup findGroup(ArrayList<WidgetGroup> arrayList, int i) {
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            WidgetGroup widgetGroup = arrayList.get(i2);
            if (i == widgetGroup.id) {
                return widgetGroup;
            }
        }
        return null;
    }

    public static WidgetGroup findDependents(ConstraintWidget constraintWidget, int i, ArrayList<WidgetGroup> arrayList, WidgetGroup widgetGroup) {
        int i2;
        int findGroupInDependents;
        if (i == 0) {
            i2 = constraintWidget.horizontalGroup;
        } else {
            i2 = constraintWidget.verticalGroup;
        }
        int i3 = 0;
        if (i2 != -1 && (widgetGroup == null || i2 != widgetGroup.id)) {
            int i4 = 0;
            while (true) {
                if (i4 >= arrayList.size()) {
                    break;
                }
                WidgetGroup widgetGroup2 = arrayList.get(i4);
                if (widgetGroup2.getId() == i2) {
                    if (widgetGroup != null) {
                        widgetGroup.moveTo(i, widgetGroup2);
                        arrayList.remove(widgetGroup);
                    }
                    widgetGroup = widgetGroup2;
                } else {
                    i4++;
                }
            }
        } else if (i2 != -1) {
            return widgetGroup;
        }
        if (widgetGroup == null) {
            if ((constraintWidget instanceof HelperWidget) && (findGroupInDependents = ((HelperWidget) constraintWidget).findGroupInDependents(i)) != -1) {
                int i5 = 0;
                while (true) {
                    if (i5 >= arrayList.size()) {
                        break;
                    }
                    WidgetGroup widgetGroup3 = arrayList.get(i5);
                    if (widgetGroup3.getId() == findGroupInDependents) {
                        widgetGroup = widgetGroup3;
                        break;
                    }
                    i5++;
                }
            }
            if (widgetGroup == null) {
                widgetGroup = new WidgetGroup(i);
            }
            arrayList.add(widgetGroup);
        }
        if (widgetGroup.add(constraintWidget)) {
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                ConstraintAnchor anchor = guideline.getAnchor();
                if (guideline.getOrientation() == 0) {
                    i3 = 1;
                }
                anchor.findDependents(i3, arrayList, widgetGroup);
            }
            if (i == 0) {
                constraintWidget.horizontalGroup = widgetGroup.getId();
                constraintWidget.mLeft.findDependents(i, arrayList, widgetGroup);
                constraintWidget.mRight.findDependents(i, arrayList, widgetGroup);
            } else {
                constraintWidget.verticalGroup = widgetGroup.getId();
                constraintWidget.mTop.findDependents(i, arrayList, widgetGroup);
                constraintWidget.mBaseline.findDependents(i, arrayList, widgetGroup);
                constraintWidget.mBottom.findDependents(i, arrayList, widgetGroup);
            }
            constraintWidget.mCenter.findDependents(i, arrayList, widgetGroup);
        }
        return widgetGroup;
    }
}
