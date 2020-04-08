package com.doudui.rongegou.Percenter.Perziliao;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.LoginAct.Loginact;
import com.doudui.rongegou.LoginAct.TimerTextView;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePho extends BaseActivity_ {

    @BindView(R.id.changepho_dh)
    DaoHang_top changephoDh;
    @BindView(R.id.changepho_tepho)
    TextView changephoTepho;
    @BindView(R.id.changepho_edpho)
    EditText changephoEdpho;
    @BindView(R.id.changepho_edyzm)
    EditText changephoEdyzm;
    @BindView(R.id.changepho_timerte)
    TimerTextView changephoTimerte;
    @BindView(R.id.changepho_teok)
    TextView changephoTeok;
    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    String code = "", pho = "", user_id = "";

    @Override
    protected void AddView() {
        changephoDh.settext_("更换手机号");
        changephoTepho.setText(getSharePre("user_pho", this));
        user_id = getSharePre("user_id", this);
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_change_pho;
    }

    @OnClick({R.id.changepho_timerte, R.id.changepho_teok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.changepho_timerte:
                if (changephoEdpho.getText().toString().length() < 11) {
                    toaste_ut(ChangePho.this, "请输入11位新手机号");
                } else {
                    changephoTimerte.isc = true;
                    get_code();
                }
                break;
            case R.id.changepho_teok:
                if (changephoEdyzm.getText().toString().length() < 4) {
                    toaste_ut(ChangePho.this, "请输入4位验证码");
                } else {
                    if (!changephoEdpho.getText().toString().equals("pho")) {
                        get_data();
                    } else {
                        toaste_ut(ChangePho.this, "新手机号与验证码不匹配");
                    }
                }
                break;
        }
    }

    public void get_data() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("user_tel", pho);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "EditUsertel");
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
                    if (jsonObject.getString("Status").equals("1")) {
                        // TODO: 2018/11/27 0027  重新调用获取用户信息
                        sharePre("user_pho", pho, ChangePho.this);
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.perziliao);
                        EventBus.getDefault().post(databean);
                        finish();
                        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);

                    } else if (jsonObject.getString("Status").equals("1002")) {
                        toaste_ut(ChangePho.this, "更换失败");
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

    public void get_code() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_tel", pho);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetCode");
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
                    if (jsonObject.getString("Status").equals("1")) {
                        code = jsonObject.getString("Value");
                        pho = changephoEdpho.getText().toString();

                    } else if (jsonObject.getString("Status").equals("1002")) {
                        toaste_ut(ChangePho.this, "验证码发送失败");
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

}
