package com.cxz.samples.di.module;

import com.cxz.samples.di.scope.FragmentScope;
import com.cxz.samples.module.other.OtherContract;
import com.cxz.samples.module.other.OtherModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by chenxz on 2017/12/3.
 */
@Module
public class OtherFragmentModule {

    private OtherContract.View view;

    public OtherFragmentModule(OtherContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    OtherContract.View provideOtherView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    OtherContract.Model provideOtherModel(OtherModel model) {
        return model;
    }

}
