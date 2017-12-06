package com.cxz.samples.di.component;

import com.cxz.samples.di.module.MainActivityModule;
import com.cxz.samples.di.scope.ActivityScope;
import com.cxz.samples.module.main.MainActivity;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
