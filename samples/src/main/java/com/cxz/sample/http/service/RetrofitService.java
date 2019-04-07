package com.cxz.sample.http.service;

import com.cxz.baselibs.bean.BaseBean;
import com.cxz.sample.mvp.model.bean.BannerListBean;
import com.cxz.sample.mvp.model.bean.CollectListBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public interface RetrofitService {

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseBean> login(@Field("username") String username, @Field("password") String password);

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    Observable<BaseBean> logout();

    /**
     * 轮播列表数据
     */
    @GET("banner/json")
    Observable<BannerListBean> getBannerList();

    /**
     * 收藏列表数据
     */
    @GET("lg/collect/list/{page}/json")
    Observable<CollectListBean> getCollectList(@Path("page") int page);

}
