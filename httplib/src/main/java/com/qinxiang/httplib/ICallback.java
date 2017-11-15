package com.qinxiang.httplib;

/**
 * 处理HttpX请求返回结果模型的回调接口
 * @param <T> 返回结果模型中data的类型
 */
public interface ICallback<T> {
    void execute(HttpXResult<T> res) throws Exception;
}
