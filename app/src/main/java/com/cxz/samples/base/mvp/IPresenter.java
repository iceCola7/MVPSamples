package com.cxz.samples.base.mvp;

/**
 * Created by chenxz on 2017/11/30.
 */

public interface IPresenter {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中会默认调用
     */
    void onDestroy();

}
