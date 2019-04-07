package com.cxz.sample.mvp.model.bean;

import com.cxz.baselibs.bean.BaseBean;

import java.util.List;

public class BannerListBean extends BaseBean<List<BannerListBean.Banner>> {

    public static class Banner {

        private String desc;
        private int id;
        private String imagePath;
        private int isVisible;
        private int order;
        private String title;
        private int type;
        private String url;

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setIsVisible(int isVisible) {
            this.isVisible = isVisible;
        }

        public int getIsVisible() {
            return isVisible;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

    }

}
