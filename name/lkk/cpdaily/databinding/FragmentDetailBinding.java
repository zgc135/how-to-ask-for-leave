package name.lkk.cpdaily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import name.lkk.cpdaily.R;
import pl.droidsonroids.gif.GifImageView;

public final class FragmentDetailBinding implements ViewBinding {
    public final TextView barText;
    public final ConstraintLayout constraintLayout4;
    public final DetailContent1LayoutBinding detailContent1Layout;
    public final DetailContent2LayoutBinding detailContent2Layout;
    public final GifImageView gifView;
    public final Guideline guideline25;
    public final Guideline guideline26;
    public final Guideline guideline36;
    public final Guideline guideline38;
    public final Guideline guideline43;
    public final Guideline guideline44;
    public final ImageView imageView3;
    public final ImageView imageView6;
    public final ConstraintLayout linearLayout;
    private final FrameLayout rootView;
    public final TextView time;
    public final Toolbar toolbar1;

    private FragmentDetailBinding(FrameLayout frameLayout, TextView textView, ConstraintLayout constraintLayout, DetailContent1LayoutBinding detailContent1LayoutBinding, DetailContent2LayoutBinding detailContent2LayoutBinding, GifImageView gifImageView, Guideline guideline, Guideline guideline2, Guideline guideline3, Guideline guideline4, Guideline guideline5, Guideline guideline6, ImageView imageView, ImageView imageView2, ConstraintLayout constraintLayout2, TextView textView2, Toolbar toolbar) {
        this.rootView = frameLayout;
        this.barText = textView;
        this.constraintLayout4 = constraintLayout;
        this.detailContent1Layout = detailContent1LayoutBinding;
        this.detailContent2Layout = detailContent2LayoutBinding;
        this.gifView = gifImageView;
        this.guideline25 = guideline;
        this.guideline26 = guideline2;
        this.guideline36 = guideline3;
        this.guideline38 = guideline4;
        this.guideline43 = guideline5;
        this.guideline44 = guideline6;
        this.imageView3 = imageView;
        this.imageView6 = imageView2;
        this.linearLayout = constraintLayout2;
        this.time = textView2;
        this.toolbar1 = toolbar;
    }

    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static FragmentDetailBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static FragmentDetailBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_detail, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentDetailBinding bind(View view) {
        View view2 = view;
        int i = R.id.bar_text;
        TextView textView = (TextView) view2.findViewById(R.id.bar_text);
        if (textView != null) {
            i = R.id.constraintLayout4;
            ConstraintLayout constraintLayout = (ConstraintLayout) view2.findViewById(R.id.constraintLayout4);
            if (constraintLayout != null) {
                i = R.id.detail_content1_layout;
                View findViewById = view2.findViewById(R.id.detail_content1_layout);
                if (findViewById != null) {
                    DetailContent1LayoutBinding bind = DetailContent1LayoutBinding.bind(findViewById);
                    i = R.id.detail_content2_layout;
                    View findViewById2 = view2.findViewById(R.id.detail_content2_layout);
                    if (findViewById2 != null) {
                        DetailContent2LayoutBinding bind2 = DetailContent2LayoutBinding.bind(findViewById2);
                        i = R.id.gifView;
                        GifImageView gifImageView = (GifImageView) view2.findViewById(R.id.gifView);
                        if (gifImageView != null) {
                            i = R.id.guideline25;
                            Guideline guideline = (Guideline) view2.findViewById(R.id.guideline25);
                            if (guideline != null) {
                                i = R.id.guideline26;
                                Guideline guideline2 = (Guideline) view2.findViewById(R.id.guideline26);
                                if (guideline2 != null) {
                                    i = R.id.guideline36;
                                    Guideline guideline3 = (Guideline) view2.findViewById(R.id.guideline36);
                                    if (guideline3 != null) {
                                        i = R.id.guideline38;
                                        Guideline guideline4 = (Guideline) view2.findViewById(R.id.guideline38);
                                        if (guideline4 != null) {
                                            i = R.id.guideline43;
                                            Guideline guideline5 = (Guideline) view2.findViewById(R.id.guideline43);
                                            if (guideline5 != null) {
                                                i = R.id.guideline44;
                                                Guideline guideline6 = (Guideline) view2.findViewById(R.id.guideline44);
                                                if (guideline6 != null) {
                                                    i = R.id.imageView3;
                                                    ImageView imageView = (ImageView) view2.findViewById(R.id.imageView3);
                                                    if (imageView != null) {
                                                        i = R.id.imageView6;
                                                        ImageView imageView2 = (ImageView) view2.findViewById(R.id.imageView6);
                                                        if (imageView2 != null) {
                                                            i = R.id.linearLayout;
                                                            ConstraintLayout constraintLayout2 = (ConstraintLayout) view2.findViewById(R.id.linearLayout);
                                                            if (constraintLayout2 != null) {
                                                                i = 2131231126;
                                                                TextView textView2 = (TextView) view2.findViewById(2131231126);
                                                                if (textView2 != null) {
                                                                    i = R.id.toolbar1;
                                                                    Toolbar toolbar = (Toolbar) view2.findViewById(R.id.toolbar1);
                                                                    if (toolbar != null) {
                                                                        return new FragmentDetailBinding((FrameLayout) view2, textView, constraintLayout, bind, bind2, gifImageView, guideline, guideline2, guideline3, guideline4, guideline5, guideline6, imageView, imageView2, constraintLayout2, textView2, toolbar);
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
