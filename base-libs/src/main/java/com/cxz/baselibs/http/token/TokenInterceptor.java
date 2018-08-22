package com.cxz.baselibs.http.token;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author chenxz
 * @date 2018/8/22
 * @desc Token失效的处理方案二：如果服务端没有遵循设计规范，可采用以下方法
 * 使用方法：addInterceptor(TokenInterceptor());
 */
public class TokenInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        // try the request
        Response originalResponse = chain.proceed(request);

        /**通过如下的办法曲线取到请求完成的数据
         *
         * 原本想通过  originalResponse.body().string()
         * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
         *
         * 然后去看了okhttp的源码,找到了这个曲线方法,取到请求完成的数据后,根据特定的判断条件去判断token过期
         */
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);

        Log.e("body----------->>", bodyString);

        /***************************************/

//        if (判断是否过期){//根据和服务端的约定判断token过期
//
//            //取出本地的refreshToken
//            String refreshToken = "";
//
//            // 可以通过一个特定的接口获取新的 Token ，此处要用到同步的 Retrofit 请求
//            // Call<String> call = service.refreshToken(refreshToken); // 此行为示例代码
//
//            // 获得的新的 Token
//            String newToken = ""; // call.execute().body(); // 示例代码
//
//            // create a new request and modify it accordingly using the new token
//            Request newRequest = request.newBuilder().header("token", newToken)
//                    .build();
//
//            // retry the request
//            originalResponse.body().close();
//            return chain.proceed(newRequest);
//        }

        // otherwise just pass the original response on
        return originalResponse;

    }
}
