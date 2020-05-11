package com.doudui.rongegou.model;

/**
 * Created by Administrator on 2020/5/9.
 * 响应体进度回调接口，用于文件下载进度回调
 */


public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
