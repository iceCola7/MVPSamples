package com.cxz.baselibs.http.interceptor;

import android.text.TextUtils;

import com.cxz.baselibs.http.HttpConstant;
import com.cxz.baselibs.utils.SPUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author chenxz
 * @date 2019/4/6
 * @desc Cookie 的保存和删除
 */
public class CookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requestUrl = request.url().toString();
        String domain = request.url().host();

        Response response = chain.proceed(request);
        if (requestUrl.contains(HttpConstant.SAVE_USER_LOGIN_KEY)) {
            List<String> cookies = response.headers(HttpConstant.SET_COOKIE_KEY);
            SPUtils.getInstance().put(domain, encodeCookie(cookies));
        } else if (requestUrl.contains(HttpConstant.REMOVE_USER_LOGOUT_KEY)) {
            SPUtils.getInstance().remove(domain);
        }

        return response;
    }

    private String encodeCookie(List<String> cookies) {

        HashSet<String> set = new HashSet<>();

        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (!TextUtils.isEmpty(s))
                    set.add(s);
            }
        }

        StringBuilder sb = new StringBuilder();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append(";");
        }

        int last = sb.lastIndexOf(";");
        if (last != -1 && sb.length() - 1 == last) {
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }

}
