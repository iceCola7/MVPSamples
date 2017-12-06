package com.cxz.samples.http;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by chenxz on 2017/11/30.
 */
public class HostType {

    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 1;

    /**
     * 天气查询的host
     */
    @HostTypeChecker
    public static final int WEATHER_INFO = 1;

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({WEATHER_INFO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {
    }

}
