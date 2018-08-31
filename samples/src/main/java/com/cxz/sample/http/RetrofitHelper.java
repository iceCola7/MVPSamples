package com.cxz.sample.http;

import com.cxz.baselibs.http.RetrofitManager;
import com.cxz.sample.http.cache.CacheProvider;
import com.cxz.sample.http.service.RetrofitService;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public class RetrofitHelper {

    /**
     * 获取 RetrofitService
     *
     * @return
     */
    public static RetrofitService getRetrofitService() {
        return RetrofitManager.getInstance().obtainRetrofitService(Api.WEATHER_HOST, RetrofitService.class);
    }

    /**
     * 获取 CacheService
     *
     * @return
     */
    public static CacheProvider getCacheService() {
        return RetrofitManager.getInstance().obtainCacheService(CacheProvider.class);
    }

}
