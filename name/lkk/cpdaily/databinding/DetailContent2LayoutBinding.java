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

public final class DetailContent2LayoutBinding implements ViewBinding {
    public final Guideline guideline19;
    public final Guideline guideline21;
    public final Guideline guideline22;
    public final Guideline guideline23;
    public final Guideline guideline24;
    public final Guideline guideline29;
    public final Guideline guideline30;
    public final Guideline guideline31;
    public final ImageView imageView2;
    private final CardView rootView;
    public final TextView textView13;
    public final TextView textView15;
    public final TextView textView20;
    public final TextView textView21;
    public final TextView textView22;
    public final TextView textView56;
    public final TextView textView57;

    private DetailContent2LayoutBinding(CardView cardView, Guideline guideline, Guideline guideline2, Guideline guideline3, Guideline guideline4, Guideline guideline5, Guideline guideline6, Guideline guideline7, Guideline guideline8, ImageView imageView, TextView textView, TextView textView2, TextView textView3, TextView textView4, TextView textView5, TextView textView6, TextView textView7) {
        this.rootView = cardView;
        this.guideline19 = guideline;
        this.guideline21 = guideline2;
        this.guideline22 = guideline3;
        this.guideline23 = guideline4;
        this.guideline24 = guideline5;
        this.guideline29 = guideline6;
        this.guideline30 = guideline7;
        this.guideline31 = guideline8;
        this.imageView2 = imageView;
        this.textView13 = textView;
        this.textView15 = textView2;
        this.textView20 = textView3;
        this.textView21 = textView4;
        this.textView22 = textView5;
        this.textView56 = textView6;
        this.textView57 = textView7;
    }

    public CardView getRoot() {
        return this.rootView;
    }

    public static DetailContent2LayoutBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static DetailContent2LayoutBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.detail_content2_layout, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static DetailContent2LayoutBinding bind(View view) {
        View view2 = view;
        int i = R.id.guideline19;
        Guideline guideline = (Guideline) view2.findViewById(R.id.guideline19);
        if (guideline != null) {
            i = R.id.guideline21;
            Guideline guideline2 = (Guideline) view2.findViewById(R.id.guideline21);
            if (guideline2 != null) {
                i = R.id.guideline22;
                Guideline guideline3 = (Guideline) view2.findViewById(R.id.guideline22);
                if (guideline3 != null) {
                    i = R.id.guideline23;
                    Guideline guideline4 = (Guideline) view2.findViewById(R.id.guideline23);
                    if (guideline4 != null) {
                        i = R.id.guideline24;
                        Guideline guideline5 = (Guideline) view2.findViewById(R.id.guideline24);
                        if (guideline5 != null) {
                            i = R.id.guideline29;
                            Guideline guideline6 = (Guideline) view2.findViewById(R.id.guideline29);
                            if (guideline6 != null) {
                                i = R.id.guideline30;
                                Guideline guideline7 = (Guideline) view2.findViewById(R.id.guideline30);
                                if (guideline7 != null) {
                                    i = R.id.guideline31;
                                    Guideline guideline8 = (Guideline) view2.findViewById(R.id.guideline31);
                                    if (guideline8 != null) {
                                        i = R.id.imageView2;
                                        ImageView imageView = (ImageView) view2.findViewById(R.id.imageView2);
                                        if (imageView != null) {
                                            i = R.id.textView13;
                                            TextView textView = (TextView) view2.findViewById(R.id.textView13);
                                            if (textView != null) {
                                                i = R.id.textView15;
                                                TextView textView2 = (TextView) view2.findViewById(R.id.textView15);
                                                if (textView2 != null) {
                                                    i = R.id.textView20;
                                                    TextView textView3 = (TextView) view2.findViewById(R.id.textView20);
                                                    if (textView3 != null) {
                                                        i = R.id.textView21;
                                                        TextView textView4 = (TextView) view2.findViewById(R.id.textView21);
                                                        if (textView4 != null) {
                                                            i = R.id.textView22;
                                                            TextView textView5 = (TextView) view2.findViewById(R.id.textView22);
                                                            if (textView5 != null) {
                                                                i = R.id.textView56;
                                                                TextView textView6 = (TextView) view2.findViewById(R.id.textView56);
                                                                if (textView6 != null) {
                                                                    i = R.id.textView57;
                                                                    TextView textView7 = (TextView) view2.findViewById(R.id.textView57);
                                                                    if (textView7 != null) {
                                                                        return new DetailContent2LayoutBinding((CardView) view2, guideline, guideline2, guideline3, guideline4, guideline5, guideline6, guideline7, guideline8, imageView, textView, textView2, textView3, textView4, textView5, textView6, textView7);
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
