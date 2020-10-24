package name.lkk.cpdaily;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarUtil {
    public static final int TYPE_FLYME = 1;
    public static final int TYPE_M = 3;
    public static final int TYPE_MIUI = 0;

    @Retention(RetentionPolicy.SOURCE)
    @interface ViewType {
    }

    public static void setStatusBarColor(Activity activity, int i) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().setStatusBarColor(i);
        } else if (Build.VERSION.SDK_INT >= 19) {
            setTranslucentStatus(activity);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setStatusBarTintColor(i);
        }
    }

    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(1280);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(0);
        } else if (Build.VERSION.SDK_INT >= 19) {
            Window window2 = activity.getWindow();
            WindowManager.LayoutParams attributes = window2.getAttributes();
            attributes.flags = 67108864 | attributes.flags;
            window2.setAttributes(attributes);
        }
    }

    public static void setRootViewFitsSystemWindows(Activity activity, boolean z) {
        ViewGroup viewGroup;
        if (Build.VERSION.SDK_INT >= 19) {
            ViewGroup viewGroup2 = (ViewGroup) activity.findViewById(16908290);
            if (viewGroup2.getChildCount() > 0 && (viewGroup = (ViewGroup) viewGroup2.getChildAt(0)) != null) {
                viewGroup.setFitsSystemWindows(z);
            }
        }
    }

    public static boolean setStatusBarDarkTheme(Activity activity, boolean z) {
        if (Build.VERSION.SDK_INT < 19 || Build.VERSION.SDK_INT < 23) {
            return false;
        }
        setStatusBarFontIconDark(activity, 3, z);
        return true;
    }

    public static boolean setStatusBarFontIconDark(Activity activity, int i, boolean z) {
        if (i == 0) {
            return setMiuiUI(activity, z);
        }
        if (i != 1) {
            return setCommonUI(activity, z);
        }
        return setFlymeUI(activity, z);
    }

    public static boolean setCommonUI(Activity activity, boolean z) {
        View decorView;
        if (Build.VERSION.SDK_INT < 23 || (decorView = activity.getWindow().getDecorView()) == null) {
            return false;
        }
        int systemUiVisibility = decorView.getSystemUiVisibility();
        int i = z ? systemUiVisibility | 8192 : systemUiVisibility & -8193;
        if (decorView.getSystemUiVisibility() == i) {
            return true;
        }
        decorView.setSystemUiVisibility(i);
        return true;
    }

    public static boolean setFlymeUI(Activity activity, boolean z) {
        try {
            Window window = activity.getWindow();
            WindowManager.LayoutParams attributes = window.getAttributes();
            Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            declaredField.setAccessible(true);
            declaredField2.setAccessible(true);
            int i = declaredField.getInt((Object) null);
            int i2 = declaredField2.getInt(attributes);
            declaredField2.setInt(attributes, z ? i2 | i : (i ^ -1) & i2);
            window.setAttributes(attributes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setMiuiUI(Activity activity, boolean z) {
        try {
            Window window = activity.getWindow();
            Class<?> cls = activity.getWindow().getClass();
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Method declaredMethod = cls.getDeclaredMethod("setExtraFlags", new Class[]{Integer.TYPE, Integer.TYPE});
            declaredMethod.setAccessible(true);
            if (z) {
                declaredMethod.invoke(window, new Object[]{Integer.valueOf(i), Integer.valueOf(i)});
            } else {
                declaredMethod.invoke(window, new Object[]{0, Integer.valueOf(i)});
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }
}
