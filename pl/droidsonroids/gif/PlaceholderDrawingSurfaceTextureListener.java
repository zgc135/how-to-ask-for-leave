package pl.droidsonroids.gif;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import pl.droidsonroids.gif.GifTextureView;

class PlaceholderDrawingSurfaceTextureListener implements TextureView.SurfaceTextureListener {
    private final GifTextureView.PlaceholderDrawListener mDrawer;

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    PlaceholderDrawingSurfaceTextureListener(GifTextureView.PlaceholderDrawListener placeholderDrawListener) {
        this.mDrawer = placeholderDrawListener;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        Surface surface = new Surface(surfaceTexture);
        Canvas lockCanvas = surface.lockCanvas((Rect) null);
        this.mDrawer.onDrawPlaceholder(lockCanvas);
        surface.unlockCanvasAndPost(lockCanvas);
        surface.release();
    }
}
