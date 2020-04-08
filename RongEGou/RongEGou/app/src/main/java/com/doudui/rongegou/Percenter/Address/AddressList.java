package com.doudui.rongegou.Percenter.Address;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.doudui.rongegou.HomePage.adapterdata;
import com.doudui.rongegou.HomePage.fenlei.fenlei_list;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
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

public class AddressList extends BaseActivity_ implements addressAdapter.itemoncl, detelAddress.detelsucc, addressAdapter.deteladdressit {

    @BindView(R.id.dh_imreturn)
    ImageView dhImreturn;
    @BindView(R.id.address_edit)
    EditText addressEdit;
    @BindView(R.id.address_tesearch)
    TextView addressTesearch;
    @BindView(R.id.address_recy)
    RecyclerView addressRecy;
    @BindView(R.id.address_smtr)
    SmartRefreshLayout addressSmtr;
    @BindView(R.id.addressit_teadd)
    TextView addressadd;

    addressAdapter addressAdapter;
    List<AddressData> list = new ArrayList<>();
    String user_id = "";
    String iscanoncl = "0";
    detelAddress detelAddress = new detelAddress();

    @Override
    protected void AddView() {
        user_id = getSharePre("user_id", this);
        iscanoncl = getIntent().getStringExtra("getlist");
        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        addressAdapter = new addressAdapter(this, list);
        addressRecy.setLayoutManager(layoutManager);
        addressRecy.setAdapter(addressAdapter);
        addressAdapter.setoncl(this);
        addressAdapter.setdetelonc(this);
        getdata_search();
    }

    @Override
    protected void SetViewListen() {
        addressadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddressList.this, AddAdress.class);
                i.putExtra("name", "添加地址");
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        addressTesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(addressEdit.getText().toString())) {
                    list.removeAll(list);
                    getdata_search();
                } else
                    toaste_ut(AddressList.this, "请输入内容");
            }
        });
        addressSmtr.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.removeAll(list);
                getdata_search();
                addressSmtr.finishRefresh();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address_list;
    }

    @OnClick({R.id.dh_imreturn, R.id.address_tesearch, R.id.address_smtr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dh_imreturn:
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
                break;
            case R.id.address_tesearch:

                break;
            case R.id.address_smtr:
                break;
        }
    }


    public void getdata_search() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "addr");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("text", addressEdit.getText().toString());
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SearchAddress");
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
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        list.removeAll(list);
                        JSONArray jsa_ = jsonObject.getJSONArray("Value");
                        for (int i = 0; i < jsa_.length(); i++) {
                            JSONObject jsosp = jsa_.getJSONObject(i);
                            list.add(new AddressData(jsosp.getString("address_id"), jsosp.getString("Receiver"), jsosp.getString("tel_phone"), jsosp.getString("areainfo"), jsosp.getString("address"), jsosp.getString("is_default")
                                    , jsosp.getString("idcardno"), jsosp.getString("areano"), iscanoncl));
                        }
                        addressAdapter.notifyDataSetChanged();

                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata_search();
                    } else
                        toaste_ut(AddressList.this, jsonObject.getString("Details"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt.isAdded()) jdt.dismiss();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.dizhilist)) {
                getdata_search();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void itemoncl(int pos) {
        if (iscanoncl.equals("1")) {
            AddressData data = list.get(pos);
            String str = data.getId() + "," + data.getName() + "," + data.getPho() + "," + data.getSfz() + ","
                    + data.getAddress() + "," + data.getAddressDetail() + "," + data.getAddressbhao();

            paramsDataBean databean = new paramsDataBean();
            databean.setMsg(configParams.getdiziitem);
            databean.setT(str);
            EventBus.getDefault().post(databean);
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
        } else if (iscanoncl.equals("2")) {
            AddressData data = list.get(pos);
            String str = data.getId() + "," + data.getName() + "," + data.getPho() + "," + data.getSfz() + ","
                    + data.getAddress() + "," + data.getAddressDetail() + "," + data.getAddressbhao();

            paramsDataBean databean = new paramsDataBean();
            databean.setMsg(configParams.getdiziitem1);
            databean.setT(str);
            EventBus.getDefault().post(databean);
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
        }
    }

    @Override
    public void detelsucc(int pos) {
        if (pos <= (list.size() - 1))
            list.remove(pos);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void deteladdressit(String id, String user_id, int pos) {
        if (!detelAddress.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putString("addressid", id);
            bundle.putString("user_id", user_id);
            bundle.putInt("pos", pos);
            detelAddress.setArguments(bundle);
            detelAddress.show(getFragmentManager(), "dete" + pos);
        }
    }
}
