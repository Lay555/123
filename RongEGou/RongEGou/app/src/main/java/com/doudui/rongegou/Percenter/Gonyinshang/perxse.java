package com.doudui.rongegou.Percenter.Gonyinshang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Percenter.Perziliao.ChangePho;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseTools.BaseFragment_;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class perxse extends BaseFragment_ {
    @BindView(R.id.prxse_terx)
    TextView prxseTerx;
    @BindView(R.id.prxse_teyx)
    TextView prxseTeyx;
    @BindView(R.id.prxse_teze)
    TextView prxseTeze;
    @BindView(R.id.prxse_tetdrx)
    TextView prxseTetdrx;
    @BindView(R.id.prxse_tetdyx)
    TextView prxseTetdyx;
    @BindView(R.id.prxse_tetdze)
    TextView prxseTetdze;
    @BindView(R.id.per_tepm)
    TextView perTepm;
    @BindView(R.id.perxse_tepm1)
    TextView perxseTepm1;
    @BindView(R.id.per_tetdpm)
    TextView perTetdpm;
    @BindView(R.id.per_tetdpm1)
    TextView perTetdpm1;
    @BindView(R.id.per_num)
    TextView te_num;
    @BindView(R.id.per_numtd)
    TextView te_numtd;

    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    String user_id = "";

    @Override
    protected void AddView() {
        user_id = getSharePre("user_id", getActivity());
        get_data();
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {

        return R.layout.perxse;
    }

    public void get_data() {
        final JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetXiaoshouGeren");
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
                    if (prxseTerx == null) {
                        return;
                    }
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONObject jso = jsonObject.getJSONObject("Value");
                        prxseTerx.setText(jso.getString("daysale") + "元");
                        prxseTeyx.setText(jso.getString("mouthsale") + "元");
                        prxseTeze.setText(jso.getString("myallsales") + "元");

                        prxseTetdrx.setText(jso.getString("allday") + "元");
                        prxseTetdyx.setText(jso.getString("allmonth") + "元");
                        prxseTetdze.setText(jso.getString("xiaallsales") + "元");

                        te_num.setText(jso.getString("daifahuo"));
                        te_numtd.setText(jso.getString("daifahuo_team"));
                        perTepm.setText(jso.getString("order"));
                        perxseTepm1.setText(jso.getString("upmonth_sale"));
                        perTetdpm.setText(jso.getString("orderdayu"));

                    } else if (jsonObject.getString("Status").equals("1002")) {
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
