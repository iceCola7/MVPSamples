package com.cxz.baselibs.http;

import com.cxz.baselibs.BuildConfig;
import com.cxz.baselibs.app.BaseApp;
import com.cxz.baselibs.http.cert.TrustAllCerts;
import com.cxz.baselibs.http.cert.TrustAllHostnameVerifier;
import com.cxz.baselibs.http.interceptor.CacheInterceptor;
import com.cxz.baselibs.http.interceptor.CookieInterceptor;
import com.cxz.baselibs.http.interceptor.HeaderInterceptor;

import java.io.File;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc RetrofitManager
 */
public class RetrofitManager {

    // 网络请求的超时时间
    private static final long DEFAULT_TIME_OUT = 30;
    private OkHttpClient mOkHttpClient;
    private HashMap<String, Object> mRetrofitService = new HashMap<>();
    private HashMap<String, Object> mCache = new HashMap<>();

    private RetrofitManager() {
    }

    private static final class RetrofitManagerHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerHolder.INSTANCE;
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param baseUrl
     * @param service
     * @param <T>
     * @return
     */
    public <T> T obtainRetrofitService(String baseUrl, Class<T> service) {
        T retrofitService = (T) mRetrofitService.get(service.getCanonicalName());
        if (retrofitService == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitService == null) {
                    retrofitService = createRetrofit(baseUrl).create(service);
                    mRetrofitService.put(service.getCanonicalName(), retrofitService);
                }
            }
        }
        return retrofitService;
    }

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
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    public <T> T obtainCacheService(Class<T> cache) {
        T cacheService = (T) mCache.get(cache.getCanonicalName());
        if (cacheService == null) {
            synchronized (RetrofitManager.class) {
                if (cacheService == null) {
                    cacheService = createCache().using(cache);
                    mCache.put(cache.getCanonicalName(), cacheService);
                }
            }
        }
        return cacheService;
    }

    private RxCache createCache() {
        RxCache rxCache = new RxCache.Builder()
                .persistence(BaseApp.getInstance().getCacheDir(), new GsonSpeaker());
        return rxCache;
    }

    /**
     * 配置OKHttpClient
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        File cacheFile = new File(BaseApp.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);// 50M 缓存大小

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        mOkHttpClient = builder
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new CookieInterceptor())
                .addInterceptor(new CacheInterceptor())
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())// 创建一个证书对象
                .hostnameVerifier(new TrustAllHostnameVerifier()) // 校验名称,这个对象就是信任所有的主机,也就是信任所有https的请求
                .cache(cache) // 添加缓存
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) // 连接不上是否重连
                .build();
        return mOkHttpClient;
    }

    /**
     * 实现 HTTPS 请求
     */
    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

}
