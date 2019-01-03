package movie.architech.android.com.popularmovienewarchitecture.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private Executor mIoExecutor;
    private Executor mNetworkExecutor;
    private MainThreadeExecutor mMainThreadExecutor;

    private static AppExecutor sInstance;
    private static final Object LOCK = new Object();

    private AppExecutor(Executor ioExecutor,Executor networkExecutor,MainThreadeExecutor mainThreadExecutor){
        mIoExecutor = ioExecutor;
        mNetworkExecutor = networkExecutor;
        mMainThreadExecutor = mainThreadExecutor;
    }

    public static synchronized AppExecutor getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadeExecutor());
            }
        }
        return sInstance;
    }

    public Executor getIoExecutor() {
        return mIoExecutor;
    }

    public Executor getNetworkExecutor() {
        return mNetworkExecutor;
    }

    public Executor getMainThreadExecutor() {
        return mMainThreadExecutor;
    }

    private static class MainThreadeExecutor implements Executor{

        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            handler.post(runnable);
        }
    }

}
