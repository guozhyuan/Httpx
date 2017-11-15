package com.qinxiang.httplib;

import java.io.File;
import java.util.HashMap;

/**
 * HttpX的Http请求实现器的接口
 *
 * @author yanbin
 */
public interface IHttpXImpl {
    /**
     * 初始化Http实现器
     *
     * @param params 参数表，每个实现器需要的参数不同，见具体实现
     */
    void init(HashMap<String, Object> params);

    /**
     * 进行异步请求
     *
     * @param method   POST/GET
     * @param url      请求地址URL
     * @param params   请求参数
     * @param callback 请求成功回调
     */
    void requestAsync(Method method, String url, IParams params, IRequestCallback callback);

    void postFile(String url, HashMap<String, String> map,File[] files , IRequestCallback callback);
}
