package com.cxz.samples.http.service;


import com.cxz.samples.bean.WeatherInfo;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenxz on 2017/11/30.
 */
public interface RetrofitService {

    @GET("adat/sk/{cityId}.html")
    Flowable<WeatherInfo> getWeatherInfo(@Path("cityId") String cityId);

}
