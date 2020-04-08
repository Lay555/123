package com.doudui.rongegou.Percenter.zijinpac;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

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

public class zijinmingxi extends BaseActivity_ {


    @BindView(R.id.zijin_dh)
    DaoHang_top zijinDh;
    @BindView(R.id.zijin_recyc)
    RecyclerView zijinRecyc;
    @BindView(R.id.zijin_smtr)
    SmartRefreshLayout smrt;

    zijinada zijinada;
    List<zijindata> list = new ArrayList<>();
    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    int page = 1;
    String type = "0";

    @Override
    protected void AddView() {
        type = getIntent().getStringExtra("type");
        if (getIntent().getStringExtra("type").equals("0"))
            zijinDh.settext_("资金明细");
        else zijinDh.settext_("提现明细");
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        zijinada = new zijinada(this, list);
        zijinRecyc.setLayoutManager(manager);
        zijinRecyc.setAdapter(zijinada);
        smrt.setEnableRefresh(false);
        smrt.setEnableLoadmore(false);
        getdata();
    }

    @Override
    protected void SetViewListen() {
        smrt.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smrt.finishRefresh(500);
            }
        });
    }

    public void getdata() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", zijinmingxi.this));
            jsonObject.put("Data", jsonObject1);
            if (type.equals("0"))
            jsonObject.put("MethodName", "GetMoneyDetails");
            else
            jsonObject.put("MethodName", "GetTixianDetails");
            jsonObject.put("ModuleName", "UserAccount");
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
                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        for (int i = 0; i < jsa.length(); i++) {
                            JSONObject jso = jsa.getJSONObject(i);
                            if (type.equals("0"))
                            list.add(new zijindata(jso.getString("remark"), jso.getString("createTime"), jso.getString("amount"),""));
                            else
                            list.add(new zijindata(jso.getString("pdc_payment_name"), jso.getString("pdc_add_time"), jso.getString("pdc_amount"),jso.getString("pdc_pay_state")));
                        }
                        zijinada.notifyDataSetChanged();

                    } else {
                        toaste_ut(zijinmingxi.this, jsonObject.getString("Details"));
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
        return R.layout.activity_zijinmingxi;
    }
}
