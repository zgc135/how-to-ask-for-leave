package name.lkk.cpdaily;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import java.util.Calendar;
import name.lkk.cpdaily.databinding.FragmentAddBinding;

public class addFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static Bundle bundle = new Bundle();
    FragmentAddBinding binding;
    private String mParam1;
    private String mParam2;
    MainViewModel mainViewModel;

    public static addFragment newInstance(String str, String str2) {
        addFragment addfragment = new addFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString(ARG_PARAM1, str);
        bundle2.putString(ARG_PARAM2, str2);
        addfragment.setArguments(bundle2);
        return addfragment;
    }

    public void onCreate(Bundle bundle2) {
        super.onCreate(bundle2);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.mainViewModel = (MainViewModel) new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) new SavedStateViewModelFactory(getActivity().getApplication(), this)).get(MainViewModel.class);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle2) {
        FragmentAddBinding inflate = FragmentAddBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    public void onActivityCreated(Bundle bundle2) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        super.onActivityCreated(bundle2);
        Calendar instance = Calendar.getInstance();
        int i = instance.get(2) + 1;
        int i2 = instance.get(5);
        int i3 = instance.get(11);
        int i4 = instance.get(12);
        if (i2 < 10) {
            str = "0" + Integer.toString(i2);
        } else {
            str = Integer.toString(i2);
        }
        if (i < 10) {
            str2 = "0" + Integer.toString(i);
        } else {
            str2 = Integer.toString(i);
        }
        if (i3 < 10) {
            str3 = "0" + Integer.toString(i3);
        } else {
            str3 = Integer.toString(i3);
        }
        if (i4 < 10) {
            str4 = "0" + Integer.toString(i4);
        } else {
            str4 = Integer.toString(i4);
        }
        String str7 = str2 + "-" + str + " " + str3 + ":" + str4;
        int i5 = i3 - 1;
        if (i5 < 0) {
            str5 = Integer.toString(Math.abs(i3 + 23));
        } else if (i5 < 10) {
            str5 = "0" + Integer.toString(i5);
        } else {
            str5 = Integer.toString(i5);
        }
        String str8 = str2 + "-" + str + " " + str5 + ":04";
        String str9 = str2 + "-" + str + " " + str5 + ":26";
        String str10 = str2 + "-" + str + " " + str5 + ":49";
        int i6 = i3 + 2;
        if (i6 < 10) {
            str6 = "0" + Integer.toString(i6);
        } else if (i6 > 24) {
            str6 = "0" + Integer.toString(Math.abs(i3 - 24));
        } else {
            str6 = Integer.toString(i6);
        }
        this.binding.editTextTextPersonName3.setText(String.valueOf(this.mainViewModel.getData()));
        this.binding.editTextTextPersonName4.setText(String.valueOf(this.mainViewModel.getNum()));
        this.binding.editTextTextPersonName5.setText(String.valueOf(this.mainViewModel.getReason()));
        this.binding.editTextTextPersonName6.setText(String.valueOf(this.mainViewModel.getLocal()));
        this.binding.editTextTextPersonName7.setText(String.valueOf(this.mainViewModel.getFdy()));
        this.binding.editTextTextPersonName.setText(str7);
        this.binding.editTextTextPersonName2.setText(str2 + "-" + str + " " + str6 + ":" + str4);
        this.binding.editTextDate.setText(str8);
        this.binding.editTextDate2.setText(str9);
        this.binding.editTextDate3.setText(str10);
        this.binding.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                addFragment.this.mainViewModel.save(addFragment.this.binding);
                addFragment.bundle.putString("name", String.valueOf(addFragment.this.binding.editTextTextPersonName3.getText()).trim());
                addFragment.bundle.putString("text2", String.valueOf(addFragment.this.binding.editTextTextPersonName.getText()));
                addFragment.bundle.putString("text3", String.valueOf(addFragment.this.binding.editTextTextPersonName2.getText()));
                addFragment.bundle.putString("text4", String.valueOf(addFragment.this.binding.editTextTextPersonName4.getText()).trim());
                addFragment.bundle.putString("text5", String.valueOf(addFragment.this.binding.editTextTextPersonName5.getText()).trim());
                addFragment.bundle.putString("text10", String.valueOf(addFragment.this.binding.editTextTextPersonName6.getText()).trim());
                addFragment.bundle.putString("text6", String.valueOf(addFragment.this.binding.editTextTextPersonName8.getText()));
                addFragment.bundle.putString("text7", String.valueOf(addFragment.this.binding.editTextDate.getText()));
                addFragment.bundle.putString("text8", String.valueOf(addFragment.this.binding.editTextDate2.getText()));
                addFragment.bundle.putString("text9", String.valueOf(addFragment.this.binding.editTextDate3.getText()));
                addFragment.bundle.putString("text11", String.valueOf(addFragment.this.binding.editTextTextPersonName7.getText()));
                NavController findNavController = Navigation.findNavController(view);
                ConApp.bundle = addFragment.bundle;
                findNavController.navigate((int) R.id.action_addFragment_to_detailFragment, addFragment.bundle);
            }
        });
    }
}
