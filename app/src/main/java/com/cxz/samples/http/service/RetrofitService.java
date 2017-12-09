package com.cxz.samples.http.service;


import com.cxz.samples.bean.WeatherInfo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenxz on 2017/11/30.
 */
public interface RetrofitService {

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfo(@Path("cityId") String cityId);

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfoWitchCache(@Path("cityId") String cityId);

}
