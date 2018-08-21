package com.cxz.baselibs.http.function;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc 请求重连
 */
public class RetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int maxRetryCount = 3; // 可重试次数
    private long retryDelayMillis = 5000; // 重试等待时间

    public RetryWithDelay() {
    }

    public RetryWithDelay(long retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
    }

    public RetryWithDelay(int maxRetryCount, long retryDelayMillis) {
        this.maxRetryCount = maxRetryCount;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {

        return observable
                .zipWith(Observable.range(1, maxRetryCount + 1), new BiFunction<Throwable, Integer, Wrapper>() {
                    @Override
                    public Wrapper apply(Throwable throwable, Integer integer) throws Exception {
                        return new Wrapper(integer, throwable);
                    }
                }).flatMap(new Function<Wrapper, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Wrapper wrapper) throws Exception {
                        Throwable t = wrapper.throwable;
                        if ((t instanceof ConnectException
                                || t instanceof SocketTimeoutException
                                || t instanceof TimeoutException
                                || t instanceof HttpException)
                                && wrapper.index < maxRetryCount + 1) {
                            return Observable.timer(retryDelayMillis * wrapper.index, TimeUnit.SECONDS);
                        }
                        return Observable.error(wrapper.throwable);
                    }
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(int index, Throwable throwable) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}
