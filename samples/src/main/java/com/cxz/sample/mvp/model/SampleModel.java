package com.cxz.sample.mvp.model;

import com.cxz.baselibs.bean.BaseBean;
import com.cxz.baselibs.mvp.BaseModel;
import com.cxz.sample.http.RetrofitHelper;
import com.cxz.sample.mvp.contract.SampleContract;
import com.cxz.sample.mvp.model.bean.BannerListBean;
import com.cxz.sample.mvp.model.bean.CollectListBean;
import com.cxz.sample.mvp.model.bean.WeatherInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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
        return RetrofitHelper.getWeatherService().getWeatherInfo(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<WeatherInfo> getWeatherInfo(final String cityId, final boolean isUpdate) {
        return Observable.just(RetrofitHelper.getWeatherService().getWeatherInfoWitchCache(cityId))
                .flatMap(new Function<Observable<WeatherInfo>, ObservableSource<WeatherInfo>>() {
                    @Override
                    public ObservableSource<WeatherInfo> apply(Observable<WeatherInfo> weatherInfoObservable) throws Exception {
                        return RetrofitHelper.getWeatherCacheService()
                                .getWeatherWithCache(weatherInfoObservable, new DynamicKey(cityId), new EvictDynamicKey(isUpdate))
                                .map(new Function<Reply<WeatherInfo>, WeatherInfo>() {
                                    @Override
                                    public WeatherInfo apply(Reply<WeatherInfo> weatherInfoReply) throws Exception {
                                        return weatherInfoReply.getData();
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean> login(String username, String password) {
        return RetrofitHelper.getRetrofitService().login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BannerListBean> getBannerList(final boolean isUpdate) {
        return Observable.just(RetrofitHelper.getRetrofitService().getBannerList())
                .flatMap(new Function<Observable<BannerListBean>, ObservableSource<BannerListBean>>() {
                    @Override
                    public ObservableSource<BannerListBean> apply(Observable<BannerListBean> bannerListBeanObservable) throws Exception {
                        return RetrofitHelper.getCacheService()
                                .getBannerWithCache(bannerListBeanObservable, new DynamicKey("banner_list"), new EvictDynamicKey(isUpdate))
                                .map(new Function<Reply<BannerListBean>, BannerListBean>() {
                                    @Override
                                    public BannerListBean apply(Reply<BannerListBean> bannerListBeanReply) throws Exception {
                                        return bannerListBeanReply.getData();
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<CollectListBean> getCollectList(int page) {
        return RetrofitHelper.getRetrofitService().getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BaseBean> logout() {
        return RetrofitHelper.getRetrofitService().logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
