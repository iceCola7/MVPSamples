package com.cxz.baselibs.rx;

import com.cxz.baselibs.rx.scheduler.IoMainScheduler;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc 统一线程处理类
 */
public class RxSchedulers {

    public static IoMainScheduler ioToMain() {
        return new IoMainScheduler();
    }

}
