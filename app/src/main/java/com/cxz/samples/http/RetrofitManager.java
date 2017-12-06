package com.cxz.samples.http;

import android.util.SparseArray;

import com.cxz.samples.bean.WeatherInfo;
import com.cxz.samples.http.interceptor.LoggingInterceptor;
import com.cxz.samples.http.interceptor.RewriteCacheControlInterceptor;
import com.cxz.samples.http.service.RetrofitService;
import com.cxz.samples.util.RxUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chenxz on 2017/11/30.
 */

public class RetrofitManager {

    // 网络请求的超时时间
    private static final long DEFAULT_TIME_OUT = 30;
    private static volatile OkHttpClient mOkHttpClient;
    private RewriteCacheControlInterceptor mRewriteCacheControlInterceptor;
    private LoggingInterceptor mLoggingInterceptor;
    // 管理不同的Host的单例
    private static SparseArray<RetrofitManager> mRetrofitManager = new SparseArray<>(HostType.TYPE_COUNT);
    private RetrofitService mService;

    private RetrofitManager() {
    }

    private RetrofitManager(@HostType.HostTypeChecker int hostType) {
        mRewriteCacheControlInterceptor = RewriteCacheControlInterceptor.getInstance();
        mLoggingInterceptor = LoggingInterceptor.getInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APi.getHost(hostType))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mService = retrofit.create(RetrofitService.class);
    }

    /**
     * 获取单例
     *
     * @param hostType {@link HostType} 传入不同的 BaseUrl
     * @return
     */
    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager instance = mRetrofitManager.get(hostType);
        if (instance == null) {
            instance = new RetrofitManager(hostType);
            mRetrofitManager.put(hostType, instance);
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
            synchronized (RetrofitManager.class) {
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

    /**
     * 获取天气 Flowable
     *
     * @param cityId
     * @return
     */
    public Flowable<WeatherInfo> getWeatherInfoObservable(String cityId) {
        return mService.getWeatherInfo(cityId).compose(RxUtil.<WeatherInfo>rxSchedulerTransformer());
    }

}
