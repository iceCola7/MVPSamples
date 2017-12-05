package com.cxz.samples.base.mvp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by chenxz on 2017/11/30.
 */

public class BasePresenter<M extends IModel, V extends IView> implements IPresenter, LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;
    protected M mModel;
    protected V mView;

    public BasePresenter(M model, V view) {
        this.mModel = model;
        this.mView = view;
        onStart();
    }

    public BasePresenter(V view) {
        this.mView = view;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart: " + useEventBus());
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mView != null && mView instanceof LifecycleOwner) {
            ((LifecycleOwner) mView).getLifecycle().addObserver(this);
            if (mModel != null && mModel instanceof LifecycleObserver) {
                ((LifecycleOwner) mView).getLifecycle().addObserver((LifecycleObserver) mModel);
            }
        }
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " + useEventBus());
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        unDispose();// 解除订阅
        if (mModel != null)
            mModel.onDestroy();
        this.mModel = null;
        this.mView = null;
        this.mCompositeDisposable = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        Log.e(TAG, "onDestroy: LifecycleOwner");
        owner.getLifecycle().removeObserver(this);
    }

    /**
     * 如果要使用 Eventbus 请将此方法返回 true
     *
     * @return
     */
    public boolean useEventBus() {
        return true;
    }

    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有的 Disposable 放入集中处理
    }

    /**
     * 停止正在进行的任务
     */
    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证Activity结束时取消
        }
    }

}
