package androidx.navigation.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.app.NotificationCompat;
import androidx.customview.widget.Openable;
import androidx.navigation.FloatingWindow;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class AbstractAppBarOnDestinationChangedListener implements NavController.OnDestinationChangedListener {
    private ValueAnimator mAnimator;
    private DrawerArrowDrawable mArrowDrawable;
    private final Context mContext;
    private final WeakReference<Openable> mOpenableLayoutWeakReference;
    private final Set<Integer> mTopLevelDestinations;

    /* access modifiers changed from: protected */
    public abstract void setNavigationIcon(Drawable drawable, int i);

    /* access modifiers changed from: protected */
    public abstract void setTitle(CharSequence charSequence);

    AbstractAppBarOnDestinationChangedListener(Context context, AppBarConfiguration appBarConfiguration) {
        this.mContext = context;
        this.mTopLevelDestinations = appBarConfiguration.getTopLevelDestinations();
        Openable openableLayout = appBarConfiguration.getOpenableLayout();
        if (openableLayout != null) {
            this.mOpenableLayoutWeakReference = new WeakReference<>(openableLayout);
        } else {
            this.mOpenableLayoutWeakReference = null;
        }
    }

    public void onDestinationChanged(NavController navController, NavDestination navDestination, Bundle bundle) {
        if (!(navDestination instanceof FloatingWindow)) {
            WeakReference<Openable> weakReference = this.mOpenableLayoutWeakReference;
            Openable openable = weakReference != null ? (Openable) weakReference.get() : null;
            if (this.mOpenableLayoutWeakReference == null || openable != null) {
                CharSequence label = navDestination.getLabel();
                boolean z = true;
                if (label != null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    Matcher matcher = Pattern.compile("\\{(.+?)\\}").matcher(label);
                    while (matcher.find()) {
                        String group = matcher.group(1);
                        if (bundle == null || !bundle.containsKey(group)) {
                            throw new IllegalArgumentException("Could not find " + group + " in " + bundle + " to fill label " + label);
                        }
                        matcher.appendReplacement(stringBuffer, "");
                        stringBuffer.append(bundle.get(group).toString());
                    }
                    matcher.appendTail(stringBuffer);
                    setTitle(stringBuffer);
                }
                boolean matchDestinations = NavigationUI.matchDestinations(navDestination, this.mTopLevelDestinations);
                if (openable != null || !matchDestinations) {
                    if (openable == null || !matchDestinations) {
                        z = false;
                    }
                    setActionBarUpIndicator(z);
                    return;
                }
                setNavigationIcon((Drawable) null, 0);
                return;
            }
            navController.removeOnDestinationChangedListener(this);
        }
    }

    private void setActionBarUpIndicator(boolean z) {
        boolean z2;
        int i;
        if (this.mArrowDrawable == null) {
            this.mArrowDrawable = new DrawerArrowDrawable(this.mContext);
            z2 = false;
        } else {
            z2 = true;
        }
        DrawerArrowDrawable drawerArrowDrawable = this.mArrowDrawable;
        if (z) {
            i = R.string.nav_app_bar_open_drawer_description;
        } else {
            i = R.string.nav_app_bar_navigate_up_description;
        }
        setNavigationIcon(drawerArrowDrawable, i);
        float f = z ? 0.0f : 1.0f;
        if (z2) {
            float progress = this.mArrowDrawable.getProgress();
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mArrowDrawable, NotificationCompat.CATEGORY_PROGRESS, new float[]{progress, f});
            this.mAnimator = ofFloat;
            ofFloat.start();
            return;
        }
        this.mArrowDrawable.setProgress(f);
    }
}
