package com.cxz.baselibs.mvp;

/**
 * Created by chenxz on 2017/11/30.
 */

public interface IPresenter<V extends IView> {

    /**
     * 绑定 View
     *
     * @param mView
     */
    void attachView(V mView);

    /**
     * 解绑 View
     */
    void detachView();

}
