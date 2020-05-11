package com.doudui.rongegou.model;

import com.doudui.rongegou.model.bean.Result;

import java.util.Observable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2020/5/9.
 * 请求的路径
 */

public interface ApiService {
    /**
     * @param requestBody
     * @return
     */
    @POST("/Client.aspx")
    Call<ResponseBody> getData(@Body RequestBody requestBody);
    /*@POST("/Client.aspx")
    Observable<Result<String>> getData()*/

}
