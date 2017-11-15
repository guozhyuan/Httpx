package com.qinxiang.rxjava;

import com.qinxiang.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gouzhun on 2016/12/1.
 */

public class MyRetrofitManager {


    private static OkHttpClient mOkHttpClient;
    //短缓存有效期为1秒钟
    public static final int CACHE_STALE_SHORT = 1;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;
    private IServerAPI mJtmlService;


    private MyRetrofitManager() {

        initOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(App.BASE_API_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(new MyCustomFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mJtmlService = retrofit.create(IServerAPI.class);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (MyRetrofitManager.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(App.getContext().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
//                            .addInterceptor(mRewriteCacheControlInterceptor)
//                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }

    }

    private static MyRetrofitManager mInstance;

    public static MyRetrofitManager getInstance() {
        if (mInstance == null) {
            mInstance = new MyRetrofitManager();

        }
        return mInstance;
    }

    public IServerAPI builder() {
        return mJtmlService;
    }
}
