package com.doudui.rongegou.Percenter.Perziliao;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.LoginAct.AesUtil;
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
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editwxact extends BaseActivity_ {

    @BindView(R.id.dh_)
    DaoHang_top dh;
    @BindView(R.id.xiugai_edname)
    EditText xiugaiEdname;
    @BindView(R.id.addaddte_teadd)
    TextView addaddteTeadd;
    String user_id = "";

    @Override
    protected void AddView() {
        dh.settext_("修改微信号");
        user_id = getSharePre("user_id", this);
        xiugaiEdname.setText(getSharePre("userwx", editwxact.this));
    }

    @Override
    protected void SetViewListen() {
        addaddteTeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(xiugaiEdname.getText().toString())) {
                    toaste_ut(editwxact.this, "微信号不能为空");
                    return;
                }
                addaddteTeadd.setEnabled(false);
                getdata_banding(user_id, xiugaiEdname.getText().toString());
                addaddteTeadd.setEnabled(true);
            }
        });

    }

    public void getdata_banding(final String user_id, String wxh) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("wechat", wxh);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "EditWechat");
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
                        toaste_ut(editwxact.this, "绑定成功");
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.perziliao);
                        EventBus.getDefault().post(databean);
                        finish();
                        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                    } else {
                        Toast.makeText(editwxact.this, jsonObject.getString("Details"), Toast.LENGTH_SHORT).show();
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
        return R.layout.activity_editwxact;
    }
}
