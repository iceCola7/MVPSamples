package com.cxz.sample.http.service;

import com.cxz.sample.mvp.model.bean.WeatherInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public interface RetrofitService {

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfo(@Path("cityId") String cityId);

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfoWitchCache(@Path("cityId") String cityId);

}
