package com.doudui.rongegou.presenter.presenter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;


import com.doudui.rongegou.Config;
import com.doudui.rongegou.manager.NoticeObserver;
import com.doudui.rongegou.model.CallBack;
import com.doudui.rongegou.model.ProgressResponseListener;
import com.doudui.rongegou.model.bean.ErrorBean;
import com.doudui.rongegou.model.bean.Result;
import com.doudui.rongegou.presenter.Interceptor;
import com.doudui.rongegou.presenter.contract.BaseContract;
import com.doudui.rongegou.util.GsonUtils;
import com.doudui.rongegou.util.LogHelper;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2020/5/9.
 */

public abstract class BasePresenter implements CallBack, BaseContract.Presenter {

    private static final String TAG = BasePresenter.class.getSimpleName();
    protected Observable observer;
    private BaseContract.View view;
    protected Context context;
    private Disposable disposable;
    private Interceptor interceptor;

    protected ProgressResponseListener progressResponseListener;

    public BasePresenter(Context context, BaseContract.View view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public <T> void onSuccess(int what, T t) {
        if (interceptor != null) {
            interceptor.intercept(what, t);
        }
        LogHelper.w(TAG, "~~请求成功--->" + GsonUtils.beanToJson(t));
        if (t instanceof Result) {
            Result result = (Result) t;
            if (result.code == Config.HTTP_SUCCESS) {
                if (view != null) {
                    if (what == Config.PRINT_AGAIN || what == Config.PRINT_LOOK) {
                        view.requestSuccess(what, result);
                    } else {
                        view.requestSuccess(what, result.data);
                    }

                }
            } else if (result.code == Config.HTTP_TOKEN_EXPIRED) {
                NoticeObserver.getInstance().notifyObservers(Config.HTTP_TOKEN_EXPIRED);
            } else if (result.code == Config.HTTP_USERKEY_EXPIRED) {
                NoticeObserver.getInstance().notifyObservers(Config.HTTP_USERKEY_EXPIRED);
            } else {
                //view.requestError(what);
                if (interceptor == null) {
                    if (result.code == 201) {
                        if (context != null) {

                        }
                    } else {
                        if (context != null) {
                            Toast.makeText(context,result.msg,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } else {
            if (view != null) {
                view.requestSuccess(what, t);
            }
        }
    }

    @Override
    public void onError(int what, Throwable e) {
        ErrorBean mErrorBean = GsonUtils.jsonToBean(GsonUtils.beanToJson(e), ErrorBean.class);
        LogHelper.e(TAG, what + " ~~请求失败 -1-->" + GsonUtils.beanToJson(e));
        LogHelper.e(TAG, what + " ~~请求失败 -2-->" + GsonUtils.beanToJson(mErrorBean));
        if (mErrorBean.getCode() == Config.HTTP_TOKEN_EXPIRED || mErrorBean.getO000000o() == Config.HTTP_TOKEN_EXPIRED) {
            NoticeObserver.getInstance().notifyObservers(Config.HTTP_TOKEN_EXPIRED);
        } else if (mErrorBean.getCode() == Config.HTTP_USERKEY_EXPIRED || mErrorBean.getO000000o() == Config.HTTP_USERKEY_EXPIRED) {
            NoticeObserver.getInstance().notifyObservers(Config.HTTP_USERKEY_EXPIRED);
        } else {
            if (view != null) {
                view.requestError(what, e);
                view.dissLoading();
            }
        }
    }

    @Override
    public void onCompleted(int what) {
        if (view != null) {
            view.dissLoading();
        }
    }

    @Override
    public void loading(int what) {
        LogHelper.i(TAG, "加载中...");
        if (view != null) {
            view.loading(what);
        }
    }

    @Override
    public void setDisposable(@NonNull Disposable d) {
        this.disposable = d;
    }

    @Override
    public void unSubscriber() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
            view = null;
        }
    }

    //开始进行网络请求
    public abstract void start(int what, String... strings);

    public void start(int what, ArrayMap<String, String> arrayMap) {
    }

    public void start(int what, Map<String, Object> param) {
    }

    public void setResponseBody(ProgressResponseListener progressResponseListener) {
        this.progressResponseListener = progressResponseListener;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }
}
