package com.qinxiang.httplib.impl;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.qinxiang.httplib.IHttpXImpl;
import com.qinxiang.httplib.IParams;
import com.qinxiang.httplib.IRequestCallback;
import com.qinxiang.httplib.Method;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * HttpX的请求实现之OKHttp
 *
 * @author yanbin
 */
public class HttpXImplOKHttp implements IHttpXImpl {

    private String URL_ROOT = "";
    private static OkHttpClient okHttpClient = null;
    private StethoInterceptor stethoInterceptor = null;


    @Override
    public void init(HashMap<String, Object> params) {
        if (params.containsKey("urlRoot")) URL_ROOT = (String) params.get("urlRoot");
        long timeout = params.containsKey("timeout") ? (long) params.get("timeout") : 20000L;
        boolean isEnableDebug = params.containsKey("enableDebug") ? (boolean) params.get("enableDebug") : false;
        String cacheDir = params.containsKey("cacheDir") ? (String) params.get("cacheDir") : null;
        int cacheMaxSize = params.containsKey("cacheMaxSize") ? (int) params.get("cacheMaxSize") : null;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File httpResponseCache = new File(cacheDir, "HttpResponseCache");
        synchronized (HttpXImplOKHttp.class) {
            if (okHttpClient == null) {
                OkHttpClient.Builder okHttpClientbuilder = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .retryOnConnectionFailure(true)
                        .connectTimeout(timeout, TimeUnit.SECONDS)
                        .writeTimeout(timeout, TimeUnit.SECONDS)
                        .readTimeout(timeout, TimeUnit.SECONDS)
                        .cache(new Cache(httpResponseCache, cacheMaxSize));

                if (isEnableDebug) {
                    okHttpClientbuilder.addNetworkInterceptor(new StethoInterceptor());
                }

                okHttpClient = okHttpClientbuilder.build();
            }
        }


    }

    @Override
    public void requestAsync(Method method, String url, IParams params, final IRequestCallback callback) {
        Request req = null;
        String reqUrl = "";
        if (Method.POST == method && params.getKeyValuePair().size() > 0) {
            reqUrl = URL_ROOT + url;
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (Map.Entry<String, String> nvp : params.getKeyValuePair().entrySet()) {
                multipartBuilder.addFormDataPart(nvp.getKey(), nvp.getValue());
            }
            RequestBody requestBody = multipartBuilder.build();
            req = new Request.Builder()
                    .url(reqUrl)
                    .post(requestBody)
                    .build();

        } else {
            reqUrl = URL_ROOT + (params == null ? url : params.appendToUrl(url));
            req = new Request.Builder().url(reqUrl).build();
        }
        Log.i("HttpX|" + this.getClass().getName(), "Request URL: " + reqUrl);
        final String finalUrl = reqUrl;

        okHttpClient.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                callback.onResponse(finalUrl, response.code(), body);
            }
        });
    }


    /**
     * post files with multirequest
     *
     * @param url      host
     * @param map      extra params
     * @param files    files what need upload
     * @param callback callback
     */
    @Override
    public void postFile(String url, HashMap<String, String> map, File[] files, final IRequestCallback callback) {
        final String reqUrl = URL_ROOT + url;
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (!map.isEmpty() && files.length > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            for (int i = 0; i < files.length; i++) {
                multipartBuilder.addFormDataPart("file" + i, files[i].getName(), RequestBody.create(MultipartBody.FORM, files[i]));
            }
            Request request = new Request.Builder()
//                    .header("","")
                    .url(reqUrl)
                    .post(multipartBuilder.build())
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    callback.onResponse(reqUrl, response.code(), body);
                }
            });
        } else {
            throw new IllegalArgumentException("params maybe null");
        }

    }



    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

}
