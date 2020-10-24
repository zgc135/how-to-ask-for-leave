package name.lkk.cpdaily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import name.lkk.cpdaily.R;

public final class DetailContent1LayoutBinding implements ViewBinding {
    public final Guideline guideline13;
    public final Guideline guideline14;
    public final Guideline guideline15;
    public final Guideline guideline16;
    public final Guideline guideline17;
    public final Guideline guideline18;
    public final Guideline guideline34;
    public final Guideline guideline41;
    public final ImageView imageView;
    public final ImageView imageView4;
    private final CardView rootView;
    public final TextView textView12;
    public final TextView textView51;
    public final TextView textView52;
    public final TextView textView53;
    public final TextView textView54;
    public final TextView textView55;

    private DetailContent1LayoutBinding(CardView cardView, Guideline guideline, Guideline guideline2, Guideline guideline3, Guideline guideline4, Guideline guideline5, Guideline guideline6, Guideline guideline7, Guideline guideline8, ImageView imageView2, ImageView imageView3, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6) {
        this.rootView = cardView;
        this.guideline13 = guideline;
        this.guideline14 = guideline2;
        this.guideline15 = guideline3;
        this.guideline16 = guideline4;
        this.guideline17 = guideline5;
        this.guideline18 = guideline6;
        this.guideline34 = guideline7;
        this.guideline41 = guideline8;
        this.imageView = imageView2;
        this.imageView4 = imageView3;
        this.textView12 = textView;
        this.textView51 = textView2;
        this.textView52 = textView3;
        this.textView53 = textView4;
        this.textView54 = textView5;
        this.textView55 = textView6;
    }

    public CardView getRoot() {
        return this.rootView;
    }

    public static DetailContent1LayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static DetailContent1LayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.detail_content1_layout, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static DetailContent1LayoutBinding bind(View view) {
        View view2 = view;
        int i = R.id.guideline13;
        Guideline guideline = (Guideline) view2.findViewById(R.id.guideline13);
        if (guideline != null) {
            i = R.id.guideline14;
            Guideline guideline2 = (Guideline) view2.findViewById(R.id.guideline14);
            if (guideline2 != null) {
                i = R.id.guideline15;
                Guideline guideline3 = (Guideline) view2.findViewById(R.id.guideline15);
                if (guideline3 != null) {
                    i = R.id.guideline16;
                    Guideline guideline4 = (Guideline) view2.findViewById(R.id.guideline16);
                    if (guideline4 != null) {
                        i = R.id.guideline17;
                        Guideline guideline5 = (Guideline) view2.findViewById(R.id.guideline17);
                        if (guideline5 != null) {
                            i = R.id.guideline18;
                            Guideline guideline6 = (Guideline) view2.findViewById(R.id.guideline18);
                            if (guideline6 != null) {
                                i = R.id.guideline34;
                                Guideline guideline7 = (Guideline) view2.findViewById(R.id.guideline34);
                                if (guideline7 != null) {
                                    i = R.id.guideline41;
                                    Guideline guideline8 = (Guideline) view2.findViewById(R.id.guideline41);
                                    if (guideline8 != null) {
                                        i = R.id.imageView;
                                        ImageView imageView2 = (ImageView) view2.findViewById(R.id.imageView);
                                        if (imageView2 != null) {
                                            i = R.id.imageView4;
                                            ImageView imageView3 = (ImageView) view2.findViewById(R.id.imageView4);
                                            if (imageView3 != null) {
                                                i = R.id.textView12;
                                                TextView textView = (TextView) view2.findViewById(R.id.textView12);
                                                if (textView != null) {
                                                    i = R.id.textView51;
                                                    TextView textView2 = (TextView) view2.findViewById(R.id.textView51);
                                                    if (textView2 != null) {
                                                        i = R.id.textView52;
                                                        TextView textView3 = (TextView) view2.findViewById(R.id.textView52);
                                                        if (textView3 != null) {
                                                            i = R.id.textView53;
                                                            TextView textView4 = (TextView) view2.findViewById(R.id.textView53);
                                                            if (textView4 != null) {
                                                                i = R.id.textView54;
                                                                TextView textView5 = (TextView) view2.findViewById(R.id.textView54);
                                                                if (textView5 != null) {
                                                                    i = R.id.textView55;
                                                                    TextView textView6 = (TextView) view2.findViewById(R.id.textView55);
                                                                    if (textView6 != null) {
                                                                        return new DetailContent1LayoutBinding((CardView) view2, guideline, guideline2, guideline3, guideline4, guideline5, guideline6, guideline7, guideline8, imageView2, imageView3, textView, textView2, textView3, textView4, textView5, textView6);
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
