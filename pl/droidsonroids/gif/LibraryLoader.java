package pl.droidsonroids.gif;

import android.content.Context;

public class LibraryLoader {
    static final String BASE_LIBRARY_NAME = "pl_droidsonroids_gif";
    static final String SURFACE_LIBRARY_NAME = "pl_droidsonroids_gif_surface";
    private static Context sAppContext;

    private LibraryLoader() {
    }

    public static void initialize(Context context) {
        sAppContext = context.getApplicationContext();
    }

    private static Context getContext() {
        if (sAppContext == null) {
            try {
                sAppContext = (Context) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke((Object) null, new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("LibraryLoader not initialized. Call LibraryLoader.initialize() before using library classes.", e);
            }
        }
        return sAppContext;
    }

    static void loadLibrary(Context context) {
        try {
            System.loadLibrary(BASE_LIBRARY_NAME);
        } catch (UnsatisfiedLinkError unused) {
            if (context == null) {
                context = getContext();
            }
            ReLinker.loadLibrary(context);
        }
    }
}
