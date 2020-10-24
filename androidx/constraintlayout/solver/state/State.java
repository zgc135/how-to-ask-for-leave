package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.solver.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.solver.state.helpers.BarrierReference;
import androidx.constraintlayout.solver.state.helpers.GuidelineReference;
import androidx.constraintlayout.solver.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.solver.state.helpers.VerticalChainReference;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.HashMap;
import java.util.Iterator;

public class State {
    static final int CONSTRAINT_RATIO = 2;
    static final int CONSTRAINT_SPREAD = 0;
    static final int CONSTRAINT_WRAP = 1;
    public static final Integer PARENT = 0;
    static final int UNKNOWN = -1;
    protected HashMap<Object, HelperReference> mHelperReferences = new HashMap<>();
    public final ConstraintReference mParent;
    protected HashMap<Object, Reference> mReferences = new HashMap<>();
    private int numHelpers;

    public enum Chain {
        SPREAD,
        SPREAD_INSIDE,
        PACKED
    }

    public enum Constraint {
        LEFT_TO_LEFT,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        RIGHT_TO_RIGHT,
        START_TO_START,
        START_TO_END,
        END_TO_START,
        END_TO_END,
        TOP_TO_TOP,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
        BOTTOM_TO_BOTTOM,
        BASELINE_TO_BASELINE,
        CENTER_HORIZONTALLY,
        CENTER_VERTICALLY
    }

    public enum Direction {
        LEFT,
        RIGHT,
        START,
        END,
        TOP,
        BOTTOM
    }

    public enum Helper {
        HORIZONTAL_CHAIN,
        VERTICAL_CHAIN,
        ALIGN_HORIZONTALLY,
        ALIGN_VERTICALLY,
        BARRIER,
        LAYER,
        FLOW
    }

    public State() {
        ConstraintReference constraintReference = new ConstraintReference(this);
        this.mParent = constraintReference;
        this.numHelpers = 0;
        this.mReferences.put(PARENT, constraintReference);
    }

    public void reset() {
        this.mHelperReferences.clear();
    }

