package com.qinxiang.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qinxiang.App;
import com.qinxiang.demo.R;
import com.qinxiang.httplib.HttpX;
import com.qinxiang.httplib.HttpXResult;
import com.qinxiang.httplib.ICallback;
import com.qinxiang.httplib.Method;
import com.qinxiang.httplib.Params;
import com.qinxiang.retrofit.RetrofitX;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gouzhun on 2016/12/1.
 */

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    TextView tv_response;
    IServerAPI api ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv_response = (TextView) findViewById(R.id.tv_response);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();


                requestHttpx();
            }
        });

        api = RetrofitX.getInstance().init(IServerAPI.class, App.BASE_API_URL);
    }


    public void requestHttpx() {
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


    public void request() {
       /* MyRetrofitManager.getOkHttpClient().builder().login("13636602646", "111111").enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                tv_response.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                tv_response.setText(t.getMessage());
            }
        });*/



        api.login("13636602646","111111")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginBean>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        tv_response.setText(loginBean.toString());
                    }
                });

    }
}
