package com.cxz.samples.di.module;

import com.cxz.samples.di.scope.ActivityScope;
import com.cxz.samples.module.main.MainContract;
import com.cxz.samples.module.main.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2017/12/2.
 */
@Module
public class MainActivityModule {

    private MainContract.View view;

    public MainActivityModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }

}
