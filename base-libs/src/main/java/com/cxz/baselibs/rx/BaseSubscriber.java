package com.cxz.baselibs.rx;

import com.cxz.baselibs.http.exception.ExceptionHandle;
import com.cxz.baselibs.mvp.IView;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @author chenxz
 * @date 2018/9/1
 * @desc
 */
public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {

    private IView mView;
    private String mErrorMsg = "";

    public BaseSubscriber(IView view) {
        this.mView = view;
    }

    public BaseSubscriber(IView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {
        if (mView == null) {
            throw new RuntimeException("mView can not be null");
        }
        if (mErrorMsg.isEmpty()) {
            mErrorMsg = ExceptionHandle.handleException(t);
            mView.showErrorMsg(mErrorMsg);
        } else {
            mView.showErrorMsg(mErrorMsg);
        }
    }

    @Override
    public void onComplete() {

    }
}
