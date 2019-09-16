package com.cxz.baselibs.http.factory;

import android.support.annotation.Nullable;

import com.cxz.baselibs.utils.LogUtils;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author chenxz
 * @date 2019/9/16
 * @desc 静态代理，实现动态管理和修改 BaseUrl，代理{@link okhttp3.Call.Factory} 拦截{@link #newCall(Request)}方法
 */
public abstract class CallFactoryProxy implements Call.Factory {

    private static final String BASE_URL_NAME = "BaseUrlName";
    private final Call.Factory delegate;

    public CallFactoryProxy(Call.Factory delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call newCall(Request request) {
        String baseUrlName = request.header(BASE_URL_NAME);
        if (baseUrlName != null) {
            HttpUrl newHttpUrl = attachNewUrl(baseUrlName, request);
            if (newHttpUrl != null) {
                Request newRequest = request.newBuilder().url(newHttpUrl).build();
                return delegate.newCall(newRequest);
            } else {
                LogUtils.d("method attachNewUrl() return null,baseUrlName = " + baseUrlName);
            }
        }
        return delegate.newCall(request);
    }

    @Nullable
    protected abstract HttpUrl attachNewUrl(String baseUrlName, Request request);
}
