package com.qinxiang.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qinxiang.httplib.HttpX;
import com.qinxiang.httplib.HttpXResult;
import com.qinxiang.httplib.ICallback;
import com.qinxiang.httplib.Method;
import com.qinxiang.httplib.Params;

//import com.socks.library.KLog;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
    }


    public void request() {
        Params p = new Params();
        p.put("accessToken", "fghjkl;")
                .put("mark", "random")
                .put("username", "15550029999")
                .put("password", "1111111");
        HttpX.requestAsync(Method.GET, "login/login", p, new ICallback<Object>() {
            @Override
            public void execute(HttpXResult<Object> res) throws Exception {
//                KLog.e(TAG, "login");
//                KLog.e("login");
            }
        });

    }
}
