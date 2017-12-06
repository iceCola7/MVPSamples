package com.cxz.samples.di.component;

import com.cxz.samples.di.module.OtherFragmentModule;
import com.cxz.samples.di.scope.FragmentScope;
import com.cxz.samples.module.other.OtherFragment;

import dagger.Component;

/**
 * Created by chenxz on 2017/12/3.
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = OtherFragmentModule.class)
public interface OtherFragmentComponent {
    void inject(OtherFragment fragment);
}
