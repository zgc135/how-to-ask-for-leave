package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.motion.widget.MotionLayout;

public abstract class TransitionAdapter implements MotionLayout.TransitionListener {
    public void onTransitionChange(MotionLayout motionLayout, int i, int i2, float f) {
    }

    public void onTransitionCompleted(MotionLayout motionLayout, int i) {
    }

    public void onTransitionStarted(MotionLayout motionLayout, int i, int i2) {
    }

    public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean z, float f) {
    }
}
