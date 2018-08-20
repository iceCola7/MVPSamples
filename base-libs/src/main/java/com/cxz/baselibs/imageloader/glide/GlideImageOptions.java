package com.cxz.baselibs.imageloader.glide;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.cxz.baselibs.imageloader.ImageOptions;

/**
 * Created by chenxz on 2017/12/18.
 */
public class GlideImageOptions extends ImageOptions {

    private DiskCacheStrategy cacheStrategy = DiskCacheStrategy.AUTOMATIC; // 缓存策略
    private int fallback; //请求 url 为空,则使用此图片作为占位符
    private BitmapTransformation transformation;//glide用它来改变图形的形状
    private boolean isSkipMemoryCache = false; //是否跳过内存缓存
    private ImageScaleType scaleType = ImageScaleType.CENTER_CROP; // 图片的 scaleType

    private boolean asGif = false; // 是否作为gif展示
    private boolean isCrossFade = true; // 是否渐变平滑的显示图片,默认为true
    private boolean blurImage = false; // 是否使用高斯模糊

    private boolean isClearMemory = true;//清理内存缓存
    private boolean isClearDiskCache = true;//清理本地缓存

    private GlideImageOptions(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.imageSize = builder.imageSize;
        this.cacheStrategy = builder.cacheStrategy;
        this.fallback = builder.fallback;
        this.asGif = builder.asGif;
        this.isCrossFade = builder.isCrossFade;
        this.isSkipMemoryCache = builder.isSkipMemoryCache;
        this.blurImage = builder.blurImage;
        this.transformation = builder.transformation;
        this.scaleType = builder.scaleType;

        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
    }

    public ImageScaleType getScaleType() {
        return scaleType;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return isSkipMemoryCache;
    }

    public boolean isBlurImage() {
        return blurImage;
    }

    public DiskCacheStrategy getCacheStrategy() {
        return cacheStrategy;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public int getFallback() {
        return fallback;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private ImageView imageView;
        private int placeholder = -1;
        private int errorPic = -1;
        private ImageSize imageSize;
        private DiskCacheStrategy cacheStrategy = DiskCacheStrategy.AUTOMATIC;
        private int fallback = -1; //请求 url 为空,则使用此图片作为占位符
        private BitmapTransformation transformation;//glide用它来改变图形的形状
        private boolean isSkipMemoryCache = false; //是否跳过内存缓存
        private ImageScaleType scaleType = ImageScaleType.CENTER_CROP;// 图片的 scaleType

        private boolean asGif = false;   //是否作为gif展示
        private boolean isCrossFade = true; //是否渐变平滑的显示图片,默认为true
        private boolean blurImage = false; //是否使用高斯模糊

        private boolean isClearMemory = true;//清理内存缓存
        private boolean isClearDiskCache = true;//清理本地缓存

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder ImageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
            return this;
        }

        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cacheStrategy(DiskCacheStrategy cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public Builder asGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder isSkipMemoryCache(boolean isSkipMemoryCache) {
            this.isSkipMemoryCache = isSkipMemoryCache;
            return this;
        }

        public Builder blurImage(boolean blurImage) {
            this.blurImage = blurImage;
            return this;
        }

        public Builder scaleType(ImageScaleType scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public GlideImageOptions build() {
            return new GlideImageOptions(this);
        }
    }

    public enum DiskCacheStrategy {
        ALL, NONE, RESOURCE, DATA, AUTOMATIC
    }

    public enum ImageScaleType {
        CENTER_CROP, FIT_CENTER, CENTER_INSIDE
    }

}

