package com.cxz.samples.http.interceptor;

import com.cxz.samples.app.App;
import com.cxz.samples.util.NetUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by chenxz on 2017/12/2.
 * 云端响应头拦截器，用来配置缓存策略
 */

public class RewriteCacheControlInterceptor implements Interceptor {

    //设置缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //30秒直接读缓存
    private static final long CACHE_AGE_SEC = 30;

    private static RewriteCacheControlInterceptor instance;

    public static RewriteCacheControlInterceptor getInstance() {
        if (instance == null) {
            instance = new RewriteCacheControlInterceptor();
        }
        return instance;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // 在这里统一配置请求头缓存策略以及响应头缓存策略
        if (NetUtil.isConnected(App.getInstance())) {
            // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
            request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            Response response = chain.proceed(request);
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
        } else {
            // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
            request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            Response response = chain.proceed(request);
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
        }
    }
}
