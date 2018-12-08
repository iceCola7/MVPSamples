package com.cxz.sample.mvp.presenter;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.cxz.baselibs.http.function.RetryWithDelay;
import com.cxz.baselibs.mvp.BasePresenter;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.SampleModel;
import com.cxz.sample.mvp.model.bean.WeatherInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public class SamplePresenter extends BasePresenter<SampleContract.Model, SampleContract.View> implements SampleContract.Presenter {
    @Override
    protected SampleContract.Model createModel() {
        return new SampleModel();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        getWeatherInfo(getView().getCityId());
    }

    @SuppressLint("CheckResult")
    @Override
    public void getWeatherInfo(String cityId) {
        getModel().getWeatherInfo(cityId, true)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        getView().showLoading();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getView().hideLoading();
                    }
                })
                .compose(getView().<WeatherInfo>bindToLife())
                .retryWhen(new RetryWithDelay())
                .subscribe(new Consumer<WeatherInfo>() {
                    @Override
                    public void accept(WeatherInfo weatherInfo) throws Exception {
                        getView().showWeatherInfo(weatherInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }
}
