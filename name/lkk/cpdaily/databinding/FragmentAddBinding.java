package name.lkk.cpdaily.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import name.lkk.cpdaily.R;

public final class FragmentAddBinding implements ViewBinding {
    public final Button button;
    public final EditText editTextDate;
    public final EditText editTextDate2;
    public final EditText editTextDate3;
    public final EditText editTextTextPersonName;
    public final EditText editTextTextPersonName2;
    public final EditText editTextTextPersonName3;
    public final EditText editTextTextPersonName4;
    public final EditText editTextTextPersonName5;
    public final EditText editTextTextPersonName6;
    public final EditText editTextTextPersonName7;
    public final EditText editTextTextPersonName8;
    public final ConstraintLayout frameLayout2;
    public final Guideline guideline10;
    public final Guideline guideline11;
    public final Guideline guideline12;
    public final Guideline guideline2;
    public final Guideline guideline20;
    public final Guideline guideline27;
    public final Guideline guideline28;
    public final Guideline guideline3;
    public final Guideline guideline32;
    public final Guideline guideline42;
    public final Guideline guideline5;
    public final Guideline guideline6;
    public final Guideline guideline7;
    public final Guideline guideline8;
    public final Guideline guideline9;
    private final ConstraintLayout rootView;
    public final TextView textView;
    public final TextView textView14;
    public final TextView textView16;
    public final TextView textView17;
    public final TextView textView18;
    public final TextView textView19;
    public final TextView textView2;
    public final TextView textView3;
    public final TextView textView4;
    public final TextView textView5;
    public final TextView textView6;
    public final TextView textView7;
    public final TextView textView8;

