package com.cxz.sample.mvp.contract;

import com.cxz.baselibs.mvp.IModel;
import com.cxz.baselibs.mvp.IPresenter;
import com.cxz.baselibs.mvp.IView;
import com.cxz.sample.mvp.model.bean.WeatherInfo;

import io.reactivex.Observable;

/**
 * @author chenxz
 * @date 2018/8/31
 * @desc
 */
public interface SampleContract {

    interface View extends IView {

        String getCityId();

        void showWeatherInfo(WeatherInfo weatherInfo);

    }

    interface Presenter extends IPresenter<View> {

        void getWeatherInfo(String cityId);

    }

    interface Model extends IModel {

        Observable<WeatherInfo> getWeatherInfo(String cityId);

        Observable<WeatherInfo> getWeatherInfo(String cityId, boolean isUpdate);

    }

}
