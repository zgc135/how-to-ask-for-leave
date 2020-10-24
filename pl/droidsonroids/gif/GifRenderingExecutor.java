package pl.droidsonroids.gif;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

final class GifRenderingExecutor extends ScheduledThreadPoolExecutor {

    private static final class InstanceHolder {
        /* access modifiers changed from: private */
        public static final GifRenderingExecutor INSTANCE = new GifRenderingExecutor();

        private InstanceHolder() {
        }
    }

    static GifRenderingExecutor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private GifRenderingExecutor() {
        super(1, new ThreadPoolExecutor.DiscardPolicy());
    }
}
