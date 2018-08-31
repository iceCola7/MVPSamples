package com.cxz.sample.mvp.presenter;

import android.annotation.SuppressLint;

import com.cxz.baselibs.http.exception.ExceptionHandle;
import com.cxz.baselibs.mvp.BasePresenter;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.SampleModel;
import com.cxz.sample.mvp.model.bean.WeatherInfo;

import org.reactivestreams.Subscription;

import io.reactivex.android.schedulers.AndroidSchedulers;
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

    @SuppressLint("CheckResult")
    @Override
    public void getWeatherInfo(String cityId) {
        getModel().getWeatherInfo(cityId, false)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
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
                .subscribe(new Consumer<WeatherInfo>() {
                    @Override
                    public void accept(WeatherInfo weatherInfo) throws Exception {
                        getView().showWeatherInfo(weatherInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getView().showErrorMsg(ExceptionHandle.handleException(throwable));
                    }
                });

    }
}
