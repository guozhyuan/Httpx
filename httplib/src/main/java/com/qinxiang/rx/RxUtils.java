package com.qinxiang.rx;

import android.util.Log;

import com.qinxiang.httplib.impl.HttpXImplOKHttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gouzhun on 2016/12/2.
 */

public class RxUtils {




    /**
     * 定义下载方法，使用rx的编程思想
     *
     * @param fw
     * @return
     */
    public static Observable<FileWrapper> downloadFile(final FileWrapper fw) {
        //创建被观察者
        return Observable.create(new Observable.OnSubscribe<FileWrapper>() {
            @Override
            public void call(final Subscriber<? super FileWrapper> subscriber) {
                //判断观察者和被观察者是否存在订阅关系
                if (!subscriber.isUnsubscribed() && fw != null && fw.url != null) {
                    Request request = new Request.Builder().url(fw.url).build();
                    HttpXImplOKHttp.getOkHttpClient().newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            subscriber.onError(e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                //拿到结果的一瞬间触发事件，并传递数据给观察者
                                //把请求结果转化成字节数组
                                byte[] bytes = response.body().bytes();
                                fw.bytes = bytes;
                                subscriber.onNext(fw);
                            }
                            //数据发送已经完成
                            subscriber.onCompleted();
                        }

                    });


                }
            }
        });

    }




    public static Observable<FileWrapper> downloadFiles(Observable<FileWrapper>  mapedFw) {
        //创建被观察者
        return mapedFw
                .flatMap(new Func1<FileWrapper, Observable<FileWrapper>>() {
                    @Override
                    public Observable<FileWrapper> call(final FileWrapper fw) {
                        String url = fw.url;
                        Log.e("TEST","url ： " + url);
                        if (url == null || url.equalsIgnoreCase("")) {

                            Log.e("TEST","file exists, not downloading");
                            return Observable.create(new Observable.OnSubscribe<FileWrapper>() {
                                @Override
                                public void call(Subscriber<? super FileWrapper> subscriber) {
                                    //判断观察者和被观察者是否存在订阅关系
                                    if (!subscriber.isUnsubscribed()) {

                                        subscriber.onNext(fw);

                                        subscriber.onCompleted();

                                    }
                                }
                            }).subscribeOn(Schedulers.io());
                        }

                        final Observable<FileWrapper> fileObservable = Observable.create(new Observable.OnSubscribe<FileWrapper>() {
                            @Override
                            public void call(final Subscriber<? super FileWrapper> subscriber) {
                                //判断观察者和被观察者是否存在订阅关系
                                if (!subscriber.isUnsubscribed()) {
                                    Request request = new Request.Builder().url(fw.url).build();
                                    Log.e("TEST","downloading: " + fw.url);
                                    HttpXImplOKHttp.getOkHttpClient().newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            subscriber.onError(e);
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            if (response.isSuccessful()) {
                                                //拿到结果的一瞬间触发事件，并传递数据给观察者
                                                //把请求结果转化成字节数组
                                                byte[] bytes = response.body().bytes();
                                                fw.bytes = bytes;
                                                subscriber.onNext(fw);
                                            }
                                            //数据发送已经完成
                                            subscriber.onCompleted();
                                        }

                                    });


                                }
                            }
                        });
                        return fileObservable.subscribeOn(Schedulers.io());
                    }
                });
    }



    public static Observable<FileWrapper> downloadFiles(final FileWrapper[] fws) {
        //创建被观察者
        return Observable.from(fws)
                .flatMap(new Func1<FileWrapper, Observable<FileWrapper>>() {
                    @Override
                    public Observable<FileWrapper> call(final FileWrapper fw) {
                        String url = fw.url;
                        Log.e("TEST","url ： " + url);
                        if (url == null || url.equalsIgnoreCase("")) {

                            Log.e("TEST","file exists, not downloading");
                            return Observable.create(new Observable.OnSubscribe<FileWrapper>() {
                                @Override
                                public void call(Subscriber<? super FileWrapper> subscriber) {
                                    //判断观察者和被观察者是否存在订阅关系
                                    if (!subscriber.isUnsubscribed()) {

                                        subscriber.onNext(fw);

                                        subscriber.onCompleted();

                                    }
                                }
                            }).subscribeOn(Schedulers.io());
                        }

                        final Observable<FileWrapper> fileObservable = Observable.create(new Observable.OnSubscribe<FileWrapper>() {
                            @Override
                            public void call(final Subscriber<? super FileWrapper> subscriber) {
                                //判断观察者和被观察者是否存在订阅关系
                                if (!subscriber.isUnsubscribed()) {
                                    Request request = new Request.Builder().url(fw.url).build();
                                    Log.e("TEST","downloading: " + fw.url);
                                    HttpXImplOKHttp.getOkHttpClient().newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            subscriber.onError(e);
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                            if (response.isSuccessful()) {
                                                //拿到结果的一瞬间触发事件，并传递数据给观察者
                                                //把请求结果转化成字节数组
                                                byte[] bytes = response.body().bytes();
                                                fw.bytes = bytes;
                                                subscriber.onNext(fw);
                                            }
                                            //数据发送已经完成
                                            subscriber.onCompleted();
                                        }

                                    });


                                }
                            }
                        }) ;
                        return fileObservable.subscribeOn(Schedulers.io());
                    }
                });
    }
}


