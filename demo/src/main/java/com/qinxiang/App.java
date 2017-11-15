package com.qinxiang;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.qinxiang.httplib.HttpX;
import com.qinxiang.httplib.HttpXResult;
import com.qinxiang.httplib.ICallback;
import com.qinxiang.httplib.impl.HttpXImplOKHttp;
import com.socks.library.KLog;

import java.util.HashMap;

/**
 * Created by guo
 *
 * @ 2016/11/18
 */

public class App extends Application {
    private static final String TAG = "Application";
    private static Context context;
    public static String BASE_API_URL = "http://dev-api.kinsoo.com/v1/";;

    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        initHttpX();
    }

    private void initRxAndroid(){

    }

    private void initHttpX() {
        HashMap<String, Object> params = new HashMap<>();

        params.put("urlRoot", BASE_API_URL);
        params.put("timeout", 15000L);
        params.put("enableDebug", true);
        params.put("cacheDir", getCacheDir().getAbsolutePath());
        params.put("cacheMaxSize", 10 * 1024 * 1024);
        HttpX.init(new HttpXImplOKHttp(), new ICallback() {
            @Override
            public void execute(HttpXResult res) throws Exception {
//                Log.e(TAG, "succCallback" );
                KLog.e(TAG, "succCallback");
            }
        }, new ICallback() {
            @Override
            public void execute(HttpXResult res) throws Exception {
                KLog.e(TAG, "failCallback" );
            }
        }, new ICallback() {
            @Override
            public void execute(HttpXResult res) throws Exception {
                KLog.e(TAG, "needLoginCallback" );
            }
        }, new ICallback() {
            @Override
            public void execute(HttpXResult res) throws Exception {
                KLog.e(TAG, "errorCallback" );
            }
        }, params);
    }
}
