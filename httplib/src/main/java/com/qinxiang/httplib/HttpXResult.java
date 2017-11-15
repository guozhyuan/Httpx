package com.qinxiang.httplib;


import java.util.List;

/**
 * 返回结果模型
 *
 * @param <T> 指定data属性的类型
 */
public class HttpXResult<T> {
    public int status;
    public String error;
    public T data;
    public List<T> itemVo;
}
