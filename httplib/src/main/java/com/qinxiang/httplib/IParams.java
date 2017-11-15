package com.qinxiang.httplib;


import java.util.HashMap;

/**
 * 请求参数构造对象接口
 * @author yanbin
 */
public interface IParams {
    /**
     * 添加一个参数
     * @param key 参数名
     * @param value 参数值
     * @return 返回自身实例
     */
    IParams put(String key, Object value);

    /**
     * 将参数拼接到URL上，一般用于Get请求
     * @param url URL（无参数）
     * @return String 拼好参数的URL
     */
    String appendToUrl(String url);


    HashMap<String,String> getKeyValuePair();

}
