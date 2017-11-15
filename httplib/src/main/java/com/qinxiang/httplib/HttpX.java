package com.qinxiang.httplib;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于与服务器进行Http通信并按协议进行处理的工具类
 *
 * @author yanbin
 */
public class HttpX {

    // 默认成功回调
    private static ICallback _defaultSuccCallback = null;

    // 默认失败回调
    private static ICallback _defaultFailCallback = null;

    // 默认需要登录回调
    private static ICallback _defaultNeedLoginCallback = null;

    // 默认错误回调
    private static ICallback _defaultErrorCallback = null;

    // http实现器
    private static IHttpXImpl _httpXImpl = null;

    /**
     * 初始化
     *
     * @param impl                     实现器实例
     * @param defaultSuccCallback      默认成功回调
     * @param defaultFailCallback      默认失败回调
     * @param defaultNeedLoginCallback 默认需要登录回调
     * @param defaultErrorCallback     默认错误回调
     * @param params                   参数表（主要用于实现器初始化）
     */
    public static void init(IHttpXImpl impl, ICallback defaultSuccCallback, ICallback defaultFailCallback, ICallback defaultNeedLoginCallback, ICallback defaultErrorCallback, HashMap<String, Object> params) {
        _defaultSuccCallback = defaultSuccCallback;
        _defaultFailCallback = defaultFailCallback;
        _defaultNeedLoginCallback = defaultNeedLoginCallback;
        _defaultErrorCallback = defaultErrorCallback;

        _httpXImpl = impl;
        _httpXImpl.init(params);
    }

