package com.doudui.rongegou.Order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Order.fragment.cancelOrder;
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
import baseTools.configParams;
import baseTools.lunbo.other_web;
import baseTools.paramsDataBean;
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

public class orderdetails extends BaseActivity_ {

    @BindView(R.id.ordde_imreturn)
    ImageView orddeImreturn;
    @BindView(R.id.ordde_tetit)
    TextView orddeTetit;
    @BindView(R.id.ordde_lintop)
    RelativeLayout orddeLintop;
    @BindView(R.id.ordde_tezt)
    TextView orddeTezt;
    @BindView(R.id.ordde_imzt)
    ImageView orddeImzt;
    @BindView(R.id.ordde_dizhi)
    LinearLayout orddeDizhi;
    @BindView(R.id.ordde_teti1)
    TextView orddeTeti1;
    @BindView(R.id.ordde_teti2)
    TextView orddeTeti2;
    @BindView(R.id.ordde_teti3)
    TextView orddeTeti3;
    @BindView(R.id.ordde_recyc)
    RecyclerView orddeRecyc;
    @BindView(R.id.ordde_tealprice)
    TextView orddeTealprice;
    @BindView(R.id.ordde_teyufei)
    TextView orddeTeyufei;
    @BindView(R.id.ordde_teyzonprice)
    TextView orddeTeyzonprice;
    @BindView(R.id.ordde_tebot1)
    TextView orddeTebot1;
    @BindView(R.id.ordde_tebot2)
    TextView orddeTebot2;
    @BindView(R.id.ordde_tebot3)
    TextView orddeTebot3;
    @BindView(R.id.ordde_tebot4)
    TextView orddeTebot4;
    @BindView(R.id.orderddatails_tecz1)
    TextView orderddatailsTecz1;
    @BindView(R.id.orderddatails_tecz2)
    TextView orderddatailsTecz2;
    @BindView(R.id.orderddatails_linbot)
    LinearLayout orderddatailsLinbot;

    @BindView(R.id.ordde_recycydh)
    RecyclerView recyclerView_ydh;

    orderdetailsada_zi recycada_zi;
    List<recycada_datazi> list = new ArrayList<>();
    String orderid = "", order_status = "0", user_id = "", orderid1 = "", type_shouhou = "", wuliuhao = "";

    com.doudui.rongegou.Order.fragment.cancelOrder cancelOrder = new cancelOrder();

    List<yudanhaoData> listyudanhao = new ArrayList<>();
    yundanhaoAdapter adapter;

