package androidx.navigation;

import android.content.Context;
import androidx.activity.OnBackPressedDispatcher;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;

public class NavHostController extends NavController {
    public NavHostController(Context context) {
        super(context);
    }

    public final void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
    }

    public final void setOnBackPressedDispatcher(OnBackPressedDispatcher onBackPressedDispatcher) {
        super.setOnBackPressedDispatcher(onBackPressedDispatcher);
    }

    public final void enableOnBackPressed(boolean z) {
        super.enableOnBackPressed(z);
    }

    public final void setViewModelStore(ViewModelStore viewModelStore) {
        super.setViewModelStore(viewModelStore);
    }
}
