package com.doudui.rongegou.presenter.presenter;


import android.content.Context;

import com.doudui.rongegou.presenter.contract.BaseContract;

import java.util.Map;

/**
 * Created by Administrator on 2020/5/9.
 */

public class HttpPresenter extends BasePresenter {
    private static final String TAG = HttpPresenter.class.getSimpleName();

    public HttpPresenter(Context context, BaseContract.View view) {
        super(context, view);
    }


    /**
     * 用于提交
     */
    @Override
    public void start(int what, Map<String, Object> param) {
    }

    /**
     *请求带参数
     */
    @Override
    public void start(int what, String... strings) {
    }


}