package name.lkk.cpdaily;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import name.lkk.cpdaily.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {
    private MainFragmentBinding binding;
    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        MainFragmentBinding inflate = MainFragmentBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        return inflate.getRoot();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mViewModel = (MainViewModel) ViewModelProviders.of((Fragment) this).get(MainViewModel.class);
        this.binding.button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Navigation.findNavController(view).navigate((int) R.id.action_mainFragment_to_addFragment);
            }
        });
    }
}
