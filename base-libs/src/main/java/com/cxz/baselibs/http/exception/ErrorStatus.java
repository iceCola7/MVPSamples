package com.cxz.baselibs.http.exception;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc ErrorStatus
 */
public class ErrorStatus {

    /**
     * 响应成功
     */
    public static final int SUCCESS = 0;

    /**
     * 未知错误
     */
    public static final int UNKNOWN_ERROR = 1000;

    /**
     * 连接超时
     */
    public static final int TIMEOUT_ERROR = 1001;

    /**
     * 证书出错
     */
    public static final int SSL_ERROR = 1002;

    /**
     * 服务器内部错误
     */
    public static final int SERVER_ERROR = 1003;

    /**
     * 网络连接超时
     */
    public static final int NETWORK_ERROR = 1004;

    /**
     * API解析异常（或者第三方数据结构更改）等其他异常
     */
    public static final int API_ERROR = 1005;

}
