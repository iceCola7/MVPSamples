package com.cxz.samples.http.cache;

import com.cxz.samples.bean.WeatherInfo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by chenxz on 2017/12/6.
 */

public interface CacheProvider {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Flowable<Reply<WeatherInfo>> getWeatherWithCache(Flowable<WeatherInfo> o, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

}
