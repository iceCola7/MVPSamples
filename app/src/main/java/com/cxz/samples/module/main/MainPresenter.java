package com.cxz.samples.module.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.cxz.samples.bean.WeatherInfo;
import com.cxz.samples.event.MessageEvent;
import com.cxz.samples.base.mvp.BasePresenter;
import com.cxz.samples.util.RxUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by chenxz on 2017/12/2.
 */

public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> implements MainContract.Presenter {

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View view) {
        super(model, view);
        Log.e(TAG, "MainPresenter: 构造。。。。");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.e(TAG, "onCreate: MainPresenter");
    }

    @Override
    public void loadWeatherData(String cityId) {
        addDispose(mModel.loadWeatherData(cityId)
                .subscribe(new Consumer<WeatherInfo>() {
                    @Override
                    public void accept(WeatherInfo weatherInfo) throws Exception {
                        mView.updateWeather(weatherInfo);
                    }
                }));
//        addDispose(Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e(TAG, "accept: " + aLong);
//                    }
//                }));

        Flowable.interval(1, TimeUnit.SECONDS)
                .compose(RxUtil.<Long>rxSchedulerTransformer())
                .compose(mView.<Long>bindToLife())
                .filter(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong % 2 == 0;
                    }
                })
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        return aLong + "::ss";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "accept: " + s);
                    }
                });

    }

    public void sendMessage() {
        Log.e(TAG, "sendMessage: ");
        EventBus.getDefault().post(new MessageEvent("message"));
    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onShowMessage(MessageEvent messageEvent) {
        Log.e(TAG, "onShowMessage: " + messageEvent.getMessage());
        mView.setMessage(messageEvent);
    }
}
