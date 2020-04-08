package com.doudui.rongegou.ApplayOrder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.Goods.GoodsDetails;
import com.doudui.rongegou.Goods.guige.sp_ggada;
import com.doudui.rongegou.Goods.guige.sp_ggadadadata;
import com.doudui.rongegou.Goods.guige.sp_ggadadadata1;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Percenter.Address.AddressData;
import com.doudui.rongegou.Percenter.Address.AddressList;
import com.doudui.rongegou.Percenter.Address.quyussq;
import com.doudui.rongegou.R;

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

public class xiadan extends BaseActivity_ implements sp_ggada.xxshua, spnameadapter.iterm_, quyussq.setSSq, shradapter.chooseshr {

    @BindView(R.id.xiadan_dh)
    DaoHang_top xiadanDh;
    @BindView(R.id.xiadan_edbh)
    EditText xiadanEdbh;
    @BindView(R.id.xiadan_tebh)
    TextView xiadanTebh;
    @BindView(R.id.xiadan_linbh)
    LinearLayout xiadanLinbh;
    @BindView(R.id.xiadan_spname)
    TextView xiadanSpname;
    @BindView(R.id.xiadan_recycguige)
    RecyclerView xiadanRecycguige;
    @BindView(R.id.xiadantekucun)
    TextView xiadantekucun;
    @BindView(R.id.xiadan_jian)
    TextView xiadanJian;
    @BindView(R.id.xiadan_num)
    EditText xiadanNum;
    @BindView(R.id.xiadan_jia)
    TextView xiadanJia;
    @BindView(R.id.xiadan_tefuli)
    TextView xiadanTefuli;
    @BindView(R.id.xiadan_tedanprice)
    TextView xiadanTedanprice;

    @BindView(R.id.xiadan_regg)
    LinearLayout xiadanRegg;
    @BindView(R.id.xiadan_kb)
    View xiadanKb;
    @BindView(R.id.xiadan_kdf)
    TextView xiadanKdf;
    @BindView(R.id.xiadan_ddprice)
    TextView xiadanDdprice;
    @BindView(R.id.xiadan_edshr)
    EditText xiadanEdshr;
    @BindView(R.id.xiadan_teshr)
    TextView xiadanTeshr;
    @BindView(R.id.xiadan_edpho)
    EditText xiadanEdpho;
    @BindView(R.id.xiadan_edsfz)
    EditText xiadanEdsfz;
    @BindView(R.id.xiadan_linssq)
    LinearLayout xiadanLinssq;
    @BindView(R.id.xiadan_tessq)
    TextView te_ssq;

    @BindView(R.id.xiadan_imssq)
    ImageView im_ssq;

    @BindView(R.id.xiadan_edaddress)
    EditText xiadanEdaddress;
    @BindView(R.id.xiadan_edliuyan)
    EditText xiadanEdliuyan;
    @BindView(R.id.xiadan_recyc)
    RecyclerView xiadanRecyc;
    @BindView(R.id.addaddte_teadd)
    TextView addaddteTeadd;

    @BindView(R.id.xiadan_linbf)
    LinearLayout lin_bf;

    @BindView(R.id.xiadan_recycshr)
    RecyclerView recyc_shr;
    @BindView(R.id.xiadan_imscshr)
    ImageView imclose_shr;

    sp_ggada sp_ggada;
    List<sp_ggadadadata> list_gg = new ArrayList<>();

    String user_id = "", skuid = "", buycount = "", addressid = "0", shrname = "", shrpho = "", quyubianma = "", quyudizhi = "", xiangxidizhi = "", remark = "",
            quyubianma1 = "", goodsid = "", spec_ids = "", isyugou = "";
    int maxnum = 1, num = 1;
    querenorder querenorder = new querenorder();

    quyussq ssq = new quyussq();


    shradapter adashr;
    List<AddressData> listshr = new ArrayList<>();
    String typeshr = "0";
    boolean can_order = false;

