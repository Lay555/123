package com.doudui.rongegou.model.bean;

/**
 * Created by Administrator on 2020/5/9.
 * 网络请求返回的结果
 */
public final class Result<T> {
    public int code;
    public String msg;
    public T data;
}
