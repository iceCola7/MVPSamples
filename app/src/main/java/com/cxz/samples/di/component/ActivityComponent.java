package com.cxz.samples.di.component;


import com.cxz.samples.di.module.ActivityModule;
import com.cxz.samples.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

//    Activity getActivity();

//    void inject(MainActivity mainActivity);

}