    @Override
    protected void AddView() {
        xiadanDh.setFocusable(true);
        xiadanDh.setFocusableInTouchMode(true);
        EventBus.getDefault().register(this);
        xiadanDh.settext_("填写下单");
//        xiadanDh.setvis();
        LinearLayoutManager layoutManager = new LinearLayoutManager(xiadan.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        user_id = getSharePre("user_id", xiadan.this);
        Intent i = getIntent();
        skuid = i.getStringExtra("skuid");
        goodsid = i.getStringExtra("goodsid");
        num = i.getIntExtra("num", 1);
        if (i.getStringExtra("isyugou") != null) {
            isyugou = i.getStringExtra("isyugou");
        }
        LinearLayoutManager manager = new LinearLayoutManager(xiadan.this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        xiadanRecycguige.setLayoutManager(manager);
        sp_ggada = new sp_ggada(list_gg, xiadan.this);
        xiadanRecycguige.setAdapter(sp_ggada);
        sp_ggada.setshaxin(this);

        xiadanSpname.setText(i.getStringExtra("name"));
        xiadanEdbh.setText(i.getStringExtra("name"));
        xiadanEdbh.setEnabled(false);
        xiadanTebh.setEnabled(false);
        xiadanNum.setText(num + "");

        getdata_chooseskuid(skuid);

        adashr = new shradapter(xiadan.this, listshr);
        LinearLayoutManager managershr = new LinearLayoutManager(xiadan.this);
        managershr.setOrientation(OrientationHelper.VERTICAL);
        recyc_shr.setLayoutManager(managershr);
        recyc_shr.setAdapter(adashr);
        adashr.setshronc(this);
    }

    @Override
    protected void SetViewListen() {
        xiadanTebh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(xiadan.this, GoodsDetails.class);
                i.putExtra("goodsid", goodsid);
                startActivity(i);
                xiadan.this.overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
            }
        });

