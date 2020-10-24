package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import java.util.ArrayList;

public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, ArrayList<ConstraintWidget> arrayList, int i) {
        ChainHead[] chainHeadArr;
        int i2;
        int i3;
        if (i == 0) {
            i3 = constraintWidgetContainer.mHorizontalChainsSize;
            chainHeadArr = constraintWidgetContainer.mHorizontalChainsArray;
            i2 = 0;
        } else {
            int i4 = constraintWidgetContainer.mVerticalChainsSize;
            chainHeadArr = constraintWidgetContainer.mVerticalChainsArray;
            i3 = i4;
            i2 = 2;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            ChainHead chainHead = chainHeadArr[i5];
            chainHead.define();
            if (arrayList == null || (arrayList != null && arrayList.contains(chainHead.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i2, chainHead);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v0, resolved type: androidx.constraintlayout.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v1, resolved type: androidx.constraintlayout.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v2, resolved type: androidx.constraintlayout.solver.widgets.ConstraintWidget} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v3, resolved type: androidx.constraintlayout.solver.SolverVariable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r21v4, resolved type: androidx.constraintlayout.solver.widgets.ConstraintWidget} */
    /* JADX WARNING: type inference failed for: r4v11, types: [androidx.constraintlayout.solver.SolverVariable] */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        if (r2.mHorizontalChainStyle == 2) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0048, code lost:
        if (r2.mVerticalChainStyle == 2) goto L_0x004a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004c, code lost:
        r5 = false;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x01ad  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01db  */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x01df  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x03c9  */
    /* JADX WARNING: Removed duplicated region for block: B:220:0x03de  */
    /* JADX WARNING: Removed duplicated region for block: B:221:0x03e1  */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x03e7  */
    /* JADX WARNING: Removed duplicated region for block: B:272:0x04bd  */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x04f2  */
    /* JADX WARNING: Removed duplicated region for block: B:287:0x0519  */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x051e  */
    /* JADX WARNING: Removed duplicated region for block: B:291:0x0524  */
    /* JADX WARNING: Removed duplicated region for block: B:292:0x0529  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x052d  */
    /* JADX WARNING: Removed duplicated region for block: B:298:0x053e  */
    /* JADX WARNING: Removed duplicated region for block: B:300:0x0541  */
    /* JADX WARNING: Removed duplicated region for block: B:315:0x03ca A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r36, androidx.constraintlayout.solver.LinearSystem r37, int r38, int r39, androidx.constraintlayout.solver.widgets.ChainHead r40) {
        /*
            r0 = r36
            r9 = r37
            r1 = r40
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r1.mFirst
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r1.mLast
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r1.mFirstVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r1.mLastVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.mHead
            float r3 = r1.mTotalWeight
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r1.mFirstMatchConstraintWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r1.mLastMatchConstraintWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r38]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r7 = 1
            if (r4 != r5) goto L_0x0021
            r4 = 1
            goto L_0x0022
        L_0x0021:
            r4 = 0
        L_0x0022:
            r5 = 2
            if (r38 != 0) goto L_0x0038
            int r8 = r2.mHorizontalChainStyle
            if (r8 != 0) goto L_0x002b
            r8 = 1
            goto L_0x002c
        L_0x002b:
            r8 = 0
        L_0x002c:
            int r14 = r2.mHorizontalChainStyle
            if (r14 != r7) goto L_0x0032
            r14 = 1
            goto L_0x0033
        L_0x0032:
            r14 = 0
        L_0x0033:
            int r15 = r2.mHorizontalChainStyle
            if (r15 != r5) goto L_0x004c
            goto L_0x004a
        L_0x0038:
            int r8 = r2.mVerticalChainStyle
            if (r8 != 0) goto L_0x003e
            r8 = 1
            goto L_0x003f
        L_0x003e:
            r8 = 0
        L_0x003f:
            int r14 = r2.mVerticalChainStyle
            if (r14 != r7) goto L_0x0045
            r14 = 1
            goto L_0x0046
        L_0x0045:
            r14 = 0
        L_0x0046:
            int r15 = r2.mVerticalChainStyle
            if (r15 != r5) goto L_0x004c
        L_0x004a:
            r5 = 1
            goto L_0x004d
        L_0x004c:
            r5 = 0
        L_0x004d:
            r7 = r10
            r15 = r14
            r14 = r8
            r8 = 0
        L_0x0051:
            r21 = 0
            if (r8 != 0) goto L_0x013b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r7.mListAnchors
            r6 = r6[r39]
            if (r5 == 0) goto L_0x005e
            r19 = 1
            goto L_0x0060
        L_0x005e:
            r19 = 4
        L_0x0060:
            int r22 = r6.getMargin()
            r23 = r3
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r7.mListDimensionBehaviors
            r3 = r3[r38]
            r24 = r8
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r8) goto L_0x0078
            int[] r3 = r7.mResolvedMatchConstraintDefault
            r3 = r3[r38]
            if (r3 != 0) goto L_0x0078
            r3 = 1
            goto L_0x0079
        L_0x0078:
            r3 = 0
        L_0x0079:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r6.mTarget
            if (r8 == 0) goto L_0x0087
            if (r7 == r10) goto L_0x0087
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r6.mTarget
            int r8 = r8.getMargin()
            int r22 = r22 + r8
        L_0x0087:
            r8 = r22
            if (r5 == 0) goto L_0x0094
            if (r7 == r10) goto L_0x0094
            if (r7 == r12) goto L_0x0094
            r22 = r15
            r19 = 8
            goto L_0x0096
        L_0x0094:
            r22 = r15
        L_0x0096:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r6.mTarget
            if (r15 == 0) goto L_0x00cc
            if (r7 != r12) goto L_0x00ab
            androidx.constraintlayout.solver.SolverVariable r15 = r6.mSolverVariable
            r25 = r14
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r14 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r14 = r14.mSolverVariable
            r26 = r2
            r2 = 6
            r9.addGreaterThan(r15, r14, r8, r2)
            goto L_0x00ba
        L_0x00ab:
            r26 = r2
            r25 = r14
            androidx.constraintlayout.solver.SolverVariable r2 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r14 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r14 = r14.mSolverVariable
            r15 = 8
            r9.addGreaterThan(r2, r14, r8, r15)
        L_0x00ba:
            if (r3 == 0) goto L_0x00c0
            if (r5 != 0) goto L_0x00c0
            r2 = 5
            goto L_0x00c2
        L_0x00c0:
            r2 = r19
        L_0x00c2:
            androidx.constraintlayout.solver.SolverVariable r3 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            r9.addEquality(r3, r6, r8, r2)
            goto L_0x00d0
        L_0x00cc:
            r26 = r2
            r25 = r14
        L_0x00d0:
            if (r4 == 0) goto L_0x0108
            int r2 = r7.getVisibility()
            r3 = 8
            if (r2 == r3) goto L_0x00f6
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r7.mListDimensionBehaviors
            r2 = r2[r38]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r3) goto L_0x00f6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r7.mListAnchors
            int r3 = r39 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r7.mListAnchors
            r3 = r3[r39]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r6 = 5
            r8 = 0
            r9.addGreaterThan(r2, r3, r8, r6)
            goto L_0x00f7
        L_0x00f6:
            r8 = 0
        L_0x00f7:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r7.mListAnchors
            r2 = r2[r39]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            r3 = r3[r39]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r6 = 8
            r9.addGreaterThan(r2, r3, r8, r6)
        L_0x0108:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r7.mListAnchors
            int r3 = r39 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x0129
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r3 = r3[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x0129
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r3 = r3[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mOwner
            if (r3 == r7) goto L_0x0127
            goto L_0x0129
        L_0x0127:
            r21 = r2
        L_0x0129:
            if (r21 == 0) goto L_0x0130
            r7 = r21
            r8 = r24
            goto L_0x0131
        L_0x0130:
            r8 = 1
        L_0x0131:
            r15 = r22
            r3 = r23
            r14 = r25
            r2 = r26
            goto L_0x0051
        L_0x013b:
            r26 = r2
            r23 = r3
            r25 = r14
            r22 = r15
            if (r13 == 0) goto L_0x01aa
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r11.mListAnchors
            int r3 = r39 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x01aa
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r13.mListDimensionBehaviors
            r6 = r6[r38]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 != r7) goto L_0x0163
            int[] r6 = r13.mResolvedMatchConstraintDefault
            r6 = r6[r38]
            if (r6 != 0) goto L_0x0163
            r6 = 1
            goto L_0x0164
        L_0x0163:
            r6 = 0
        L_0x0164:
            if (r6 == 0) goto L_0x017e
            if (r5 != 0) goto L_0x017e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r2.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.mOwner
            if (r6 != r0) goto L_0x017e
            androidx.constraintlayout.solver.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            int r8 = r2.getMargin()
            int r8 = -r8
            r14 = 5
            r9.addEquality(r6, r7, r8, r14)
            goto L_0x0196
        L_0x017e:
            r14 = 5
            if (r5 == 0) goto L_0x0196
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r2.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.mOwner
            if (r6 != r0) goto L_0x0196
            androidx.constraintlayout.solver.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            int r8 = r2.getMargin()
            int r8 = -r8
            r15 = 4
            r9.addEquality(r6, r7, r8, r15)
        L_0x0196:
            androidx.constraintlayout.solver.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r11.mListAnchors
            r3 = r7[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            int r2 = r2.getMargin()
            int r2 = -r2
            r7 = 6
            r9.addLowerThan(r6, r3, r2, r7)
            goto L_0x01ab
        L_0x01aa:
            r14 = 5
        L_0x01ab:
            if (r4 == 0) goto L_0x01c8
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            int r2 = r39 + 1
            r0 = r0[r2]
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r11.mListAnchors
            r3 = r3[r2]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r11.mListAnchors
            r2 = r4[r2]
            int r2 = r2.getMargin()
            r4 = 8
            r9.addGreaterThan(r0, r3, r2, r4)
        L_0x01c8:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r0 = r1.mWeightedMatchConstraintsWidgets
            if (r0 == 0) goto L_0x027f
            int r2 = r0.size()
            r3 = 1
            if (r2 <= r3) goto L_0x027f
            boolean r4 = r1.mHasUndefinedWeights
            if (r4 == 0) goto L_0x01df
            boolean r4 = r1.mHasComplexMatchWeights
            if (r4 != 0) goto L_0x01df
            int r4 = r1.mWidgetsMatchCount
            float r4 = (float) r4
            goto L_0x01e1
        L_0x01df:
            r4 = r23
        L_0x01e1:
            r6 = 0
            r7 = r21
            r8 = 0
            r28 = 0
        L_0x01e7:
            if (r8 >= r2) goto L_0x027f
            java.lang.Object r15 = r0.get(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r15
            float[] r3 = r15.mWeight
            r3 = r3[r38]
            int r18 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r18 >= 0) goto L_0x0215
            boolean r3 = r1.mHasComplexMatchWeights
            if (r3 == 0) goto L_0x0210
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r15.mListAnchors
            int r18 = r39 + 1
            r3 = r3[r18]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r15 = r15.mListAnchors
            r15 = r15[r39]
            androidx.constraintlayout.solver.SolverVariable r15 = r15.mSolverVariable
            r6 = 0
            r14 = 4
            r9.addEquality(r3, r15, r6, r14)
            r14 = 0
            goto L_0x022e
        L_0x0210:
            r14 = 4
            r3 = 1065353216(0x3f800000, float:1.0)
            r6 = 0
            goto L_0x0216
        L_0x0215:
            r14 = 4
        L_0x0216:
            int r18 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1))
            if (r18 != 0) goto L_0x0233
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r15.mListAnchors
            int r18 = r39 + 1
            r3 = r3[r18]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r15 = r15.mListAnchors
            r15 = r15[r39]
            androidx.constraintlayout.solver.SolverVariable r15 = r15.mSolverVariable
            r6 = 8
            r14 = 0
            r9.addEquality(r3, r15, r14, r6)
        L_0x022e:
            r23 = r0
            r17 = r2
            goto L_0x0274
        L_0x0233:
            r14 = 0
            if (r7 == 0) goto L_0x026d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r7.mListAnchors
            r6 = r6[r39]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r7.mListAnchors
            int r17 = r39 + 1
            r7 = r7[r17]
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r14 = r15.mListAnchors
            r14 = r14[r39]
            androidx.constraintlayout.solver.SolverVariable r14 = r14.mSolverVariable
            r23 = r0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r15.mListAnchors
            r0 = r0[r17]
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r17 = r2
            androidx.constraintlayout.solver.ArrayRow r2 = r37.createRow()
            r27 = r2
            r29 = r4
            r30 = r3
            r31 = r6
            r32 = r7
            r33 = r14
            r34 = r0
            r27.createRowEqualMatchDimensions(r28, r29, r30, r31, r32, r33, r34)
            r9.addConstraint(r2)
            goto L_0x0271
        L_0x026d:
            r23 = r0
            r17 = r2
        L_0x0271:
            r28 = r3
            r7 = r15
        L_0x0274:
            int r8 = r8 + 1
            r2 = r17
            r0 = r23
            r3 = 1
            r6 = 0
            r14 = 5
            goto L_0x01e7
        L_0x027f:
            if (r12 == 0) goto L_0x02d8
            if (r12 == r13) goto L_0x0285
            if (r5 == 0) goto L_0x02d8
        L_0x0285:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r10.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r11.mListAnchors
            int r2 = r39 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r0.mTarget
            if (r3 == 0) goto L_0x0299
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r3 = r0
            goto L_0x029b
        L_0x0299:
            r3 = r21
        L_0x029b:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r1.mTarget
            if (r0 == 0) goto L_0x02a5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r5 = r0
            goto L_0x02a7
        L_0x02a5:
            r5 = r21
        L_0x02a7:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r12.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r2]
            if (r3 == 0) goto L_0x0503
            if (r5 == 0) goto L_0x0503
            if (r38 != 0) goto L_0x02ba
            r2 = r26
            float r2 = r2.mHorizontalBiasPercent
            goto L_0x02be
        L_0x02ba:
            r2 = r26
            float r2 = r2.mVerticalBiasPercent
        L_0x02be:
            r4 = r2
            int r6 = r0.getMargin()
            int r7 = r1.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r8 = r1.mSolverVariable
            r10 = 7
            r0 = r37
            r1 = r2
            r2 = r3
            r3 = r6
            r6 = r8
            r8 = r10
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x0503
        L_0x02d8:
            if (r25 == 0) goto L_0x03ce
            if (r12 == 0) goto L_0x03ce
            int r0 = r1.mWidgetsMatchCount
            if (r0 <= 0) goto L_0x02e9
            int r0 = r1.mWidgetsCount
            int r1 = r1.mWidgetsMatchCount
            if (r0 != r1) goto L_0x02e9
            r16 = 1
            goto L_0x02eb
        L_0x02e9:
            r16 = 0
        L_0x02eb:
            r14 = r12
            r15 = r14
        L_0x02ed:
            if (r14 == 0) goto L_0x0503
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r0 = r14.mNextChainWidget
            r0 = r0[r38]
            r8 = r0
        L_0x02f4:
            if (r8 == 0) goto L_0x0303
            int r0 = r8.getVisibility()
            r6 = 8
            if (r0 != r6) goto L_0x0305
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r0 = r8.mNextChainWidget
            r8 = r0[r38]
            goto L_0x02f4
        L_0x0303:
            r6 = 8
        L_0x0305:
            if (r8 != 0) goto L_0x030e
            if (r14 != r13) goto L_0x030a
            goto L_0x030e
        L_0x030a:
            r17 = r8
            goto L_0x03c1
        L_0x030e:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.SolverVariable r1 = r0.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            if (r2 == 0) goto L_0x031d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x031f
        L_0x031d:
            r2 = r21
        L_0x031f:
            if (r15 == r14) goto L_0x032a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            int r3 = r39 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x0341
        L_0x032a:
            if (r14 != r12) goto L_0x0341
            if (r15 != r14) goto L_0x0341
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r10.mListAnchors
            r2 = r2[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x033f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r10.mListAnchors
            r2 = r2[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x0341
        L_0x033f:
            r2 = r21
        L_0x0341:
            int r0 = r0.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r14.mListAnchors
            int r4 = r39 + 1
            r3 = r3[r4]
            int r3 = r3.getMargin()
            if (r8 == 0) goto L_0x0363
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r8.mListAnchors
            r5 = r5[r39]
            androidx.constraintlayout.solver.SolverVariable r7 = r5.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r14.mListAnchors
            r6 = r6[r4]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            r35 = r7
            r7 = r6
            r6 = r35
            goto L_0x0376
        L_0x0363:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r11.mListAnchors
            r5 = r5[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x036e
            androidx.constraintlayout.solver.SolverVariable r6 = r5.mSolverVariable
            goto L_0x0370
        L_0x036e:
            r6 = r21
        L_0x0370:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r14.mListAnchors
            r7 = r7[r4]
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
        L_0x0376:
            if (r5 == 0) goto L_0x037d
            int r5 = r5.getMargin()
            int r3 = r3 + r5
        L_0x037d:
            if (r15 == 0) goto L_0x0388
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r15.mListAnchors
            r5 = r5[r4]
            int r5 = r5.getMargin()
            int r0 = r0 + r5
        L_0x0388:
            if (r1 == 0) goto L_0x030a
            if (r2 == 0) goto L_0x030a
            if (r6 == 0) goto L_0x030a
            if (r7 == 0) goto L_0x030a
            if (r14 != r12) goto L_0x039a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r12.mListAnchors
            r0 = r0[r39]
            int r0 = r0.getMargin()
        L_0x039a:
            r5 = r0
            if (r14 != r13) goto L_0x03a8
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r13.mListAnchors
            r0 = r0[r4]
            int r0 = r0.getMargin()
            r17 = r0
            goto L_0x03aa
        L_0x03a8:
            r17 = r3
        L_0x03aa:
            if (r16 == 0) goto L_0x03af
            r18 = 8
            goto L_0x03b1
        L_0x03af:
            r18 = 5
        L_0x03b1:
            r4 = 1056964608(0x3f000000, float:0.5)
            r0 = r37
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r17
            r17 = r8
            r8 = r18
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x03c1:
            int r0 = r14.getVisibility()
            r8 = 8
            if (r0 == r8) goto L_0x03ca
            r15 = r14
        L_0x03ca:
            r14 = r17
            goto L_0x02ed
        L_0x03ce:
            r8 = 8
            if (r22 == 0) goto L_0x0503
            if (r12 == 0) goto L_0x0503
            int r0 = r1.mWidgetsMatchCount
            if (r0 <= 0) goto L_0x03e1
            int r0 = r1.mWidgetsCount
            int r1 = r1.mWidgetsMatchCount
            if (r0 != r1) goto L_0x03e1
            r16 = 1
            goto L_0x03e3
        L_0x03e1:
            r16 = 0
        L_0x03e3:
            r14 = r12
            r15 = r14
        L_0x03e5:
            if (r14 == 0) goto L_0x04a5
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r0 = r14.mNextChainWidget
            r0 = r0[r38]
        L_0x03eb:
            if (r0 == 0) goto L_0x03f8
            int r1 = r0.getVisibility()
            if (r1 != r8) goto L_0x03f8
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r0 = r0.mNextChainWidget
            r0 = r0[r38]
            goto L_0x03eb
        L_0x03f8:
            if (r14 == r12) goto L_0x0490
            if (r14 == r13) goto L_0x0490
            if (r0 == 0) goto L_0x0490
            if (r0 != r13) goto L_0x0403
            r7 = r21
            goto L_0x0404
        L_0x0403:
            r7 = r0
        L_0x0404:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.SolverVariable r1 = r0.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            if (r2 == 0) goto L_0x0412
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
        L_0x0412:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            int r3 = r39 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            int r0 = r0.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r14.mListAnchors
            r4 = r4[r3]
            int r4 = r4.getMargin()
            if (r7 == 0) goto L_0x043a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r7.mListAnchors
            r5 = r5[r39]
            androidx.constraintlayout.solver.SolverVariable r6 = r5.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r5.mTarget
            if (r8 == 0) goto L_0x0437
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r5.mTarget
            androidx.constraintlayout.solver.SolverVariable r8 = r8.mSolverVariable
            goto L_0x044b
        L_0x0437:
            r8 = r21
            goto L_0x044b
        L_0x043a:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            r5 = r5[r39]
            if (r5 == 0) goto L_0x0443
            androidx.constraintlayout.solver.SolverVariable r6 = r5.mSolverVariable
            goto L_0x0445
        L_0x0443:
            r6 = r21
        L_0x0445:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r8 = r14.mListAnchors
            r8 = r8[r3]
            androidx.constraintlayout.solver.SolverVariable r8 = r8.mSolverVariable
        L_0x044b:
            if (r5 == 0) goto L_0x0452
            int r5 = r5.getMargin()
            int r4 = r4 + r5
        L_0x0452:
            r17 = r4
            if (r15 == 0) goto L_0x045f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r15.mListAnchors
            r3 = r4[r3]
            int r3 = r3.getMargin()
            int r0 = r0 + r3
        L_0x045f:
            r3 = r0
            if (r16 == 0) goto L_0x0465
            r18 = 8
            goto L_0x0467
        L_0x0465:
            r18 = 4
        L_0x0467:
            if (r1 == 0) goto L_0x0485
            if (r2 == 0) goto L_0x0485
            if (r6 == 0) goto L_0x0485
            if (r8 == 0) goto L_0x0485
            r4 = 1056964608(0x3f000000, float:0.5)
            r0 = r37
            r5 = r6
            r19 = 4
            r6 = r8
            r20 = r7
            r7 = r17
            r17 = r15
            r15 = 8
            r8 = r18
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x048d
        L_0x0485:
            r20 = r7
            r17 = r15
            r15 = 8
            r19 = 4
        L_0x048d:
            r0 = r20
            goto L_0x0496
        L_0x0490:
            r17 = r15
            r15 = 8
            r19 = 4
        L_0x0496:
            int r1 = r14.getVisibility()
            if (r1 == r15) goto L_0x049d
            goto L_0x049f
        L_0x049d:
            r14 = r17
        L_0x049f:
            r15 = r14
            r8 = 8
            r14 = r0
            goto L_0x03e5
        L_0x04a5:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r12.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r10.mListAnchors
            r1 = r1[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            int r3 = r39 + 1
            r10 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r11.mListAnchors
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r14 = r2.mTarget
            if (r1 == 0) goto L_0x04f2
            if (r12 == r13) goto L_0x04cc
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            int r0 = r0.getMargin()
            r15 = 5
            r9.addEquality(r2, r1, r0, r15)
            goto L_0x04f3
        L_0x04cc:
            r15 = 5
            if (r14 == 0) goto L_0x04f3
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r3 = r1.mSolverVariable
            int r4 = r0.getMargin()
            r5 = 1056964608(0x3f000000, float:0.5)
            androidx.constraintlayout.solver.SolverVariable r6 = r10.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r7 = r14.mSolverVariable
            int r8 = r10.getMargin()
            r16 = 5
            r0 = r37
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r16
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x04f3
        L_0x04f2:
            r15 = 5
        L_0x04f3:
            if (r14 == 0) goto L_0x0503
            if (r12 == r13) goto L_0x0503
            androidx.constraintlayout.solver.SolverVariable r0 = r10.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r1 = r14.mSolverVariable
            int r2 = r10.getMargin()
            int r2 = -r2
            r9.addEquality(r0, r1, r2, r15)
        L_0x0503:
            if (r25 != 0) goto L_0x0507
            if (r22 == 0) goto L_0x056e
        L_0x0507:
            if (r12 == 0) goto L_0x056e
            if (r12 == r13) goto L_0x056e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r12.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            int r2 = r39 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r0.mTarget
            if (r3 == 0) goto L_0x051e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x0520
        L_0x051e:
            r3 = r21
        L_0x0520:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            if (r4 == 0) goto L_0x0529
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            goto L_0x052b
        L_0x0529:
            r4 = r21
        L_0x052b:
            if (r11 == r13) goto L_0x053e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r11.mListAnchors
            r4 = r4[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r4.mTarget
            if (r5 == 0) goto L_0x053b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r21 = r4
        L_0x053b:
            r5 = r21
            goto L_0x053f
        L_0x053e:
            r5 = r4
        L_0x053f:
            if (r12 != r13) goto L_0x0549
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r12.mListAnchors
            r0 = r0[r39]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r2]
        L_0x0549:
            if (r3 == 0) goto L_0x056e
            if (r5 == 0) goto L_0x056e
            r4 = 1056964608(0x3f000000, float:0.5)
            int r6 = r0.getMargin()
            if (r13 != 0) goto L_0x0556
            goto L_0x0557
        L_0x0556:
            r11 = r13
        L_0x0557:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r11.mListAnchors
            r2 = r7[r2]
            int r7 = r2.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r8 = r1.mSolverVariable
            r10 = 5
            r0 = r37
            r1 = r2
            r2 = r3
            r3 = r6
            r6 = r8
            r8 = r10
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
        L_0x056e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}
