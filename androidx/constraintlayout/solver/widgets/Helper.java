package androidx.constraintlayout.solver.widgets;

public interface Helper {
    void add(ConstraintWidget constraintWidget);

    void removeAllIds();

    void updateConstraints(ConstraintWidgetContainer constraintWidgetContainer);
}
