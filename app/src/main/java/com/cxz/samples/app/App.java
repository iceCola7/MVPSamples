package com.cxz.samples.app;

import android.app.Application;

import com.cxz.samples.di.component.AppComponent;
import com.cxz.samples.di.component.DaggerAppComponent;
import com.cxz.samples.di.module.AppModule;

/**
 * Created by chenxz on 2017/3/30.
 */
public class App extends Application {

    private static App instance;
    public static AppComponent appComponent = null;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .build();
        }
        return appComponent;
    }
}
