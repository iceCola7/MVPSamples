package com.cxz.baselibs.imageloader;

import android.widget.ImageView;

/**
 * Created by chenxz on 2017/12/18.
 */

public class ImageOptions {

    protected String url;
    protected ImageView imageView;
    protected int placeholder = -1;// 占位符
    protected int errorPic = -1;// 错误占位符
    protected ImageSize imageSize;

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public class ImageSize{
        private int imageWidth;
        private int imageHeight;

        public ImageSize(int imageWidth, int imageHeight) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
        }

        public int getImageWidth() {
            return imageWidth;
        }

        public int getImageHeight() {
            return imageHeight;
        }
    }
}
