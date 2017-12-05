package com.cxz.samples.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.cxz.samples.R;
import com.cxz.samples.base.mvp.IPresenter;
import com.cxz.samples.base.mvp.IView;
import com.cxz.samples.widget.EmptyLayout;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxz on 2017/11/30.
 */

public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements IView {

    protected final String TAG = this.getClass().getSimpleName();

    @Nullable
    @BindView(R.id.empty_layout)
    protected EmptyLayout mEmptyLayout;

    /**
     * 绑定 ButterKnife 时返回的 Unbinder ，用于 ButterKnife 解绑
     */
    private Unbinder mUnbinder;

    @Inject
    protected P mPresenter;

    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化 Dagger2
     */
    protected abstract void initInject();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResId = attachLayoutRes();
            if (layoutResId != 0) {
                setContentView(layoutResId);
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initInject();
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }
}
