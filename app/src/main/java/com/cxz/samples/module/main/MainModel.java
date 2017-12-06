package com.cxz.samples.module.main;

import com.cxz.samples.bean.WeatherInfo;
import com.cxz.samples.di.scope.ActivityScope;
import com.cxz.samples.http.HostType;
import com.cxz.samples.http.RetrofitManager;
import com.cxz.samples.base.mvp.BaseModel;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

    @Inject
    public MainModel() {
    }

    @Override
    public Flowable<WeatherInfo> loadWeatherData(String cityId) {
        return RetrofitManager.getInstance(HostType.WEATHER_INFO).getWeatherInfoObservable(cityId);
    }
}
