package name.lkk.cpdaily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import name.lkk.cpdaily.R;

public final class FragmentLeaveBinding implements ViewBinding {
    public final TextView barText;
    public final Button button3;
    public final ConstraintLayout constraintLayout;
    public final ConstraintLayout frameLayout4;
    public final Guideline guideline25;
    public final Guideline guideline26;
    public final Guideline guideline33;
    public final Guideline guideline35;
    public final Guideline guideline37;
    public final Guideline guideline39;
    public final Guideline guideline4;
    public final Guideline guideline40;
    public final ImageView imageView6;
    public final ImageView imageView7;
    public final ImageView imageView8;
    private final ConstraintLayout rootView;
    public final TextView textView11;
    public final TextView textView24;
    public final TextView textView9;
    public final Toolbar toolbar;

    private FragmentLeaveBinding(ConstraintLayout constraintLayout2, TextView textView, Button button, ConstraintLayout constraintLayout3, ConstraintLayout constraintLayout4, Guideline guideline, Guideline guideline2, Guideline guideline3, Guideline guideline5, Guideline guideline6, Guideline guideline7, Guideline guideline8, Guideline guideline9, ImageView imageView, ImageView imageView2, ImageView imageView3, TextView textView2, TextView textView3, TextView textView4, Toolbar toolbar2) {
        this.rootView = constraintLayout2;
        this.barText = textView;
        this.button3 = button;
        this.constraintLayout = constraintLayout3;
        this.frameLayout4 = constraintLayout4;
        this.guideline25 = guideline;
        this.guideline26 = guideline2;
        this.guideline33 = guideline3;
        this.guideline35 = guideline5;
        this.guideline37 = guideline6;
        this.guideline39 = guideline7;
        this.guideline4 = guideline8;
        this.guideline40 = guideline9;
        this.imageView6 = imageView;
        this.imageView7 = imageView2;
        this.imageView8 = imageView3;
        this.textView11 = textView2;
        this.textView24 = textView3;
        this.textView9 = textView4;
        this.toolbar = toolbar2;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentLeaveBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static FragmentLeaveBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_leave, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentLeaveBinding bind(View view) {
        View view2 = view;
        int i = R.id.bar_text;
        TextView textView = (TextView) view2.findViewById(R.id.bar_text);
        if (textView != null) {
            i = R.id.button3;
            Button button = (Button) view2.findViewById(R.id.button3);
            if (button != null) {
                i = R.id.constraintLayout;
                ConstraintLayout constraintLayout2 = (ConstraintLayout) view2.findViewById(R.id.constraintLayout);
                if (constraintLayout2 != null) {
                    ConstraintLayout constraintLayout3 = (ConstraintLayout) view2;
                    i = R.id.guideline25;
                    Guideline guideline = (Guideline) view2.findViewById(R.id.guideline25);
                    if (guideline != null) {
                        i = R.id.guideline26;
                        Guideline guideline2 = (Guideline) view2.findViewById(R.id.guideline26);
                        if (guideline2 != null) {
                            i = R.id.guideline33;
                            Guideline guideline3 = (Guideline) view2.findViewById(R.id.guideline33);
                            if (guideline3 != null) {
                                i = R.id.guideline35;
                                Guideline guideline5 = (Guideline) view2.findViewById(R.id.guideline35);
                                if (guideline5 != null) {
                                    i = R.id.guideline37;
                                    Guideline guideline6 = (Guideline) view2.findViewById(R.id.guideline37);
                                    if (guideline6 != null) {
                                        i = R.id.guideline39;
                                        Guideline guideline7 = (Guideline) view2.findViewById(R.id.guideline39);
                                        if (guideline7 != null) {
                                            i = R.id.guideline4;
                                            Guideline guideline8 = (Guideline) view2.findViewById(R.id.guideline4);
                                            if (guideline8 != null) {
                                                i = R.id.guideline40;
                                                Guideline guideline9 = (Guideline) view2.findViewById(R.id.guideline40);
                                                if (guideline9 != null) {
                                                    i = R.id.imageView6;
                                                    ImageView imageView = (ImageView) view2.findViewById(R.id.imageView6);
                                                    if (imageView != null) {
                                                        i = R.id.imageView7;
                                                        ImageView imageView2 = (ImageView) view2.findViewById(R.id.imageView7);
                                                        if (imageView2 != null) {
                                                            i = R.id.imageView8;
                                                            ImageView imageView3 = (ImageView) view2.findViewById(R.id.imageView8);
                                                            if (imageView3 != null) {
                                                                i = R.id.textView11;
                                                                TextView textView2 = (TextView) view2.findViewById(R.id.textView11);
                                                                if (textView2 != null) {
                                                                    i = R.id.textView24;
                                                                    TextView textView3 = (TextView) view2.findViewById(R.id.textView24);
                                                                    if (textView3 != null) {
                                                                        i = R.id.textView9;
                                                                        TextView textView4 = (TextView) view2.findViewById(R.id.textView9);
                                                                        if (textView4 != null) {
                                                                            i = R.id.toolbar;
                                                                            Toolbar toolbar2 = (Toolbar) view2.findViewById(R.id.toolbar);
                                                                            if (toolbar2 != null) {
                                                                                return new FragmentLeaveBinding(constraintLayout3, textView, button, constraintLayout2, constraintLayout3, guideline, guideline2, guideline3, guideline5, guideline6, guideline7, guideline8, guideline9, imageView, imageView2, imageView3, textView2, textView3, textView4, toolbar2);
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
