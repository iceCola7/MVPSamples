package com.cxz.baselibs.rx;

import com.cxz.baselibs.http.exception.ExceptionHandle;
import com.cxz.baselibs.mvp.IView;

import io.reactivex.observers.ResourceObserver;

/**
 * @author chenxz
 * @date 2018/9/1
 * @desc
 */
public abstract class BaseObserver<T> extends ResourceObserver<T> {

    private IView mView;
    private String mErrorMsg = "";

    public BaseObserver(IView view) {
        this.mView = view;
    }

    public BaseObserver(IView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            throw new RuntimeException("mView can not be null");
        }
        if (mErrorMsg.isEmpty()) {
            mErrorMsg = ExceptionHandle.handleException(e);
            mView.showErrorMsg(mErrorMsg);
        } else {
            mView.showErrorMsg(mErrorMsg);
        }
    }

    @Override
    public void onComplete() {

    }
}
