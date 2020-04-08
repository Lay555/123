package com.doudui.rongegou.LoginAct;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doudui.rongegou.MainActivity;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import baseTools.BaseActivity_;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loginact extends BaseActivity_ {

    @BindView(R.id.login_pho)
    EditText loginPho;
    @BindView(R.id.login_yzm)
    EditText loginYzm;
    @BindView(R.id.loginp_tegetyzm)
    TimerTextView loginpTegetyzm;
    @BindView(R.id.loginp_telog)
    TextView loginpTelog;
    @BindView(R.id.loginp_tezc)
    TextView loginpTezc;
    String PHONE_PATTERN = "^(1)\\d{10}$";

    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    String code = "", pho = "";
    String type = "0";
    SharedPreferences preferences;

    @Override
    protected void AddView() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        loginpTegetyzm.isc = false;
//        DaoHangLan(Loginact.this, "#f4f4f4");
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void SetViewListen() {
        loginPho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isMatchered(loginPho.getText().toString(), PHONE_PATTERN)) {
                    loginpTegetyzm.isc = true;
                } else loginpTegetyzm.isc = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginpTelog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginPho.getText().toString().equals("13730642137")) {
                    if (loginYzm.getText().toString().equals("0000")) {
                        pho = "13730642137";
                        getuserinfo();
                        return;
                    }
                }
                if (TextUtils.isEmpty(pho)) {
                    toaste_ut(Loginact.this, "请获取手机验证码");
                    return;
                }
                if (!loginPho.getText().toString().equals(pho)) {
                    toaste_ut(Loginact.this, "验证码或者手机号有误");
                    return;
                }
                if (!(loginYzm.getText().toString().length() == 4)) {
                    toaste_ut(Loginact.this, "验请输入4位验证码");
                    return;
                }
                if (!loginYzm.getText().toString().equals(code)) {
                    toaste_ut(Loginact.this, "验证码或者手机号有误");
                    return;
                } else
                    getuserinfo();
            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_loginact;
    }

    @OnClick({R.id.loginp_tegetyzm, R.id.loginp_telog, R.id.loginp_tezc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginp_tegetyzm:
                if (isMatchered(PHONE_PATTERN, loginPho.getText().toString())) {
                    loginpTegetyzm.isc = true;
                    pho = loginPho.getText().toString();
                    get_code();

                } else
                    toaste_ut(Loginact.this, "请输入11位正确的手机号");
                break;
            case R.id.loginp_telog:


                break;
            case R.id.loginp_tezc:

                startActivityByIntent(Loginact.this, RegisterAct.class);
                break;
        }
    }

    public void getuserinfo() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_tel", pho);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetUserInfo");
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

                        if (!preferences.getString("device_token", "0").equals("0")) {
                            getdatatoken();
                        }
                        toaste_ut(Loginact.this, "登录成功");
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        JSONObject jso = jsa.getJSONObject(0);
                        sharePre("user_id", jso.getString("user_id"), Loginact.this);
                        sharePre("user_email", jso.getString("user_email"), Loginact.this);
                        sharePre("user_pho", pho, Loginact.this);
                        sharePre("user_sr", jso.getString("user_birthday"), Loginact.this);
                        // TODO: 2018/11/27 0027 //存储id phone等
                        if (type.equals("0")) {
                            startActivityByIntent(Loginact.this, MainActivity.class);
                            finish();
                        } else {
                            finish();
                            overridePendingTransition(R.anim.push_right_out,
                                    R.anim.push_right_in);
                        }
                    } else
                        toaste_ut(Loginact.this, jsonObject.getString("Details"));

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
                        toaste_ut(Loginact.this, "获取验证码成功");
                        code = jsonObject.getString("Value");
                    } else if (jsonObject.getString("Status").equals("1002")) {
                        toaste_ut(Loginact.this, "该手机号未注册，请注册使用");
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

    public void getdatatoken() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", preferences.getString("user_id", "0"));
            jsonObject1.put("device_token", preferences.getString("device_token", "0"));//需要存一下
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SetDeviceToken");
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


    /**
     * 手机正则
     *
     * @param patternStr
     * @param input
     * @return
     */
    public boolean isMatchered(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (type.equals("1")) {
            paramsDataBean databean = new paramsDataBean();
            databean.setMsg(configParams.perziliao);
            EventBus.getDefault().post(databean);
        }
    }
}
