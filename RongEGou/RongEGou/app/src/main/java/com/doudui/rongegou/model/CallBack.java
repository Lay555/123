package com.doudui.rongegou.model;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2020/5/9.
 */
public interface CallBack {

    //请求成功
    <T> void onSuccess(int what, T t);

    //请求失败
    void onError(int what, Throwable e);

    //请求取消，与onError只能二存一
    void onCompleted(int what);

    void loading(int what);

     void setDisposable(@NonNull Disposable d);
}
