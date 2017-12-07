package com.cxz.samples.module.main;

import com.cxz.samples.base.mvp.BaseModel;
import com.cxz.samples.bean.WeatherInfo;
import com.cxz.samples.di.scope.ActivityScope;
import com.cxz.samples.http.HostType;
import com.cxz.samples.http.RetrofitHelper;
import com.cxz.samples.http.RetrofitManager;
import com.cxz.samples.http.cache.CacheProvider;
import com.cxz.samples.http.service.RetrofitService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * Created by chenxz on 2017/12/2.
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {

    private RetrofitHelper mRetrofitHelper;

    @Inject
    public MainModel() {
        mRetrofitHelper = RetrofitHelper.getInstance();
    }

    @Override
    public Flowable<WeatherInfo> loadWeatherData(String cityId) {
        return RetrofitManager.getInstance(HostType.WEATHER_INFO).getWeatherInfoObservable(cityId);
    }

    @Override
    public Observable<WeatherInfo> loadWeatherData(final String cityId, final boolean isUpdate) {

        return Observable.just(mRetrofitHelper.obtainRetrofitService(RetrofitService.class).getWeatherInfoWitchCache(cityId))
                .flatMap(new Function<Observable<WeatherInfo>, ObservableSource<WeatherInfo>>() {
                    @Override
                    public ObservableSource<WeatherInfo> apply(Observable<WeatherInfo> weatherInfoObservable) throws Exception {
                        return mRetrofitHelper.obtainCacheService(CacheProvider.class)
                                .getWeatherWithCache(weatherInfoObservable, new DynamicKey(cityId), new EvictDynamicKey(isUpdate))
                                .map(new Function<Reply<WeatherInfo>, WeatherInfo>() {
                                    @Override
                                    public WeatherInfo apply(Reply<WeatherInfo> weatherInfoReply) throws Exception {
                                        return weatherInfoReply.getData();
                                    }
                                });
                    }
                });
    }
}
