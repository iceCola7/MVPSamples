package com.cxz.samples.module.other;


import com.cxz.samples.di.scope.FragmentScope;
import com.cxz.samples.base.mvp.BaseModel;

import javax.inject.Inject;

/**
 * Created by chenxz on 2017/12/3.
 */
@FragmentScope
public class OtherModel extends BaseModel implements OtherContract.Model {

    @Inject
    public OtherModel() {
    }

}
