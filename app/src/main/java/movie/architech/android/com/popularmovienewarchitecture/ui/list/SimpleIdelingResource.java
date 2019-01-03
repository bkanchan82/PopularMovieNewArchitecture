package movie.architech.android.com.popularmovienewarchitecture.ui.list;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdelingResource implements IdlingResource {

    @Nullable
    private volatile ResourceCallback mCallback;

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }


    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdelState(boolean isIdelNow){
        mIsIdleNow.set(isIdelNow);
        if(isIdelNow && mCallback!= null){
            mCallback.onTransitionToIdle();
        }
    }
}
