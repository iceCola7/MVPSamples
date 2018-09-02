package com.cxz.baselibs.http;

import java.io.Serializable;

/**
 * @author chenxz
 * @date 2018/9/2
 * @desc 抽取的一个基类的bean，直接传入泛型，可根据需求自由定制
 */
public class BaseHttpResult<T> implements Serializable {

    private int errorCode;
    private String errorMsg;
    private T results;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return results;
    }

    public void setData(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", results=" + results +
                '}';
    }

}
