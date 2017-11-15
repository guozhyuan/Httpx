package com.qinxiang.httplib;

/**
 * Http请求回调，For IHttpXImpl实现器
 * @author yanbin
 */
public interface IRequestCallback {
    void onError(String message);
    void onResponse(String url, int statusCode, String body);
}
