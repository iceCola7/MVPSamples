package com.cxz.baselibs.http.token;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @author chenxz
 * @date 2018/8/22
 * @desc Token失效的处理方案一：当 HTTP 状态码为 401 时，OkHttp 才会回调 Authenticator.authenticate() 方法，
 * 如果服务端遵循设计规范，可以尝试如下方法
 * 使用方法：new OkHttpClient().setAuthenticator(new TokenAuthenticator());
 */
public class TokenAuthenticator implements Authenticator {
    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        // 取出本地的 Token
        String refreshToken = "";

        // 可以通过一个特定的接口获取新的 Token ，此处要用到同步的 Retrofit 请求
        // Call<String> call = service.refreshToken(refreshToken); // 此行为示例代码

        // 获得的新的 Token
        String newToken = ""; // call.execute().body(); // 示例代码

        return response.request().newBuilder().header("token", refreshToken).build();

    }
}
