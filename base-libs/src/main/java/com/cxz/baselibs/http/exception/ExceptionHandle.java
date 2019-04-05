package com.cxz.baselibs.http.exception;

import com.cxz.baselibs.http.HttpStatus;
import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc 异常处理类
 */
public class ExceptionHandle {

    private static String TAG = "ExceptionHandle";
    private static int errorCode = HttpStatus.UNKNOWN_ERROR;
    private static String errorMsg = "请求失败，请稍后重试";

    public static String handleException(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) { // 网络超时
            errorMsg = "网络连接超时";
            errorCode = HttpStatus.TIMEOUT_ERROR;
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) { // 均视为网络错误
            Logger.e(TAG, "网络连接异常：", e.getMessage());
            errorMsg = "网络连接异常";
            errorCode = HttpStatus.NETWORK_ERROR;
        } else if (e instanceof JSONException
                || e instanceof JsonParseException
                || e instanceof ParseException) { // 均视为解析错误
            errorMsg = "数据解析异常";
            errorCode = HttpStatus.SERVER_ERROR;
        } else if (e instanceof SSLHandshakeException) {
            errorMsg = "证书验证失败";
            errorCode = HttpStatus.SSL_ERROR;
        } else if (e instanceof IllegalArgumentException) {
            errorMsg = "参数错误";
            errorCode = HttpStatus.SERVER_ERROR;
        } else if (e instanceof ApiException) { // 服务器返回的错误信息
            errorMsg = e.getMessage();
            errorCode = HttpStatus.API_ERROR;
        } else { // 未知错误
            try {
                Logger.e(TAG, "错误: " + e.getMessage());
            } catch (Exception e1) {
                Logger.e(TAG, "未知错误Debug调试 ");
            }
            errorMsg = "未知错误，可能抛锚了吧~";
            errorCode = HttpStatus.UNKNOWN_ERROR;
        }
        return errorMsg;
    }

}
