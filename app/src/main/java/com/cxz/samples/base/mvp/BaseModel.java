package com.cxz.samples.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * Created by chenxz on 2017/11/30.
 */

public class BaseModel implements IModel, LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onDestroy() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        Log.e(TAG, "onDestroy: LifecycleOwner");
        owner.getLifecycle().removeObserver(this);
    }

}
