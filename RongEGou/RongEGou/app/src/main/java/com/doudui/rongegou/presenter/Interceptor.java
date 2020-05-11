package com.doudui.rongegou.presenter;

/**
 * Created by asf on 2017/7/31.
 */

public interface Interceptor {
    public abstract <DATA> void intercept(int what, DATA data);
}