    @Override
    protected void AddView() {
        adapter = new yundanhaoAdapter(this, listyudanhao);
        LinearLayoutManager layoutManagerydh = new LinearLayoutManager(this);
        layoutManagerydh.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_ydh.setLayoutManager(layoutManagerydh);
        recyclerView_ydh.setAdapter(adapter);
        EventBus.getDefault().register(this);
        orderid = getIntent().getStringExtra("id");
        orderid1 = getIntent().getStringExtra("orderid");
        wuliuhao = getIntent().getStringExtra("wuliuhao");
        user_id = getSharePre("user_id", this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(orderdetails.this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        orddeRecyc.setLayoutManager(layoutManager);

        recycada_zi = new orderdetailsada_zi(this, list);

        orddeRecyc.setAdapter(recycada_zi);
        recycada_zi.notifyDataSetChanged();
        getdata();
    }

    @Override
    protected void SetViewListen() {
        orddeImreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_orderdetails;
    }

    public void getdata() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "addr");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orderId", orderid);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetOrderDetails");
            jsonObject.put("ModuleName", "OrderManage");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        System.out.println(jsonObject);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());
        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        JSONObject jso = jsa.getJSONObject(0);
                        orddeTeti1.setText("收货人:   " + jso.getString("Receiver") + "  " + jso.getString("tel_phone"));
                        orddeTeti2.setText("收货地址:" + jso.getString("areainfo"));
                        orddeTeti3.setText(jso.getString("address"));
                        list.add(new recycada_datazi("", jso.getString("goods_image"), jso.getString("goods_name"), jso.getString("goods_sku_info"), jso.getString("goods_price"), jso.getString("goods_num")));
                        recycada_zi.notifyDataSetChanged();
                        orddeTealprice.setText("￥" + jso.getString("goods_amount"));
                        orddeTeyufei.setText("￥" + jso.getString("express_amount"));

                        orddeTeyzonprice.setText("￥" + jso.getString("final_amount"));

                        orddeTebot1.setText("快递公司  " + jso.getString("exp_name"));
                        orddeTebot2.setText("快递单号  " );
                        orddeTebot3.setText("订单编号  " + orderid);
                        orddeTebot4.setText("下单时间  " + jso.getString("create_time"));

                        getyundanhao(jso.getString("exp_no"), jso.getString("exp_name"));

                        order_status = jso.getString("order_status");
                        type_shouhou = jso.getString("aftersale_state");
                        status();

                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(orderdetails.this, jsonObject.getString("Details"));
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

    private void getyundanhao(String exp_no, String exp_type) {

        if (exp_no.contains(",")) {
            String[] str = exp_no.split(",");
            for (int i = 0; i < str.length; i++) {
                listyudanhao.add(new yudanhaoData(str[i], exp_type));
            }
        } else
            listyudanhao.add(new yudanhaoData(exp_no, exp_type));
        adapter.notifyDataSetChanged();
    }

    private void status() {
        if (order_status.equals("10")) {
            orddeTezt.setText("待提交");
            orderddatailsTecz1.setVisibility(View.GONE);
            orderddatailsTecz2.setVisibility(View.VISIBLE);
            orderddatailsTecz2.setText("取消订单");
            Glide.with(orderdetails.this).load(R.mipmap.dtjxq).into(orddeImzt);
            orderddatailsTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cancelOrder.isAdded()) {
                        Bundle b = new Bundle();
                        b.putString("id", orderid1);
                        b.putString("user_id", user_id);
                        b.putString("type", "2");
                        cancelOrder.setArguments(b);
                        cancelOrder.show(getFragmentManager(), "can");
                    }
                }
            });
        } else if (order_status.equals("20")) {
            orddeTezt.setText("待发货");
            orderddatailsTecz1.setVisibility(View.GONE);
            orderddatailsTecz2.setVisibility(View.VISIBLE);
            if (type_shouhou.equals("0")) {
                orderddatailsTecz2.setText("售后处理中");
            } else if (type_shouhou.equals("1")) {
                orderddatailsTecz2.setText("完成售后");
            } else if (type_shouhou.equals("-1")) {
                orderddatailsTecz2.setText("申请售后");
            }
            Glide.with(orderdetails.this).load(R.mipmap.dshxq).into(orddeImzt);
            orderddatailsTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cancelOrder.isAdded()) {
                        Intent i = new Intent(orderdetails.this, order_shouhou.class);
                        i.putExtra("id", orderid1);
                        i.putExtra("type", "2");
                        i.putExtra("type_shouhou", type_shouhou);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                }
            });
        } else if (order_status.equals("30")) {
            orddeTezt.setText("待收货");
            orderddatailsTecz1.setVisibility(View.GONE);
            orderddatailsTecz2.setVisibility(View.VISIBLE);
            orderddatailsTecz1.setText("查看物流");
            if (type_shouhou.equals("0")) {
                orderddatailsTecz2.setText("售后处理中");
            } else if (type_shouhou.equals("1")) {
                orderddatailsTecz2.setText("完成售后");
            } else if (type_shouhou.equals("-1")) {
                orderddatailsTecz2.setText("申请售后");
            }
            Glide.with(orderdetails.this).load(R.mipmap.yfh1).into(orddeImzt);
            orderddatailsTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cancelOrder.isAdded()) {
                        Intent i = new Intent(orderdetails.this, order_shouhou.class);
                        i.putExtra("id", orderid1);
                        i.putExtra("type", "2");
                        i.putExtra("type_shouhou", type_shouhou);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }
                }
            });
            orderddatailsTecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!cancelOrder.isAdded()) {
                        Intent i = new Intent();
                        i.putExtra("titl", "物流详情");
                        i.putExtra("url",
                                "https://m.kuaidi100.com/result.jsp?nu="
                                        + wuliuhao);
                        i.setClass(orderdetails.this, other_web.class);
                        startActivity(i);
                        overridePendingTransition(
                                R.anim.push_left_in, R.anim.push_left_out);
                    }
                }
            });
        } else if (order_status.equals("40")) {
            orddeTezt.setText("待支付");
            orderddatailsTecz1.setVisibility(View.GONE);
            orderddatailsTecz2.setVisibility(View.GONE);
            orderddatailsTecz2.setText("待支付");
            Glide.with(orderdetails.this).load(R.mipmap.dzhfxq).into(orddeImzt);

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderlist1)) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
