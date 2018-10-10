package com.cxz.sample.app;

import com.cxz.baselibs.app.BaseApp;
import com.cxz.sample.BuildConfig;

import timber.log.Timber;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.LOG_DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }
}
