package com.doudui.rongegou.presenter.contract;

import com.doudui.rongegou.presenter.BaseView;
import com.doudui.rongegou.presenter.BasePresenter;

/**
 * Created by Administrator on 2020/5/9.
 */
public interface BaseContract {
    interface View extends BaseView<Presenter> {
        void loading(int what);

        void dissLoading();

        <T> void requestSuccess(int code, T t);

        void requestError(int code, Throwable e);
    }

    interface Presenter extends BasePresenter {
    }
}
