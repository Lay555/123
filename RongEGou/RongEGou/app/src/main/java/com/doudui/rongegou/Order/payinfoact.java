package com.doudui.rongegou.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.MainActivity;
import com.doudui.rongegou.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class payinfoact extends BaseActivity_ {

    @BindView(R.id.duizhang_dh)
    DaoHang_top duizhangDh;
    @BindView(R.id.duizhang_teprice)
    TextView duizhangTeprice;
    @BindView(R.id.duizhang_reje)
    RelativeLayout duizhangReje;
    @BindView(R.id.duizhang_tetme)
    TextView duizhangTetme;
    @BindView(R.id.duizhang_tepay)
    TextView duizhangTepay;
    @BindView(R.id.duizhang_teprice1)
    TextView duizhangTeprice1;
    @BindView(R.id.duizhang_teyunfei)
    TextView duizhangTeyunfei;
    @BindView(R.id.duizhang_tenum)
    TextView duizhangTenum;


    @Override
    protected void AddView() {
        duizhangDh.settext_("每日对账");
        getdatatoken();
    }

    @Override
    protected void SetViewListen() {
        duizhangTepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(payinfoact.this, order.class);
                i.putExtra("pos", 2);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                finish();
            }
        });
        duizhangDh.ima_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(payinfoact.this, MainActivity.class);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payinfoact;
    }


    public void getdatatoken() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", payinfoact.this));
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetDailyStatistic");
            jsonObject.put("ModuleName", "UserAccount");
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
//                    System.out.println(jsonObject.toString());
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONObject jso = jsonObject.getJSONObject("Value");
                        duizhangTeprice.setText(jso.getString("yingfu"));
                        duizhangTetme.setText(jso.getString("datetime"));//
                        duizhangTeprice1.setText(jso.getString("goods_amount"));
                        duizhangTeyunfei.setText(jso.getString("trans_amount"));
                        duizhangTenum.setText(jso.getString("gongji"));
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdatatoken();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            startActivityByIntent(payinfoact.this, MainActivity.class);
            finish();
            return false;
        }
        return false;
    }


}
