package com.cxz.samples.module.other;

import com.cxz.samples.event.MessageEvent;
import com.cxz.samples.base.mvp.IModel;
import com.cxz.samples.base.mvp.IPresenter;
import com.cxz.samples.base.mvp.IView;

/**
 * Created by chenxz on 2017/12/3.
 */

public interface OtherContract {

    interface View extends IView {
        void setMessage(MessageEvent messageEvent);
    }

    interface Model extends IModel {

    }

    interface Presenter extends IPresenter {
        void sendMessage();
    }

}
