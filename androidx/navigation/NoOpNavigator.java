package androidx.navigation;

import android.os.Bundle;
import androidx.navigation.Navigator;

@Navigator.Name("NoOp")
public class NoOpNavigator extends Navigator<NavDestination> {
    public NavDestination navigate(NavDestination navDestination, Bundle bundle, NavOptions navOptions, Navigator.Extras extras) {
        return navDestination;
    }

    public boolean popBackStack() {
        return true;
    }

    public NavDestination createDestination() {
        return new NavDestination((Navigator<? extends NavDestination>) this);
    }
}
