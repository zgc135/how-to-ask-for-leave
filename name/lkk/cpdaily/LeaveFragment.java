package name.lkk.cpdaily;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import name.lkk.cpdaily.databinding.FragmentLeaveBinding;

public class LeaveFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentLeaveBinding binding;
    private String mParam1;
    private String mParam2;
    MainViewModel mainViewModel;

    public static LeaveFragment newInstance(String str, String str2) {
        LeaveFragment leaveFragment = new LeaveFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        leaveFragment.setArguments(bundle);
        return leaveFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.mainViewModel = (MainViewModel) new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) new SavedStateViewModelFactory(getActivity().getApplication(), this)).get(MainViewModel.class);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentLeaveBinding inflate = FragmentLeaveBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.binding.imageView7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate((int) R.id.action_leaveFragment2_to_detailFragment, ConApp.bundle);
            }
        });
        String string = ConApp.bundle.getString("text2");
        String string2 = ConApp.bundle.getString("text3");
        int parseInt = Integer.parseInt(string.substring(6, 8));
        int parseInt2 = Integer.parseInt(string2.substring(6, 8));
        int parseInt3 = Integer.parseInt(string.substring(9, 11));
        int parseInt4 = Integer.parseInt(string2.substring(9, 11));
        this.binding.textView9.setText(string + " ~ " + string2 + "（共" + (parseInt2 - parseInt) + "小时" + (parseInt4 - parseInt3) + "分钟）");
        this.binding.textView11.setText(string.substring(0, 6));
    }
}
