package com.cxz.samples.base.mvp;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by chenxz on 2017/11/30.
 */

public interface IView {

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     *
     * @param message
     */
    void showMessage(String message);

    /**
     * 绑定生命周期
     */
    <T> LifecycleTransformer<T> bindToLife();

}