    /**
     * 发起一个异步请求
     *
     * @param resDataClass      请求结果模型中的data的类型
     * @param method            Method.Get / Method.Post
     * @param url               请求地址URL
     * @param params            请求参数
     * @param succCallback      成功回调
     * @param failCallback      失败回调
     * @param needLoginCallback 需要登录回调
     * @param errorCallback     错误回调
     * @param <T>               请求结果模型中的data的类型
     */
    public static <T> void requestAsync(final Class<T> resDataClass, Method method, String url, IParams params, final ICallback<T> succCallback, final ICallback<T> failCallback, final ICallback<T> needLoginCallback, final ICallback<T> errorCallback) {
        _httpXImpl.requestAsync(method, url, params, new IRequestCallback() {
            // 请求不成功
            @Override
            public void onError(String message) {
                Log.i("HttpX.onError", message + "");
                onErrorHandler(message, errorCallback);
            }

            // 请求成功，但要根据response及返回内容进行对应处理
            @Override
            public void onResponse(String url, int statusCode, String body) {
                try {
                    boolean isSuccessful = isResponseSuccessful(statusCode);
                    Log.i("HttpX.onResponse", "URL: " + url + "\nStatusCode:" + statusCode + "\nBody:" + body);

                    if (isSuccessful) {
                        // 转换body(json)为HttpXResult模型
                        Gson gson = new GsonBuilder().create();
                        HttpXResult res = gson.fromJson(body, HttpXResult.class);

                        // 根据协议约定，判断status并进行相应处理
                        switch (res.status) {
                            case HttpXStatus.SUCCESS:
                                // 此处可以优化，data转换了两次
                                String dataStr = gson.toJson(res.data);
                                if (dataStr.startsWith("[")) {
                                    if (((List) res.data).size() == 0) {
                                        res.data = new ArrayList<>();
                                    } else {
                                        res.data = gson.fromJson(dataStr, new TypeToken<T>() {
                                        }.getType());
                                    }

                                } else {
                                    res.data = gson.fromJson(dataStr, resDataClass);
                                }
                               /* Log.e("heh", resDataClass.getClass().getName() + "    " + gson.toJson(res.data));
                                if (Collection.class.isAssignableFrom(resDataClass) || Map.class.isAssignableFrom(resDataClass)) {
                                    res.data = gson.fromJson(gson.toJson(res.data), new TypeToken<T>() {
                                    }.getType());
                                } else if (res.data != null && ((List) res.data).size() == 0) {
                                    res.data = new ArrayList<>();

                                } else {
                                    res.data = gson.fromJson(gson.toJson(res.data), resDataClass);
                                }*/

                                if (succCallback != null)
                                    succCallback.execute(res);
                                else if (_defaultSuccCallback != null)
                                    _defaultSuccCallback.execute(res);
                                break;

                            case HttpXStatus.FAILED:
                                if (failCallback != null)
                                    failCallback.execute(res);
                                else if (_defaultFailCallback != null)
                                    _defaultFailCallback.execute(res);
                                break;

                            case HttpXStatus.NEED_LOGIN:
                                if (needLoginCallback != null)
                                    needLoginCallback.execute(res);
                                else if (_defaultNeedLoginCallback != null)
                                    _defaultNeedLoginCallback.execute(res);
                                break;

                            default:
                                onErrorHandler("Unknow status: " + res.status, errorCallback);
                        }
                    } else {
                        onErrorHandler("Failed Response Status Code: " + statusCode, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onErrorHandler(" ERROR : " + e.getMessage(), null);
                }

            }
        });
    }

    /**
     * 发起一个异步请求
     *
     * @param resDataClass      请求结果模型中的data的类型
     * @param method            Method.Get / Method.Post
     * @param url               请求地址URL
     * @param params            请求参数
     * @param succCallback      成功回调
     * @param failCallback      失败回调
     * @param needLoginCallback 需要登录回调
     * @param <T>               请求结果模型中的data的类型
     */
    public static <T> void requestAsync(final Class<T> resDataClass, Method method, String url, IParams params, final ICallback<T> succCallback, final ICallback<T> failCallback, final ICallback<T> needLoginCallback) {
        requestAsync(resDataClass, method, url, params, succCallback, failCallback, needLoginCallback, null);
    }

    /**
     * 发起一个异步请求
     *
     * @param resDataClass 请求结果模型中的data的类型
     * @param method       Method.Get / Method.Post
     * @param url          请求地址URL
     * @param params       请求参数
     * @param succCallback 成功回调
     * @param failCallback 失败回调
     * @param <T>          请求结果模型中的data的类型
     */
    public static <T> void requestAsync(final Class<T> resDataClass, Method method, String url, IParams params, final ICallback<T> succCallback, final ICallback<T> failCallback) {
        requestAsync(resDataClass, method, url, params, succCallback, failCallback, null, null);
    }

    /**
     * 发起一个异步请求
     *
     * @param resDataClass 请求结果模型中的data的类型
     * @param method       Method.Get / Method.Post
     * @param url          请求地址URL
     * @param params       请求参数
     * @param succCallback 成功回调
     * @param <T>          请求结果模型中的data的类型
     */
    public static <T> void requestAsync(final Class<T> resDataClass, Method method, String url, IParams params, final ICallback<T> succCallback) {
        requestAsync(resDataClass, method, url, params, succCallback, null, null, null);
    }


    /**
     * 错误处理
     *
     * @param message       错误消息
     * @param errorCallback 错误回调
     */
    private static void onErrorHandler(String message, final ICallback errorCallback) {
        try {
            HttpXResult res = new HttpXResult();
            res.status = HttpXStatus.FAILED;
            res.error = message;

            if (errorCallback != null) {
                errorCallback.execute(res);
            } else if (_defaultErrorCallback != null) {
                _defaultErrorCallback.execute(res);
            }
        } catch (Exception e) {
            Log.e("HttpX", e.getMessage());
        }

    }

    /**
     * 判断Response的StatusCode是否成功
     *
     * @param statusCode
     * @return boolean
     */
    private static boolean isResponseSuccessful(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }


    /**
     * 发起一个异步请求 返回结果是List
     *
     * @param method            Method.Get / Method.Post
     * @param url               请求地址URL
     * @param params            请求参数
     * @param succCallback      成功回调
     * @param failCallback      失败回调
     * @param needLoginCallback 需要登录回调
     * @param errorCallback     错误回调
     * @param <T>               请求结果模型中的data的类型
     */
    public static <T> void requestAsync(Method method, String url, IParams params, final ICallback<T> succCallback, final ICallback<T> failCallback, final ICallback<T> needLoginCallback, final ICallback<T> errorCallback) {
        _httpXImpl.requestAsync(method, url, params, new IRequestCallback() {
            // 请求不成功
            @Override
            public void onError(String message) {
                Log.i("HttpX.onError", message + "");
                onErrorHandler(message, errorCallback);
            }

            // 请求成功，但要根据response及返回内容进行对应处理
            @Override
            public void onResponse(String url, int statusCode, String body) {
                try {
                    boolean isSuccessful = isResponseSuccessful(statusCode);
                    Log.i("HttpX.onResponse", "URL: " + url + "\nStatusCode:" + statusCode + "\nBody:" + body);

                    if (isSuccessful) {
                        // 转换body(json)为HttpXResult模型
                        Gson gson = new GsonBuilder().create();
                        HttpXResult res = gson.fromJson(body, HttpXResult.class);
                        // 根据协议约定，判断status并进行相应处理
                        switch (res.status) {
                            case HttpXStatus.SUCCESS:
                                if (res.itemVo != null) {
                                    //MyApplication.processItemVo(res.itemVo); // TODO:需要验证
                                }
                                if (succCallback != null)
                                    succCallback.execute(res);
                                else if (_defaultSuccCallback != null)
                                    _defaultSuccCallback.execute(res);
                                break;

                            case HttpXStatus.FAILED:
                                if (failCallback != null)
                                    failCallback.execute(res);
                                else if (_defaultFailCallback != null)
                                    _defaultFailCallback.execute(res);
                                break;

                            case HttpXStatus.NEED_LOGIN:
                                if (needLoginCallback != null)
                                    needLoginCallback.execute(res);
                                else if (_defaultNeedLoginCallback != null)
                                    _defaultNeedLoginCallback.execute(res);
                                break;

                            default:
                                onErrorHandler("Unknow status: " + res.status, errorCallback);
                        }
                    } else {
                        onErrorHandler("Failed Response Status Code: " + statusCode, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    onErrorHandler(" ERROR : " + e.getMessage(), null);
                }

            }
        });
    }

    public static <T> void requestAsync(Method method, String url, IParams params, final ICallback<T> succCallback) {
        requestAsync(method, url, params, succCallback, null, null, null);
    }

    public static <T> void requestAsync(Method method, String url, IParams params, final ICallback<T> succCallback, final ICallback<T> failCallback) {
        requestAsync(method, url, params, succCallback, failCallback, null, null);
    }

    public static <T> void requestAsync(Method method, String url, IParams params, final ICallback<T> succCallback, final ICallback<T> failCallback, final ICallback<T> needLoginCallback) {
        requestAsync(method, url, params, succCallback, failCallback, needLoginCallback, null);
    }

    /**
     * post files with multirequest
     *
     * @param url   host
     * @param map   extra params
     * @param files files what need upload
     */
    public static void postFiles(String url, HashMap<String, String> map, File[] files, IRequestCallback callback) {
        _httpXImpl.postFile(url, map, files, callback);
    }

}