    private FragmentAddBinding(ConstraintLayout constraintLayout, Button button2, EditText editText, EditText editText2, EditText editText3, EditText editText4, EditText editText5, EditText editText6, EditText editText7, EditText editText8, EditText editText9, EditText editText10, EditText editText11, ConstraintLayout constraintLayout2, Guideline guideline, Guideline guideline4, Guideline guideline13, Guideline guideline14, Guideline guideline15, Guideline guideline16, Guideline guideline17, Guideline guideline18, Guideline guideline19, Guideline guideline21, Guideline guideline22, Guideline guideline23, Guideline guideline24, Guideline guideline25, Guideline guideline26, TextView textView9, TextView textView10, TextView textView11, TextView textView12, TextView textView13, TextView textView15, TextView textView20, TextView textView21, TextView textView22, TextView textView23, TextView textView24, TextView textView25, TextView textView26) {
        this.rootView = constraintLayout;
        this.button = button2;
        this.editTextDate = editText;
        this.editTextDate2 = editText2;
        this.editTextDate3 = editText3;
        this.editTextTextPersonName = editText4;
        this.editTextTextPersonName2 = editText5;
        this.editTextTextPersonName3 = editText6;
        this.editTextTextPersonName4 = editText7;
        this.editTextTextPersonName5 = editText8;
        this.editTextTextPersonName6 = editText9;
        this.editTextTextPersonName7 = editText10;
        this.editTextTextPersonName8 = editText11;
        this.frameLayout2 = constraintLayout2;
        this.guideline10 = guideline;
        this.guideline11 = guideline4;
        this.guideline12 = guideline13;
        this.guideline2 = guideline14;
        this.guideline20 = guideline15;
        this.guideline27 = guideline16;
        this.guideline28 = guideline17;
        this.guideline3 = guideline18;
        this.guideline32 = guideline19;
        this.guideline42 = guideline21;
        this.guideline5 = guideline22;
        this.guideline6 = guideline23;
        this.guideline7 = guideline24;
        this.guideline8 = guideline25;
        this.guideline9 = guideline26;
        this.textView = textView9;
        this.textView14 = textView10;
        this.textView16 = textView11;
        this.textView17 = textView12;
        this.textView18 = textView13;
        this.textView19 = textView15;
        this.textView2 = textView20;
        this.textView3 = textView21;
        this.textView4 = textView22;
        this.textView5 = textView23;
        this.textView6 = textView24;
        this.textView7 = textView25;
        this.textView8 = textView26;
    }

    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static FragmentAddBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, (ViewGroup) null, false);
    }

    public static FragmentAddBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.fragment_add, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static FragmentAddBinding bind(View view) {
        View view2 = view;
        int i = R.id.button;
        Button button2 = (Button) view2.findViewById(R.id.button);
        if (button2 != null) {
            i = R.id.editTextDate;
            EditText editText = (EditText) view2.findViewById(R.id.editTextDate);
            if (editText != null) {
                i = R.id.editTextDate2;
                EditText editText2 = (EditText) view2.findViewById(R.id.editTextDate2);
                if (editText2 != null) {
                    i = R.id.editTextDate3;
                    EditText editText3 = (EditText) view2.findViewById(R.id.editTextDate3);
                    if (editText3 != null) {
                        i = R.id.editTextTextPersonName;
                        EditText editText4 = (EditText) view2.findViewById(R.id.editTextTextPersonName);
                        if (editText4 != null) {
                            i = R.id.editTextTextPersonName2;
                            EditText editText5 = (EditText) view2.findViewById(R.id.editTextTextPersonName2);
                            if (editText5 != null) {
                                i = R.id.editTextTextPersonName3;
                                EditText editText6 = (EditText) view2.findViewById(R.id.editTextTextPersonName3);
                                if (editText6 != null) {
                                    i = R.id.editTextTextPersonName4;
                                    EditText editText7 = (EditText) view2.findViewById(R.id.editTextTextPersonName4);
                                    if (editText7 != null) {
                                        i = R.id.editTextTextPersonName5;
                                        EditText editText8 = (EditText) view2.findViewById(R.id.editTextTextPersonName5);
                                        if (editText8 != null) {
                                            i = R.id.editTextTextPersonName6;
                                            EditText editText9 = (EditText) view2.findViewById(R.id.editTextTextPersonName6);
                                            if (editText9 != null) {
                                                i = R.id.editTextTextPersonName7;
                                                EditText editText10 = (EditText) view2.findViewById(R.id.editTextTextPersonName7);
                                                if (editText10 != null) {
                                                    i = R.id.editTextTextPersonName8;
                                                    EditText editText11 = (EditText) view2.findViewById(R.id.editTextTextPersonName8);
                                                    if (editText11 != null) {
                                                        ConstraintLayout constraintLayout = (ConstraintLayout) view2;
                                                        i = R.id.guideline10;
                                                        Guideline guideline = (Guideline) view2.findViewById(R.id.guideline10);
                                                        if (guideline != null) {
                                                            i = R.id.guideline11;
                                                            Guideline guideline4 = (Guideline) view2.findViewById(R.id.guideline11);
                                                            if (guideline4 != null) {
                                                                i = R.id.guideline12;
                                                                Guideline guideline13 = (Guideline) view2.findViewById(R.id.guideline12);
                                                                if (guideline13 != null) {
                                                                    i = R.id.guideline2;
                                                                    Guideline guideline14 = (Guideline) view2.findViewById(R.id.guideline2);
                                                                    if (guideline14 != null) {
                                                                        i = R.id.guideline20;
                                                                        Guideline guideline15 = (Guideline) view2.findViewById(R.id.guideline20);
                                                                        if (guideline15 != null) {
                                                                            i = R.id.guideline27;
                                                                            Guideline guideline16 = (Guideline) view2.findViewById(R.id.guideline27);
                                                                            if (guideline16 != null) {
                                                                                i = R.id.guideline28;
                                                                                Guideline guideline17 = (Guideline) view2.findViewById(R.id.guideline28);
                                                                                if (guideline17 != null) {
                                                                                    i = R.id.guideline3;
                                                                                    Guideline guideline18 = (Guideline) view2.findViewById(R.id.guideline3);
                                                                                    if (guideline18 != null) {
                                                                                        i = R.id.guideline32;
                                                                                        Guideline guideline19 = (Guideline) view2.findViewById(R.id.guideline32);
                                                                                        if (guideline19 != null) {
                                                                                            i = R.id.guideline42;
                                                                                            Guideline guideline21 = (Guideline) view2.findViewById(R.id.guideline42);
                                                                                            if (guideline21 != null) {
                                                                                                i = R.id.guideline5;
                                                                                                Guideline guideline22 = (Guideline) view2.findViewById(R.id.guideline5);
                                                                                                if (guideline22 != null) {
                                                                                                    i = R.id.guideline6;
                                                                                                    Guideline guideline23 = (Guideline) view2.findViewById(R.id.guideline6);
                                                                                                    if (guideline23 != null) {
                                                                                                        i = R.id.guideline7;
                                                                                                        Guideline guideline24 = (Guideline) view2.findViewById(R.id.guideline7);
                                                                                                        if (guideline24 != null) {
                                                                                                            i = R.id.guideline8;
                                                                                                            Guideline guideline25 = (Guideline) view2.findViewById(R.id.guideline8);
                                                                                                            if (guideline25 != null) {
                                                                                                                i = R.id.guideline9;
                                                                                                                Guideline guideline26 = (Guideline) view2.findViewById(R.id.guideline9);
                                                                                                                if (guideline26 != null) {
                                                                                                                    i = R.id.textView;
                                                                                                                    TextView textView9 = (TextView) view2.findViewById(R.id.textView);
                                                                                                                    if (textView9 != null) {
                                                                                                                        i = R.id.textView14;
                                                                                                                        TextView textView10 = (TextView) view2.findViewById(R.id.textView14);
                                                                                                                        if (textView10 != null) {
                                                                                                                            i = R.id.textView16;
                                                                                                                            TextView textView11 = (TextView) view2.findViewById(R.id.textView16);
                                                                                                                            if (textView11 != null) {
                                                                                                                                i = R.id.textView17;
                                                                                                                                TextView textView12 = (TextView) view2.findViewById(R.id.textView17);
                                                                                                                                if (textView12 != null) {
                                                                                                                                    i = R.id.textView18;
                                                                                                                                    TextView textView13 = (TextView) view2.findViewById(R.id.textView18);
                                                                                                                                    if (textView13 != null) {
                                                                                                                                        i = R.id.textView19;
                                                                                                                                        TextView textView15 = (TextView) view2.findViewById(R.id.textView19);
                                                                                                                                        if (textView15 != null) {
                                                                                                                                            i = R.id.textView2;
                                                                                                                                            TextView textView20 = (TextView) view2.findViewById(R.id.textView2);
                                                                                                                                            if (textView20 != null) {
                                                                                                                                                i = R.id.textView3;
                                                                                                                                                TextView textView21 = (TextView) view2.findViewById(R.id.textView3);
                                                                                                                                                if (textView21 != null) {
                                                                                                                                                    i = R.id.textView4;
                                                                                                                                                    TextView textView22 = (TextView) view2.findViewById(R.id.textView4);
                                                                                                                                                    if (textView22 != null) {
                                                                                                                                                        i = R.id.textView5;
                                                                                                                                                        TextView textView23 = (TextView) view2.findViewById(R.id.textView5);
                                                                                                                                                        if (textView23 != null) {
                                                                                                                                                            i = R.id.textView6;
                                                                                                                                                            TextView textView24 = (TextView) view2.findViewById(R.id.textView6);
                                                                                                                                                            if (textView24 != null) {
                                                                                                                                                                i = R.id.textView7;
                                                                                                                                                                TextView textView25 = (TextView) view2.findViewById(R.id.textView7);
                                                                                                                                                                if (textView25 != null) {
                                                                                                                                                                    i = R.id.textView8;
                                                                                                                                                                    TextView textView26 = (TextView) view2.findViewById(R.id.textView8);
                                                                                                                                                                    if (textView26 != null) {
                                                                                                                                                                        return new FragmentAddBinding(constraintLayout, button2, editText, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11, constraintLayout, guideline, guideline4, guideline13, guideline14, guideline15, guideline16, guideline17, guideline18, guideline19, guideline21, guideline22, guideline23, guideline24, guideline25, guideline26, textView9, textView10, textView11, textView12, textView13, textView15, textView20, textView21, textView22, textView23, textView24, textView25, textView26);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
