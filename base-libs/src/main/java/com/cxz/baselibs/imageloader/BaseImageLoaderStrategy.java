package com.cxz.baselibs.imageloader;

import android.content.Context;

/**
 * Created by chenxz on 2017/12/18.
 * 策略设计模式
 */

public interface BaseImageLoaderStrategy<T extends ImageOptions> {

    /**
     * 加载图片
     *
     * @param context
     * @param options
     */
    void loadImage(Context context, T options);

    /**
     * 停止加载或者清理缓存
     *
     * @param context
     * @param options
     */
    void clear(Context context, T options);

}
