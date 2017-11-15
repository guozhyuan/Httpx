package com.qinxiang.rxjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gouzhun on 2016/12/1.
 */

public interface IServerAPI {

    @GET("login/login")
    Observable<LoginBean> login(@Query("username") String username, @Query("password") String password);

}
