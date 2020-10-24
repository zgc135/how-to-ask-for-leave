package name.lkk.cpdaily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import name.lkk.cpdaily.R;

public final class MainFragmentBinding implements ViewBinding {
    public final Button button2;
    public final ConstraintLayout frameLayout;
    public final Guideline guideline;
    private final ConstraintLayout rootView;
    public final TextView textView23;

    private MainFragmentBinding(ConstraintLayout constraintLayout, Button button, ConstraintLayout constraintLayout2, Guideline guideline2, TextView textView) {
        this.rootView = constraintLayout;
        this.button2 = button;
        this.frameLayout = constraintLayout2;
        this.guideline = guideline2;
        this.textView23 = textView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static MainFragmentBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static MainFragmentBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.main_fragment, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static MainFragmentBinding bind(View view) {
        int i = R.id.button2;
        Button button = (Button) view.findViewById(R.id.button2);
        if (button != null) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            i = R.id.guideline;
            Guideline guideline2 = (Guideline) view.findViewById(R.id.guideline);
            if (guideline2 != null) {
                i = R.id.textView23;
                TextView textView = (TextView) view.findViewById(R.id.textView23);
                if (textView != null) {
                    return new MainFragmentBinding(constraintLayout, button, constraintLayout, guideline2, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
