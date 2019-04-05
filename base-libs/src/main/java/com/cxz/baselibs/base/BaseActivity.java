package com.cxz.baselibs.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenxz on 2017/11/30.
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 绑定 ButterKnife 时返回的 Unbinder ，用于 ButterKnife 解绑
     */
    private Unbinder mUnbinder;

    private RxPermissions rxPermissions;

    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 初始化View
     */
    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected boolean useEventBus() {
        return false;
    }

    /**
     * 获取权限处理类
     */
    protected RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        return rxPermissions;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResId = attachLayoutRes();
        if (layoutResId != 0) {
            setContentView(layoutResId);
            mUnbinder = ButterKnife.bind(this);
            if (useEventBus()) {
                EventBus.getDefault().register(this);
            }
            initView();
            initData();
            initListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

}