        xiadanTeshr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(xiadan.this, AddressList.class);
                i.putExtra("getlist", "2");
                startActivity(i);
                xiadan.this.overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });


        te_ssq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ssq.isAdded())
                    ssq.show(getFragmentManager(), "qy11");
            }
        });

        im_ssq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ssq.isAdded())
                    ssq.show(getFragmentManager(), "qy11");
            }
        });


        xiadanJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isyugou.equals("pre_purchase"))//pre_purchase预购商品
                {
                    num++;
                    xiadanNum.setText(num + "");
                    if (!TextUtils.isEmpty(quyubianma))
                        getdata_getfei(skuid, num + "", quyubianma1);
                } else {
                    if (num < maxnum) {
                        num++;
                        xiadanNum.setText(num + "");
                        if (!TextUtils.isEmpty(quyubianma))
                            getdata_getfei(skuid, num + "", quyubianma1);
                    }
                }
            }
        });
        xiadanJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num >= 2) {
                    num--;
                    xiadanNum.setText(num + "");
                    //计算价格
                    if (!TextUtils.isEmpty(quyubianma))
                        getdata_getfei(skuid, num + "", quyubianma1);
                }
            }
        });
        xiadanNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(xiadanNum.getText().toString())) {

                    if (maxnum == 0) {
                        xiadanNum.setText("0");
                        num = 0;
                    } else {
                        xiadanNum.setText("1");
                        num = 1;
                    }
                } else {
                    int num_ = Integer.valueOf(xiadanNum.getText().toString()).intValue();
//                    if (num_ > maxnum) {
//                        num_ = maxnum;
//                        xiadanNum.setText(num_+"");
//                    }
                    if (num_ >= 999) {
                        num_ = 999;
                        num = num_;
                        xiadanNum.setText(num_ + "");
                    } else {
                        num = num_;
                    }
                }
                if (!TextUtils.isEmpty(quyubianma))
                    if (num != 0)
                        getdata_getfei(skuid, num + "", quyubianma1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addaddteTeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (skuid.length() <= 0) {
                    toaste_ut(xiadan.this, "请选择产品");
                    return;
                }
                if (TextUtils.isEmpty(xiadanEdshr.getText().toString()) || TextUtils.isEmpty(te_ssq.getText().toString()) || TextUtils.isEmpty(xiadanEdaddress.getText().toString())) {
                    toaste_ut(xiadan.this, "请完成选择地址内容");
                    return;
                }
                if (fastClick()) {
                    if (can_order) {
                        System.out.println(isyugou);
                        if (isyugou.equals("pre_purchase"))//pre_purchase预购商品
                            getdata_xiadanyugou();
                        else
                            getdata_xiadan();
                    } else toaste_ut(xiadan.this, "该地区暂不支持配送");
                }
            }
        });
        xiadanEdshr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (typeshr.equals("0"))
                    if (xiadanEdshr.getText().toString().length() >= 1) {
                        getdata_search(xiadanEdshr.getText().toString());

                    } else recyc_shr.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imclose_shr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeshr = "0";
                imclose_shr.setVisibility(View.GONE);
                if (fastClick()) {
                    xiadanEdshr.setText("");
                    xiadanEdsfz.setText("");
                    xiadanEdpho.setText("");
                    xiadanEdaddress.setText("");
                    xiadanKdf.setText("");
                    xiadanDdprice.setText("");

                    te_ssq.setText("所在地区");
                    addressid = "0";
                    shrpho = "";
                    xiadanEdshr.setEnabled(true);
                    xiadanEdsfz.setEnabled(true);
                    xiadanEdpho.setEnabled(true);
                    xiadanEdaddress.setEnabled(true);
//                    xiadanLinssq.setEnabled(true);
                    te_ssq.setEnabled(true);
                    im_ssq.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_xiadan;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.getdiziitem1)) {
                String str = (String) data.getT();

                imclose_shr.setVisibility(View.VISIBLE);
                typeshr = "1";

                xiadanEdshr.setEnabled(false);
                xiadanEdsfz.setEnabled(false);
                xiadanEdpho.setEnabled(false);
                xiadanEdaddress.setEnabled(false);

//                xiadanLinssq.setEnabled(false);
                te_ssq.setEnabled(false);
                im_ssq.setEnabled(false);

                String[] datastr = str.split(",");
                xiadanEdshr.setText(datastr[1]);
                xiadanEdpho.setText(datastr[2]);
                xiadanEdsfz.setText(datastr[3]);
                te_ssq.setText(datastr[4]);
                xiadanEdaddress.setText(datastr[5]);
                quyubianma = datastr[6];
                quyubianma1 = datastr[6];
                addressid = datastr[0];
                if (!TextUtils.isEmpty(quyubianma))
                    getdata_getfei(skuid, num + "", quyubianma1);

                return;
            } else if (data.getMsg().equals(configParams.syxdcg1)) {
                finish();
                overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
                return;
            }
        }
    }


    public void getdata_chooseskuid(final String skuid) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("skuid", skuid);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetSkuInfo");
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
                        JSONObject jso = jsonObject.getJSONObject("Value");
                        JSONArray jsa1 = jso.getJSONArray("allspecs");
                        JSONArray jsa2 = jso.getJSONArray("selected_specs");

                        list_gg.removeAll(list_gg);
                        sp_ggada.notifyDataSetChanged();
                        for (int i = 0; i < jsa1.length(); i++) {
                            JSONObject jso1 = jsa1.getJSONObject(i);
                            JSONArray jsa_zi = jso1.getJSONArray("values");
                            List<sp_ggadadadata1> list1 = new ArrayList<>();
                            for (int j = 0; j < jsa_zi.length(); j++) {
                                JSONObject jso_zi = jsa_zi.getJSONObject(j);
                                if (jsa2.getString(i).equals(jso_zi.getString("spec_value_id")))
                                    list1.add(new sp_ggadadadata1(true, jso_zi.getString("spec_value_id"), jso_zi.getString("spec_value_name"), "1"));
                                else
                                    list1.add(new sp_ggadadadata1(false, jso_zi.getString("spec_value_id"), jso_zi.getString("spec_value_name"), "1"));
                            }
                            list_gg.add(new sp_ggadadadata(jso1.getString("spec_id"), jso1.getString("spec_name"), list1));
                        }
                        sp_ggada.notifyDataSetChanged();

                        xiadantekucun.setText("（库存" + jso.getString("stock") + "）");
                        maxnum = Integer.valueOf(jso.getString("stock")).intValue();

                        if (!isyugou.equals("pre_purchase"))
                            if (maxnum == 0) {
                                num = 0;
                                xiadanNum.setText(num + "");
                            }
                        xiadanTefuli.setText("(福利价:￥" + jso.getString("market_price") + ")");
                        xiadanTedanprice.setText("销售价:￥" + jso.getString("store_price"));
                        if (!TextUtils.isEmpty(quyubianma))
                            if (!TextUtils.isEmpty(skuid))
                                getdata_getfei(skuid, num + "", quyubianma1);

                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata_chooseskuid(skuid);
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

    public void getdata_getfei(final String skuid, String num, String ssqbianhao) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("skuid", skuid);
            jsonObject1.put("provinceCode", ssqbianhao);
            jsonObject1.put("number", num);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetExpressMoney");
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
                        can_order = true;
                        JSONObject jso = jsonObject.getJSONObject("Value");
                        xiadanKdf.setText("￥" + jso.getString("exp_money"));
                        xiadanDdprice.setText("￥" + jso.getString("finalAmount"));

                    } else {
                        can_order = false;
                        toaste_ut(xiadan.this, jsonObject.getString("Details"));
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

    public void getdata_xiadan() {

        if (jdt != null)
            if (!jdt.isAdded()) {
                jdt.show(getFragmentManager(), "xdd");
            }

        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("skuid", skuid);
            jsonObject1.put("buyCount", num);
            jsonObject1.put("platform", "ANDROID");
            jsonObject1.put("addressId", addressid);
            jsonObject1.put("addressBuyName", xiadanEdshr.getText().toString());
            jsonObject1.put("addressTel", xiadanEdpho.getText().toString());
            jsonObject1.put("addressIdcard", xiadanEdsfz.getText().toString());
            jsonObject1.put("addressAreaNo", quyubianma);
            jsonObject1.put("addressAreaInfo", te_ssq.getText().toString());
            jsonObject1.put("addressDetail", xiadanEdaddress.getText().toString());
            jsonObject1.put("remark", xiadanEdliuyan.getText().toString());
            jsonObject1.put("sfz", xiadanEdsfz.getText().toString());

            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SubOrder");
            jsonObject.put("ModuleName", "OrderManage");
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
                        String orderid = jsonObject.getJSONObject("Value").getString("orderId");
                        if (!TextUtils.isEmpty(orderid)) {
                            getdata_searchnumber(orderid);
                        }

                    } else {
                        if (jdt != null)
                            if (jdt.isAdded()) {
                                jdt.dismiss();
                            }

                        toaste_ut(xiadan.this, jsonObject.getString("Details"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt != null)
                    if (jdt.isAdded()) {
                        jdt.dismiss();
                    }
            }
        });
    }

    public void getdata_xiadanyugou() {

        if (jdt != null)
            if (!jdt.isAdded()) {
                jdt.show(getFragmentManager(), "xdd");
            }

        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("skuid", skuid);
            jsonObject1.put("buyCount", num);
            jsonObject1.put("platform", "ANDROID");
            jsonObject1.put("addressId", addressid);
            jsonObject1.put("addressBuyName", xiadanEdshr.getText().toString());
            jsonObject1.put("addressTel", xiadanEdpho.getText().toString());
            jsonObject1.put("addressIdcard", xiadanEdsfz.getText().toString());
            jsonObject1.put("addressAreaNo", quyubianma);
            jsonObject1.put("addressAreaInfo", te_ssq.getText().toString());
            jsonObject1.put("addressDetail", xiadanEdaddress.getText().toString());
            jsonObject1.put("remark", xiadanEdliuyan.getText().toString());
            jsonObject1.put("sfz", xiadanEdsfz.getText().toString());

            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "PreSubOrder");
            jsonObject.put("ModuleName", "OrderManage");
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

                if (jdt != null)
                    if (jdt.isAdded()) {
                        jdt.dismiss();
                    }

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getString("Status").equals("1")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "lm");
                        querenorder.setArguments(bundle);
                        if (!querenorder.isAdded()) {
                            querenorder.show(getSupportFragmentManager(), "lm");
                        }
                    } else {
                        toaste_ut(xiadan.this, jsonObject.getString("Details"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (jdt != null)
                    if (jdt.isAdded()) {
                        jdt.dismiss();
                    }
            }
        });
    }


    @Override
    public void iterm_(String skuid, String name, String goodsidx) {
        xiadanEdbh.setText(skuid);
        xiadanSpname.setText(name);
        xiadanRecyc.setVisibility(View.GONE);
        getdata_chooseskuid(skuid);
        this.skuid = skuid;
        goodsid = goodsidx;
        if (!TextUtils.isEmpty(quyubianma))
            getdata_getfei(skuid, num + "", quyubianma);
    }

    @Override
    public void setSSq(String ids, String ssqstr) {
        String[] datastr = ids.split(",");
        te_ssq.setText(ssqstr);
        quyubianma = datastr[2];
        quyubianma1 = datastr[2];
        if (!TextUtils.isEmpty(quyubianma))
            getdata_getfei(skuid, num + "", quyubianma1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void shuaxin() {
        spec_ids = "";
        for (int i = 0; i < list_gg.size(); i++) {

            for (int j = 0; j < list_gg.get(i).getList().size(); j++) {
                if (list_gg.get(i).getList().get(j).isT_sele()) {
                    spec_ids += list_gg.get(i).getList().get(j).getId() + ",";
                    break;
                }
            }
        }
        if (spec_ids.contains(",")) {
            spec_ids = spec_ids.substring(0, spec_ids.length() - 1);
        }
        String[] id = spec_ids.split(",");
        if (id.length == list_gg.size()) {
            num = 1;
            xiadanNum.setText("1");
            getdata_gg();
        }
    }

    public void getdata_gg() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("goods_id", goodsid);
            jsonObject1.put("spec_ids", spec_ids);
            jsonObject1.put("goods_num", num);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetGoodsSku");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
//            System.out.println(jsonObject.toString());
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
                    JSONArray jsa_ = jsonObject.getJSONArray("Value");
                    JSONObject jso = jsa_.getJSONObject(0);
                    maxnum = Integer.valueOf(jso.getString("stock")).intValue();
                    skuid = jso.getString("skuid");
                    if (maxnum == 0) {
                        num = 0;
                        xiadanNum.setText("0");
                    }
                    getdata_chooseskuid(skuid);

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
    public void chooseshr(int pos) {
        typeshr = "1";
        imclose_shr.setVisibility(View.VISIBLE);
        recyc_shr.setVisibility(View.GONE);
        AddressData data = listshr.get(pos);
        String str = data.getId() + "," + data.getName() + "," + data.getPho() + "," + data.getSfz() + ","
                + data.getAddress() + "," + data.getAddressDetail() + "," + data.getAddressbhao();

        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.getdiziitem1);
        databean.setT(str);
        EventBus.getDefault().post(databean);
    }

    public void getdata_search(String txt) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", xiadan.this));
            jsonObject1.put("text", txt);
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
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        listshr.removeAll(listshr);
                        JSONArray jsa_ = jsonObject.getJSONArray("Value");
                        if (jsa_.length() >= 1) {
                            if (!recyc_shr.isShown()) {
                                recyc_shr.setVisibility(View.VISIBLE);
                            }
                        } else recyc_shr.setVisibility(View.GONE);
                        for (int i = 0; i < jsa_.length(); i++) {
                            JSONObject jsosp = jsa_.getJSONObject(i);
                            listshr.add(new AddressData(jsosp.getString("address_id"), jsosp.getString("Receiver"), jsosp.getString("tel_phone"), jsosp.getString("areainfo"), jsosp.getString("address"), jsosp.getString("is_default")
                                    , jsosp.getString("idcardno"), jsosp.getString("areano"), "0"));
                        }
                        adashr.notifyDataSetChanged();
                        if (listshr.size() >= 1) {
                            if (!recyc_shr.isShown()) {
                                recyc_shr.setVisibility(View.VISIBLE);
                            }
                        } else recyc_shr.setVisibility(View.GONE);

                    } else if (jsonObject.getString("Status").equals("1101")) {
                    } else
                        toaste_ut(xiadan.this, jsonObject.getString("Details"));
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
     * @param txt .查询订单处理状态
     */
    int numcheck = 1;

    public void getdata_searchnumber(final String txt) {
        numcheck++;
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orderId", txt);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SearchSubOrderStatus");
            jsonObject.put("ModuleName", "OrderManage");
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
                        if (jdt.isAdded()) {
                            jdt.dismiss();
                        }
                        numcheck = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("type", "lm");
                        querenorder.setArguments(bundle);
                        if (!querenorder.isAdded()) {
                            querenorder.show(getSupportFragmentManager(), "lm");
                        }
                    } else {
                        if (numcheck <= 10)
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getdata_searchnumber(txt);
                                }
                            }, 1000);
                        else {
                            numcheck = 1;
                            cancel_order(txt);
                            if (jdt.isAdded()) {
                                jdt.dismiss();
                            }
                        }
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

    public void cancel_order(String txt) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orderId", txt);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "CancelQueueOrder");
            jsonObject.put("ModuleName", "OrderManage");
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
                    toaste_ut(xiadan.this, jsonObject.getString("Details"));
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