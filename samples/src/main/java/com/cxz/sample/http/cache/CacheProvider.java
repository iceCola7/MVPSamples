package com.cxz.sample.http.cache;

import com.cxz.sample.mvp.model.bean.WeatherInfo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public interface CacheProvider {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<WeatherInfo>> getWeatherWithCache(Observable<WeatherInfo> o, DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);

}
