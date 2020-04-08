package com.doudui.rongegou.HomePage.phb;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

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
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class paihangbang extends BaseActivity_ {

    @BindView(R.id.phb_dh)
    DaoHang_top phbDh;
    @BindView(R.id.phb_recy)
    RecyclerView phbRecy;

    phbada phbada;
    List<phbdata> list = new ArrayList<>();

    @Override
    protected void AddView() {
        phbDh.settext_("排行榜");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        phbada = new phbada(this, list);
        phbRecy.setAdapter(phbada);
        phbRecy.setLayoutManager(manager);
        get_data();
    }

    @Override
    protected void SetViewListen() {

    }

    retro_intf serivce = retrofit_single.getInstence().getserivce(1);

    public void get_data() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetGuiqiuPaihang");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());

        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
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
                            list.add(new phbdata(jso.getString("goods_conver"), jso.getString("goods_name"), "", jso.getString("ranks")));
                        }
                        phbada.notifyDataSetChanged();

                    } else if (jsonObject.getString("Status").equals("1002")) {
                        toaste_ut(paihangbang.this, "没有更多数据了");
                    }
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


    @Override
    protected int getLayout() {
        return R.layout.activity_paihangbang;
    }


}
