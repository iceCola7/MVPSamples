package com.cxz.samples.http;

import com.cxz.samples.app.App;
import com.cxz.samples.http.interceptor.LoggingInterceptor;
import com.cxz.samples.http.interceptor.RewriteCacheControlInterceptor;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenxz on 2017/11/30.
 */

public class RetrofitHelper {

    // 网络请求的超时时间
    private static final long DEFAULT_TIME_OUT = 30;
    private static volatile OkHttpClient mOkHttpClient;
    private RewriteCacheControlInterceptor mRewriteCacheControlInterceptor;
    private LoggingInterceptor mLoggingInterceptor;

    private static RetrofitHelper instance;
    /**
     * 存放不同 HOST 的 Retrofit
     */
    private HashMap<String, Object> mRetrofitServiceCache = new HashMap<>();

    private HashMap<String, Object> mCacheServiceCache = new HashMap<>();

    private RetrofitHelper() {
        mRewriteCacheControlInterceptor = RewriteCacheControlInterceptor.getInstance();
        mLoggingInterceptor = LoggingInterceptor.getInstance();
    }

    /**
     * 创建 Retrofit
     *
     * @param baseUrl
     * @return
     */
    private Retrofit createRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T obtainRetrofitService(Class<T> service) {
        String baseUrl = APi.WEATHER_HOST;
        T retrofitService = (T) mRetrofitServiceCache.get(service.getCanonicalName());
        if (retrofitService == null) {
            synchronized (mRetrofitServiceCache) {
                if (retrofitService == null) {
                    retrofitService = createRetrofit(baseUrl).create(service);
                    mRetrofitServiceCache.put(service.getCanonicalName(), retrofitService);
                }
            }
        }
        return retrofitService;
    }

    /**
     * 创建 RxCache
     *
     * @return
     */
    private RxCache createCache() {
        RxCache rxCache = new RxCache.Builder()
                .persistence(App.getInstance().getCacheDir(), new GsonSpeaker());
        return rxCache;
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    public <T> T obtainCacheService(Class<T> cache) {
        T cacheService = (T) mCacheServiceCache.get(cache.getCanonicalName());
        if (cacheService == null) {
            synchronized (mCacheServiceCache) {
                if (cacheService == null) {
                    cacheService = createCache().using(cache);
                    mCacheServiceCache.put(cache.getCanonicalName(), cacheService);
                }
            }
        }
        return cacheService;
    }

    /**
     * 获取单例
     */
    public static RetrofitHelper getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null)
                    instance = new RetrofitHelper();
            }
        }
        return instance;
    }

    /**
     * 配置OKHttpClient
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //指定缓存路径
                    //XLog.e("----------"+App.getApp().getCacheDir());
                    //File file = new File(App.getApp().getCacheDir().getAbsolutePath(), "HttpCache");
                    //Cache cache = new Cache(file, 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
                            //.cache(cache)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor).addInterceptor(mLoggingInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

}
