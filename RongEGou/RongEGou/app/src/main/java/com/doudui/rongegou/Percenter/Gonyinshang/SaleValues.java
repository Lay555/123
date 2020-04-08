package com.doudui.rongegou.Percenter.Gonyinshang;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Percenter.SaleVaAdapter;
import com.doudui.rongegou.Percenter.SaleVaData;
import com.doudui.rongegou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseFragment_;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleValues extends BaseFragment_ {

    @BindView(R.id.salevalue_Teday)
    TextView salevalueTeday;
    @BindView(R.id.salevalue_Tename)
    TextView salevalueTename;
    @BindView(R.id.salevalue_recy)
    RecyclerView salevalueRecy;

    SaleVaAdapter ada;
    List<SaleVaData> list = new ArrayList<>();
    @BindView(R.id.salevalue_Tesz)
    TextView salevalueTesz;
    @BindView(R.id.salevalue_Temon)
    TextView salevalueTemon;

    @BindView(R.id.salevalue_novalue)
    LinearLayout layoutnovalue;
    @BindView(R.id.salevalue_linvalue)
    LinearLayout lin_novalue;

    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    String user_id = "";

    @Override
    protected void AddView() {
        ada = new SaleVaAdapter(getActivity(), list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        salevalueRecy.setLayoutManager(layoutManager);
        salevalueRecy.setAdapter(ada);

        user_id = getSharePre("user_id", getActivity());

        get_data();

    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sale_values;
    }

    public void get_data() {
        final JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetXiaoshouTuandui");
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
                    if (lin_novalue == null) {
                        return;
                    }
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        if (jsa.length() >= 1) {
                            lin_novalue.setVisibility(View.VISIBLE);
                            layoutnovalue.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < jsa.length(); i++) {
                            JSONObject jso = jsa.getJSONObject(i);
                            if (i == 0) {
                                salevalueTename.setText(jso.getString("user_name"));
                                salevalueTesz.setText(jso.getString("user_email"));
                                salevalueTeday.setText(jso.getString("day_sale"));
                                salevalueTemon.setText(jso.getString("mouth_sale"));
                            }
                            list.add(new SaleVaData(jso.getString("user_name"), jso.getString("user_email"), jso.getString("day_sale"), jso.getString("mouth_sale")));
                        }
                        ada.notifyDataSetChanged();
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
