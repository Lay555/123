package com.doudui.rongegou.model;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/5/9.
 */

public class HttpClientHelper {
    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressResponseListener(final ProgressResponseListener progressListener) {
        OkHttpClient.Builder client = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000, TimeUnit.MILLISECONDS);
        //增加拦截器
        client.addInterceptor(chain -> {
            //拦截
            Response originalResponse = chain.proceed(chain.request());
            //包装响应体并返回
            return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
        });
        return client.build();
    }

}
