package com.doudui.rongegou.Percenter.zijinpac;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseTools.BaseActivity_;
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

public class tixian extends BaseActivity_ {
    @BindView(R.id.ima_close)
    ImageView ima_close;
    @BindView(R.id.ed_num)
    EditText edNum;
    @BindView(R.id.ima_cha)
    ImageView imaCha;
    @BindView(R.id.te_txgz)
    TextView teTxgz;
    @BindView(R.id.pay_imawx)
    ImageView payImawx;
    @BindView(R.id.fk_linwx)
    LinearLayout fkLinwx;
    @BindView(R.id.pay_imaqb)
    ImageView payImaqb;
    @BindView(R.id.fk_linqb)
    LinearLayout fkLinqb;

    @BindView(R.id.te_zjmx)
    TextView te_txmx;
    @BindView(R.id.te_tx)
    TextView te_tx;
    @BindView(R.id.te_txcs)
    TextView te_cs;
    tixiancsdialog tixiancsdialog = new tixiancsdialog();

    @Override
    protected void AddView() {
        payImawx.setSelected(true);
    }

    @Override
    protected void SetViewListen() {
        ima_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
            }
        });
        te_txmx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(tixian.this, zijinmingxi.class);
            }
        });
        fkLinwx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payImawx.setSelected(true);
                payImaqb.setSelected(false);
            }
        });
        fkLinqb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payImawx.setSelected(false);
                payImaqb.setSelected(true);
            }
        });

        edNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(edNum.getText().toString())) {
                    imaCha.setVisibility(View.GONE);
                } else imaCha.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imaCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edNum.setText("");
            }
        });
        te_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fastClick()) {
                    if (TextUtils.isEmpty(edNum.getText().toString())) {
                        toaste_ut(tixian.this, "请输入提现金额");
                    } else {
                        if (payImawx.isSelected()) {
                            tx(edNum.getText().toString(), "微信");
                        } else tx(edNum.getText().toString(), "钱包");
                    }
                }
            }
        });

        te_txmx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(tixian.this, zijinmingxi.class);
                i.putExtra("type", "1");
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        teTxgz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tixiancsdialog.isAdded()) {
                    tixiancsdialog.show(getFragmentManager(), "txc");
                }
            }
        });
    }

    retro_intf serivce = retrofit_single.getInstence().getserivce(1);

    public void getdata() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", tixian.this));
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetKeTixian");
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
                    JSONObject JSO = jsonObject.getJSONObject("Value");
                    if (JSO.getString("money") != null) {
                        edNum.setText(JSO.getString("money"));
                        te_cs.setText("今日提现次数：" + JSO.getString("cishu"));
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

    public void tx(String money, String type) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        final String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", tixian.this));
            jsonObject1.put("money", money);
            jsonObject1.put("payment_name", type);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "Tixian");
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
                        toaste_ut(tixian.this, "提现成功");
                        finish();
                        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                    } else toaste_ut(tixian.this, jsonObject.getString("Details"));

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
        return R.layout.activity_tixian;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.grzxzl);
        EventBus.getDefault().post(databean);
    }
}
