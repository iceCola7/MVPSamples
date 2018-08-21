package com.cxz.baselibs.http.cert;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author chenxz
 * @date 2018/8/21
 * @desc TrustAllHostnameVerifier
 */
public class TrustAllHostnameVerifier implements HostnameVerifier {

    /**
     * 返回true，信任所有服务器
     */
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
