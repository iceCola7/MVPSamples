package com.cxz.baselibs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cxz.baselibs.mvp.BasePresenter;
import com.cxz.baselibs.mvp.IView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author chenxz
 * @date 2018/8/20
 * @desc BaseMvpActivity
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        this.mPresenter = null;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }

}
