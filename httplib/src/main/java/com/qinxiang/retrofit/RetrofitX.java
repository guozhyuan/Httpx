package com.qinxiang.retrofit;

import com.qinxiang.httplib.impl.HttpXImplOKHttp;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gouzhun on 2016/12/5.
 */

public class RetrofitX {


    public <T> T init(final Class<T> service, String urlRoot) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlRoot)
                .client(HttpXImplOKHttp.getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }


    public void destroy() {
        mInstance = null;
    }

    private static RetrofitX mInstance;

    public static RetrofitX getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitX();

        }
        return mInstance;
    }


}
