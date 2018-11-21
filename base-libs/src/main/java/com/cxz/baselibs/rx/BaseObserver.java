package com.cxz.baselibs.rx;

import com.cxz.baselibs.app.BaseApp;
import com.cxz.baselibs.bean.BaseBean;
import com.cxz.baselibs.http.exception.ErrorStatus;
import com.cxz.baselibs.http.exception.ExceptionHandle;
import com.cxz.baselibs.mvp.IView;
import com.cxz.baselibs.utils.NetworkUtil;

import io.reactivex.observers.ResourceObserver;

/**
 * @author chenxz
 * @date 2018/9/1
 * @desc BaseObserver
 */
public abstract class BaseObserver<T extends BaseBean> extends ResourceObserver<T> {

    private IView mView;
    private String mErrorMsg = "";

    abstract void onSuccess(T t);

    public BaseObserver(IView view) {
        this.mView = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mView.showLoading();
        if (!NetworkUtil.isConnected(BaseApp.getInstance())) {
            mView.showDefaultMsg("当前网络不可用，请检查网络设置");
            onComplete();
        }
    }

    @Override
    public void onNext(T t) {
        if (t.getErrorCode() == ErrorStatus.SUCCESS) {
            onSuccess(t);
        } else if (t.getErrorCode() == ErrorStatus.TOKEN_INVAILD) {

        } else {
            if (!t.getErrorMsg().isEmpty()) {
                mView.showDefaultMsg(t.getErrorMsg());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            throw new RuntimeException("mView can not be null");
        }
        if (mErrorMsg.isEmpty()) {
            mErrorMsg = ExceptionHandle.handleException(e);
            mView.showDefaultMsg(mErrorMsg);
        } else {
            mView.showDefaultMsg(mErrorMsg);
        }
    }

    @Override
    public void onComplete() {
        mView.hideLoading();
    }
}
