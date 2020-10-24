package name.lkk.cpdaily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import name.lkk.cpdaily.R;

public final class ActionbarLayoutBinding implements ViewBinding {
    public final TextView barText;
    public final ConstraintLayout frameLayout3;
    public final Guideline guideline25;
    public final Guideline guideline26;
    public final ImageView imageView6;
    private final ConstraintLayout rootView;

    private ActionbarLayoutBinding(ConstraintLayout constraintLayout, TextView textView, ConstraintLayout constraintLayout2, Guideline guideline, Guideline guideline2, ImageView imageView) {
        this.rootView = constraintLayout;
        this.barText = textView;
        this.frameLayout3 = constraintLayout2;
        this.guideline25 = guideline;
        this.guideline26 = guideline2;
        this.imageView6 = imageView;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ActionbarLayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static ActionbarLayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.actionbar_layout, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ActionbarLayoutBinding bind(View view) {
        int i = R.id.bar_text;
        TextView textView = (TextView) view.findViewById(R.id.bar_text);
        if (textView != null) {
            ConstraintLayout constraintLayout = (ConstraintLayout) view;
            i = R.id.guideline25;
            Guideline guideline = (Guideline) view.findViewById(R.id.guideline25);
            if (guideline != null) {
                i = R.id.guideline26;
                Guideline guideline2 = (Guideline) view.findViewById(R.id.guideline26);
                if (guideline2 != null) {
                    i = R.id.imageView6;
                    ImageView imageView = (ImageView) view.findViewById(R.id.imageView6);
                    if (imageView != null) {
                        return new ActionbarLayoutBinding(constraintLayout, textView, constraintLayout, guideline, guideline2, imageView);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
