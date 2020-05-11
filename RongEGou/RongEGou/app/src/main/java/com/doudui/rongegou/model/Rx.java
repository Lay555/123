package com.doudui.rongegou.model;


import android.support.annotation.NonNull;
import android.util.Log;

import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.Config;
import com.doudui.rongegou.manager.NoticeObserver;
import com.doudui.rongegou.model.bean.Result;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求工具类
 * Created by Administrator on 2020/5/9.
 */
public class Rx {
    private Rx() {
        throw new AssertionError("无法实例化该类");
    }

    public static final String BaseUrl = com.doudui.rongegou.BuildConfig.BASEURL;


    public static ApiService apiService;

    /**
     * contract
     * 创建一个Retrofit对象
     *
     * @return
     */
    public static synchronized ApiService create() {
        if (apiService != null) {
            return apiService;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(1, TimeUnit.MINUTES);
        builder.writeTimeout(1, TimeUnit.MINUTES);
        builder.connectTimeout(1, TimeUnit.MINUTES);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG_SETTING) {
            //包含header、body数据
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);

        }
        apiService = new Retrofit.Builder().baseUrl(BaseUrl)
                //添加gson适配器
                .addConverterFactory(GsonConverterFactory.create())
                //添加RxAndroid与Retrofit的适配器，将两者关联起来
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build()
                .create(ApiService.class);
        return apiService;
    }

    public static ApiService create(ProgressResponseListener progressResponseListener) {
        return new Retrofit.Builder().baseUrl(BaseUrl)
                //添加gson适配器
                .addConverterFactory(GsonConverterFactory.create())
                //添加RxAndroid与Retrofit的适配器，将两者关联起来
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HttpClientHelper.addProgressResponseListener(progressResponseListener))
                .build()
                .create(ApiService.class);
    }

    //RxJava2.x使用方式参考：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0907/6604.html
    public static <T> void sendHttp(final int what, Observable<T> observable, final CallBack callBack) {
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                callBack.setDisposable(d);
            }

            @Override
            public void onNext(@NonNull T t) {
                callBack.onSuccess(what, t);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                callBack.onError(what, e);
            }

            @Override
            public void onComplete() {
                callBack.onCompleted(what);
            }
        };
        observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    //异步请求之前的提示信息
                    callBack.loading(what);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static <T> Disposable request(Observable<T> observable, final Callback<T> callBack) {

        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        if (t instanceof Result) {
                            Result result = (Result) t;
                            if (result.code == Config.HTTP_SUCCESS) {
                                if (callBack != null) {
                                    callBack.result(false, t);
                                }
                            } else if (result.code == Config.HTTP_TOKEN_EXPIRED) {
                                NoticeObserver.getInstance().notifyObservers(Config.HTTP_TOKEN_EXPIRED);
                            } else if (result.code == Config.HTTP_USERKEY_EXPIRED) {
                                NoticeObserver.getInstance().notifyObservers(Config.HTTP_USERKEY_EXPIRED);
                            } else {
                                //view.requestError(what);
                                if (result.code == 201) {

                                } else {
                                    Log.v("", result.msg);
                                }
                            }
                        } else {
                            if (t instanceof JsonObject) {
                                callBack.result(false, t);
                            } else {
                                callBack.result(true, t);
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.v("", throwable.getMessage());
                        callBack.result(true, null);
                    }
                });
    }

    public interface Callback<T> {
        void result(boolean error, T result);
    }


}
