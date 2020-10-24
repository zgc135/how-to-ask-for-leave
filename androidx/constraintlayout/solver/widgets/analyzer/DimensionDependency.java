package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;

class DimensionDependency extends DependencyNode {
    public int wrapValue;

    public DimensionDependency(WidgetRun widgetRun) {
        super(widgetRun);
        if (widgetRun instanceof HorizontalWidgetRun) {
            this.type = DependencyNode.Type.HORIZONTAL_DIMENSION;
        } else {
            this.type = DependencyNode.Type.VERTICAL_DIMENSION;
        }
    }

    public void resolve(int i) {
        if (!this.resolved) {
            this.resolved = true;
            this.value = i;
            for (Dependency dependency : this.dependencies) {
                dependency.update(dependency);
            }
        }
    }
}
