package com.cxz.baselibs.rx.scheduler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc IoMainScheduler
 */
public class IoMainScheduler extends BaseScheduler {
    public IoMainScheduler() {
        super(Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
