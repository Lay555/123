package com.doudui.rongegou.HomePage.images;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class images extends BaseActivity_ {

    @BindView(R.id.imgs_dh)
    DaoHang_top imgsDh;
    @BindView(R.id.imgs_recy)
    RecyclerView imgsRecy;
    @BindView(R.id.imgs_smtr)
    SmartRefreshLayout imgsSmtr;
    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    int page = 1,page1=1;

    List<imagesdata> list = new ArrayList<>();
    imagesada imagesada;

    @Override
    protected void AddView() {
        imgsDh.settext_("买家秀");

        imgsDh.setFocusableInTouchMode(true);
        imgsDh.setFocusable(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        imagesada = new imagesada(this, list);

        ((DefaultItemAnimator) imgsRecy.getItemAnimator()).setSupportsChangeAnimations(false);//解决glide4.0刷新数据闪烁bug3步  bu1
        imagesada.setHasStableIds(true);//bu2
        imgsRecy.setLayoutManager(staggeredGridLayoutManager);
        imgsRecy.setAdapter(imagesada);
        getuserinfo();

    }

    @Override
    protected void SetViewListen() {
        imgsSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getuserinfo();
                imgsSmtr.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                list.removeAll(list);
                getuserinfo();
                imgsSmtr.finishRefresh();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_images;
    }

    public void getuserinfo() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("page", page);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetCustomerShow");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());

        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        for (int i = 0; i < jsa.length(); i++) {
                            JSONObject jso = jsa.getJSONObject(i);
                            String txt = jso.getString("goods_id");
                            list.add(new imagesdata(jso.getString("ads_pic"), txt));
                        }
                        imagesada.notifyDataSetChanged();
                    } else
                        toaste_ut(images.this, jsonObject.getString("Details"));

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
