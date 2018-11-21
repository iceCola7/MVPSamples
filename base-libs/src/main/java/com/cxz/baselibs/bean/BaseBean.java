package com.cxz.baselibs.bean;

/**
 * @author chenxz
 * @date 2018/11/21
 * @desc
 */
public class BaseBean {
    private int errorCode;
    private String errorMsg;

    public BaseBean(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

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
}
