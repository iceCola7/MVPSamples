package com.cxz.baselibs.http.exception;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc ApiException
 */
public class ApiException extends RuntimeException {

    private int code;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }

    public ApiException(String message) {
        super(new Throwable(message));
    }

}
