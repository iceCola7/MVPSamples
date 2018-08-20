package com.cxz.baselibs.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cxz.baselibs.imageloader.BaseImageLoaderStrategy;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chenxz on 2017/12/18.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageOptions> {

    @Override
    public void loadImage(Context context, GlideImageOptions options) {
        if (context == null)
            throw new NullPointerException("Context is null");
        if (options == null)
            throw new NullPointerException("ImageOptions is null");
        if (options.getImageView() == null)
            throw new NullPointerException("ImageView is null");

        RequestOptions requestOptions = new RequestOptions();
        // 缓存策略
        switch (options.getCacheStrategy()) {
            case ALL:
                requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case DATA:
                requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case RESOURCE:
                requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case AUTOMATIC:
                requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            case NONE:
                requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            default:
                break;
        }
        // 图片的 ScaleType
        switch (options.getScaleType()) {
            case CENTER_CROP:
                requestOptions = requestOptions.centerCrop();
                break;
            case FIT_CENTER:
                requestOptions = requestOptions.fitCenter();
                break;
            case CENTER_INSIDE:
                requestOptions = requestOptions.centerInside();
                break;
            default:
                break;
        }
        if (options.getPlaceholder() != -1) {
            requestOptions = requestOptions.placeholder(options.getPlaceholder());
        }
        if (options.getErrorPic() != -1) {
            requestOptions = requestOptions.error(options.getErrorPic());
        }
        if (options.getFallback() != -1) {
            requestOptions = requestOptions.fallback(options.getFallback());
        }
        if (options.getTransformation() != null) {
            requestOptions = requestOptions.transform(options.getTransformation());
        }
        if (options.getImageSize() != null) {
            requestOptions = requestOptions.override(options.getImageSize().getImageWidth(), options.getImageSize().getImageHeight());
        }

        requestOptions = requestOptions
                .skipMemoryCache(options.isSkipMemoryCache());

        RequestManager manager = Glide.with(context);
        if (options.isAsGif()) {
            manager.asGif()
                    .load(options.getUrl())
                    .apply(requestOptions)
                    .into(options.getImageView());
        } else {
            manager.load(options.getUrl())
                    .apply(requestOptions)
                    .into(options.getImageView());
        }

    }

    @Override
    public void clear(final Context context, GlideImageOptions options) {

        if (context == null)
            throw new NullPointerException("Context is null");
        if (options == null)
            throw new NullPointerException("ImageOptions is null");

        if (options.getImageView() != null) {
            Glide.get(context).getRequestManagerRetriever().get(context).clear(options.getImageView());
        }

        // 清除本地缓存
        if (options.isClearDiskCache()) {
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Glide.get(context).clearDiskCache();
                        }
                    });
        }

        // 清除内存缓存
        if (options.isClearMemory()) {
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Glide.get(context).clearMemory();
                        }
                    });
        }
    }
}
