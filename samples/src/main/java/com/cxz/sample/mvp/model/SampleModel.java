package com.cxz.sample.mvp.model;

import com.cxz.baselibs.mvp.BaseModel;
import com.cxz.sample.http.RetrofitHelper;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.bean.WeatherInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public class SampleModel extends BaseModel implements SampleContract.Model {

    @Override
    public Observable<WeatherInfo> getWeatherInfo(String cityId) {
        return RetrofitHelper.getRetrofitService().getWeatherInfo(cityId);
    }

    @Override
    public Observable<WeatherInfo> getWeatherInfo(final String cityId, final boolean isUpdate) {
        return Observable.just(RetrofitHelper.getRetrofitService().getWeatherInfoWitchCache(cityId))
                .flatMap(new Function<Observable<WeatherInfo>, ObservableSource<WeatherInfo>>() {
                    @Override
                    public ObservableSource<WeatherInfo> apply(Observable<WeatherInfo> weatherInfoObservable) throws Exception {
                        return RetrofitHelper.getCacheService()
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
