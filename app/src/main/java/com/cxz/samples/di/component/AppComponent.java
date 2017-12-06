package com.cxz.samples.di.component;


import com.cxz.samples.app.App;
import com.cxz.samples.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/2.
 */

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App getContext();

}
