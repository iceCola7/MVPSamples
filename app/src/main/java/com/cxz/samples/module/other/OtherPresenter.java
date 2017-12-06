package com.cxz.samples.module.other;

import android.util.Log;

import com.cxz.samples.event.MessageEvent;
import com.cxz.samples.base.mvp.BasePresenter;
import com.cxz.samples.util.RxUtil;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * Created by chenxz on 2017/12/3.
 */

public class OtherPresenter extends BasePresenter<OtherContract.Model, OtherContract.View> implements OtherContract.Presenter {

    @Inject
    public OtherPresenter(OtherContract.Model model, OtherContract.View view) {
        super(model, view);
    }

    @Override
    public void sendMessage() {
        EventBus.getDefault().post(new MessageEvent("fragment message"));

//        addDispose(Flowable.interval(1, TimeUnit.SECONDS)
//                .compose(RxUtil.<Long>rxSchedulerTransformer())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.e(TAG, "accept: " + aLong);
//                    }
//                }));

        Flowable.interval(1, TimeUnit.SECONDS)
                .compose(RxUtil.<Long>rxSchedulerTransformer())
                .compose(mView.<Long>bindToLife())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept: " + aLong);
                    }
                });

    }

    @Subscriber(mode = ThreadMode.MAIN)
    public void onShowMessage(MessageEvent messageEvent) {
        Log.e(TAG, "onShowMessage: " + messageEvent.getMessage());
        mView.setMessage(messageEvent);
    }
}