    public int convertDimension(Object obj) {
        if (obj instanceof Float) {
            return ((Float) obj).intValue();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        return 0;
    }

    public ConstraintReference createConstraintReference(Object obj) {
        return new ConstraintReference(this);
    }

    public State width(Dimension dimension) {
        return setWidth(dimension);
    }

    public State height(Dimension dimension) {
        return setHeight(dimension);
    }

    public State setWidth(Dimension dimension) {
        this.mParent.setWidth(dimension);
        return this;
    }

    public State setHeight(Dimension dimension) {
        this.mParent.setHeight(dimension);
        return this;
    }

    /* access modifiers changed from: package-private */
    public Reference reference(Object obj) {
        return this.mReferences.get(obj);
    }

    public ConstraintReference constraints(Object obj) {
        Reference reference = this.mReferences.get(obj);
        if (reference == null) {
            reference = createConstraintReference(obj);
            this.mReferences.put(obj, reference);
            reference.setKey(obj);
        }
        if (reference instanceof ConstraintReference) {
            return (ConstraintReference) reference;
        }
        return null;
    }

    private String createHelperKey() {
        StringBuilder sb = new StringBuilder();
        sb.append("__HELPER_KEY_");
        int i = this.numHelpers;
        this.numHelpers = i + 1;
        sb.append(i);
        sb.append("__");
        return sb.toString();
    }

    public HelperReference helper(Object obj, Helper helper) {
        HelperReference helperReference;
        if (obj == null) {
            obj = createHelperKey();
        }
        HelperReference helperReference2 = this.mHelperReferences.get(obj);
        if (helperReference2 == null) {
            int i = AnonymousClass1.$SwitchMap$androidx$constraintlayout$solver$state$State$Helper[helper.ordinal()];
            if (i == 1) {
                helperReference = new HorizontalChainReference(this);
            } else if (i == 2) {
                helperReference = new VerticalChainReference(this);
            } else if (i == 3) {
                helperReference = new AlignHorizontallyReference(this);
            } else if (i == 4) {
                helperReference = new AlignVerticallyReference(this);
            } else if (i != 5) {
                helperReference2 = new HelperReference(this, helper);
                this.mHelperReferences.put(obj, helperReference2);
            } else {
                helperReference = new BarrierReference(this);
            }
            helperReference2 = helperReference;
            this.mHelperReferences.put(obj, helperReference2);
        }
        return helperReference2;
    }

    /* renamed from: androidx.constraintlayout.solver.state.State$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$solver$state$State$Helper;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                androidx.constraintlayout.solver.state.State$Helper[] r0 = androidx.constraintlayout.solver.state.State.Helper.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$androidx$constraintlayout$solver$state$State$Helper = r0
                androidx.constraintlayout.solver.state.State$Helper r1 = androidx.constraintlayout.solver.state.State.Helper.HORIZONTAL_CHAIN     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$androidx$constraintlayout$solver$state$State$Helper     // Catch:{ NoSuchFieldError -> 0x001d }
                androidx.constraintlayout.solver.state.State$Helper r1 = androidx.constraintlayout.solver.state.State.Helper.VERTICAL_CHAIN     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$androidx$constraintlayout$solver$state$State$Helper     // Catch:{ NoSuchFieldError -> 0x0028 }
                androidx.constraintlayout.solver.state.State$Helper r1 = androidx.constraintlayout.solver.state.State.Helper.ALIGN_HORIZONTALLY     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$androidx$constraintlayout$solver$state$State$Helper     // Catch:{ NoSuchFieldError -> 0x0033 }
                androidx.constraintlayout.solver.state.State$Helper r1 = androidx.constraintlayout.solver.state.State.Helper.ALIGN_VERTICALLY     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$androidx$constraintlayout$solver$state$State$Helper     // Catch:{ NoSuchFieldError -> 0x003e }
                androidx.constraintlayout.solver.state.State$Helper r1 = androidx.constraintlayout.solver.state.State.Helper.BARRIER     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.state.State.AnonymousClass1.<clinit>():void");
        }
    }

    public GuidelineReference horizontalGuideline(Object obj) {
        return guideline(obj, 0);
    }

    public GuidelineReference verticalGuideline(Object obj) {
        return guideline(obj, 1);
    }

    public GuidelineReference guideline(Object obj, int i) {
        Reference reference = this.mReferences.get(obj);
        GuidelineReference guidelineReference = reference;
        if (reference == null) {
            GuidelineReference guidelineReference2 = new GuidelineReference(this);
            guidelineReference2.setOrientation(i);
            guidelineReference2.setKey(obj);
            this.mReferences.put(obj, guidelineReference2);
            guidelineReference = guidelineReference2;
        }
        return (GuidelineReference) guidelineReference;
    }

    public BarrierReference barrier(Object obj, Direction direction) {
        BarrierReference barrierReference = (BarrierReference) helper(obj, Helper.BARRIER);
        barrierReference.setBarrierDirection(direction);
        return barrierReference;
    }

    public VerticalChainReference verticalChain(Object... objArr) {
        VerticalChainReference verticalChainReference = (VerticalChainReference) helper((Object) null, Helper.VERTICAL_CHAIN);
        verticalChainReference.add(objArr);
        return verticalChainReference;
    }

    public HorizontalChainReference horizontalChain(Object... objArr) {
        HorizontalChainReference horizontalChainReference = (HorizontalChainReference) helper((Object) null, Helper.HORIZONTAL_CHAIN);
        horizontalChainReference.add(objArr);
        return horizontalChainReference;
    }

    public AlignHorizontallyReference centerHorizontally(Object... objArr) {
        AlignHorizontallyReference alignHorizontallyReference = (AlignHorizontallyReference) helper((Object) null, Helper.ALIGN_HORIZONTALLY);
        alignHorizontallyReference.add(objArr);
        return alignHorizontallyReference;
    }

    public AlignVerticallyReference centerVertically(Object... objArr) {
        AlignVerticallyReference alignVerticallyReference = (AlignVerticallyReference) helper((Object) null, Helper.ALIGN_VERTICALLY);
        alignVerticallyReference.add(objArr);
        return alignVerticallyReference;
    }

    public void directMapping() {
        for (Object next : this.mReferences.keySet()) {
            constraints(next).setView(next);
        }
    }

    public void map(Object obj, Object obj2) {
        constraints(obj).setView(obj2);
    }

    public void apply(ConstraintWidgetContainer constraintWidgetContainer) {
        constraintWidgetContainer.removeAllChildren();
        this.mParent.getWidth().apply(this, constraintWidgetContainer, 0);
        this.mParent.getHeight().apply(this, constraintWidgetContainer, 1);
        for (Object next : this.mHelperReferences.keySet()) {
            HelperWidget helperWidget = this.mHelperReferences.get(next).getHelperWidget();
            if (helperWidget != null) {
                Reference reference = this.mReferences.get(next);
                if (reference == null) {
                    reference = constraints(next);
                }
                reference.setConstraintWidget(helperWidget);
            }
        }
        for (Object obj : this.mReferences.keySet()) {
            Reference reference2 = this.mReferences.get(obj);
            if (reference2 != this.mParent) {
                ConstraintWidget constraintWidget = reference2.getConstraintWidget();
                constraintWidget.setParent((ConstraintWidget) null);
                if (reference2 instanceof GuidelineReference) {
                    reference2.apply();
                }
                constraintWidgetContainer.add(constraintWidget);
            } else {
                reference2.setConstraintWidget(constraintWidgetContainer);
            }
        }
        for (Object obj2 : this.mHelperReferences.keySet()) {
            HelperReference helperReference = this.mHelperReferences.get(obj2);
            if (helperReference.getHelperWidget() != null) {
                Iterator<Object> it = helperReference.mReferences.iterator();
                while (it.hasNext()) {
                    helperReference.getHelperWidget().add(this.mReferences.get(it.next()).getConstraintWidget());
                }
                helperReference.apply();
            }
        }
        for (Object obj3 : this.mReferences.keySet()) {
            this.mReferences.get(obj3).apply();
        }
    }
}
