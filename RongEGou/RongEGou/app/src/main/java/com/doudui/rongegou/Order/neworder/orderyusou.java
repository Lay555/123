package com.doudui.rongegou.Order.neworder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Order.recycada_datazi;
import com.doudui.rongegou.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class orderyusou extends BaseActivity_ implements recycada_.cancelord, yushoufra.mappos {

    @BindView(R.id.order_imreturn)
    ImageView orderImreturn;
    @BindView(R.id.order_edit)
    EditText orderEdit;
    @BindView(R.id.order_imss)
    ImageView orderImss;
    @BindView(R.id.order_tess)
    TextView orderTess;
    @BindView(R.id.order_retop)
    RelativeLayout orderRetop;
    @BindView(R.id.te_wzh)
    TextView teWzh;
    @BindView(R.id.te_yzh)
    TextView teYzh;
    @BindView(R.id.te_yqx)
    TextView teYqx;

    @BindView(R.id.noorder_tego)
    TextView noorderTego;
    @BindView(R.id.order_recy)
    RecyclerView orderRecy;
    @BindView(R.id.order_smtr)
    SmartRefreshLayout orderSmtr;
    @BindView(R.id.order_noorder)
    View layout_nogood;
    int page = 1;
    String orderstr = "10", txt = "";//订单状态共两种 10 （未转化），20 （已转化）
    recycada_ recycada_;
    List<recycada_data> list = new ArrayList<>();
    cancelOrder cancelOrder = new cancelOrder();
    yushoufra yushoufra = new yushoufra();


    @Override
    protected void AddView() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(OrientationHelper.VERTICAL);
        recycada_ = new recycada_(this, list);
        orderRecy.setLayoutManager(layoutManager1);
        orderRecy.setAdapter(recycada_);
        recycada_.setinter_hen(this);
        EventBus.getDefault().register(this);

        txt = "";
        teWzh.setSelected(true);
        getdata();

    }

    @Override
    protected void SetViewListen() {
        orderSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdata();
                orderSmtr.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                list.removeAll(list);
                recycada_.notifyDataSetChanged();
                page = 1;
                getdata();
                orderSmtr.finishRefresh();
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.orderlistyushou)) {
                list.removeAll(list);
                recycada_.notifyDataSetChanged();
                page = 1;
                getdata();
                return;
            } else if (data.getMsg().equals(configParams.orderlistyushou_zhuanhua)) {
                if (!teYzh.isSelected()) {
                    orderstr = "20";
                    page = 1;
                    teYzh.setSelected(true);
                    teWzh.setSelected(false);
                    teYqx.setSelected(false);
                    list.removeAll(list);
                    recycada_.notifyDataSetChanged();
                    getdata();
                }
                return;
            }
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_neworderfract;
    }

    @OnClick({R.id.order_imreturn, R.id.order_tess, R.id.te_wzh, R.id.te_yzh, R.id.te_yqx})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.order_imreturn:
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
                break;
            case R.id.order_tess:

//                if (TextUtils.isEmpty(orderEdit.getText().toString())) {
//                    toaste_ut(orderyusou.this, "请输入内容");
//                } else {
                page = 1;
                list.removeAll(list);
                txt = orderEdit.getText().toString();
                getdata();
//                }

                break;
            case R.id.te_wzh:

                if (fastClick())
                    if (!teWzh.isSelected()) {
                        txt = "";
                        orderstr = "10";
                        page = 1;
                        teWzh.setSelected(true);
                        teYzh.setSelected(false);
                        teYqx.setSelected(false);
                        list.removeAll(list);
                        recycada_.notifyDataSetChanged();
                        getdata();
                    }

                break;
            case R.id.te_yzh:
                if (fastClick())
                    if (!teYzh.isSelected()) {
                        txt = "";
                        orderstr = "20";
                        page = 1;
                        teYzh.setSelected(true);
                        teWzh.setSelected(false);
                        teYqx.setSelected(false);
                        list.removeAll(list);
                        recycada_.notifyDataSetChanged();
                        getdata();
                    }

                break;
            case R.id.te_yqx:
                if (fastClick())
                    if (!teYqx.isSelected()) {
                        txt = "";
                        orderstr = "-10";
                        page = 1;
                        teYqx.setSelected(true);
                        teWzh.setSelected(false);
                        teYzh.setSelected(false);
                        list.removeAll(list);
                        recycada_.notifyDataSetChanged();
                        getdata();
                    }

                break;
        }
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", orderyusou.this));
            jsonObject1.put("page", page);
            jsonObject1.put("text", txt);
            jsonObject1.put("order_status", orderstr);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetPreOrders");
            jsonObject.put("ModuleName", "UserAccount");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);
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
                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        if (page == 1) {
                            list.removeAll(list);
                            recycada_.notifyDataSetChanged();
                        }
                        JSONArray jsa_ = jsonObject.getJSONArray("Value");

                        if (page != 1) {
                            if (jsa_.length() <= 0) {
                                toaste_ut(orderyusou.this, "没有更多数据了");
                            }
                        }
                        for (int i = 0; i < jsa_.length(); i++) {
                            JSONObject jso = jsa_.getJSONObject(i);
                            List<recycada_datazi> list1 = new ArrayList<>();
                            //下单时间 单个商品数量  运费
                            list1.add(new recycada_datazi(jso.getString("order_id"), jso.getString("goods_image"), jso.getString("goods_name"), jso.getString("goods_sku_info"), jso.getString("goods_price"), jso.getString("goods_num")));
                            if (orderstr.equals("10"))
                                list.add(new recycada_data(jso.getString("order_id"), jso.getString("order_no"), jso.getString("order_status"), jso.getString("goods_num"), jso.getString("final_amount") + "(运费:￥" + jso.getString("express_amount") + ")",
                                        jso.getString("Receiver"), jso.getString("create_time"), list1, "没有售后", "", "", "", "", "", jso.getString("address")));
                            else if (orderstr.equals("20"))
                                list.add(new recycada_data(jso.getString("order_id"), jso.getString("order_no"), jso.getString("order_status"), jso.getString("goods_num"), jso.getString("final_amount") + "(运费:￥" + jso.getString("express_amount") + ")",
                                        jso.getString("Receiver"), jso.getString("create_time"), list1, "没有售后", "", "", jso.getString("formal_orderno"), jso.getString("convert_time"), "", jso.getString("address")));
                            else if (orderstr.equals("-10"))
                                list.add(new recycada_data(jso.getString("order_id"), jso.getString("order_no"), jso.getString("order_status"), jso.getString("goods_num"), jso.getString("final_amount") + "(运费:￥" + jso.getString("express_amount") + ")",
                                        jso.getString("Receiver"), jso.getString("create_time"), list1, "没有售后", "", "", jso.getString("formal_orderno"), "", jso.getString("cancel_time"), jso.getString("address")));
                        }
                        recycada_.notifyDataSetChanged();


                        if (list.size() == 0) {
                            if (layout_nogood != null)
                                layout_nogood.setVisibility(View.VISIBLE);
                        } else {
                            if (layout_nogood != null)
                                layout_nogood.setVisibility(View.GONE);
                        }
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(orderyusou.this, jsonObject.getString("Details"));
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

    @Override
    public void cancel_order(int pos, String id) {
        if (!cancelOrder.isAdded()) {
            Bundle b = new Bundle();
            b.putString("id", id);
            b.putString("user_id", getSharePre("user_id", orderyusou.this));
            cancelOrder.setArguments(b);
            cancelOrder.show(getFragmentManager(), "can" + pos);
        }
    }

    @Override
    public void zhuanhua(int pos, String id) {
        if (!yushoufra.isAdded()) {
            Bundle b = new Bundle();
            b.putString("id", list.get(pos).getBhao());
            b.putString("user_id", getSharePre("user_id", orderyusou.this));
            yushoufra.setArguments(b);
            yushoufra.show(getFragmentManager(), "yu" + pos);
        }
    }

    @Override
    public void mappos(int pos) {
        toaste_ut(orderyusou.this, "转化成功");
        orderstr = "20";
        page = 1;
        teYzh.setSelected(true);
        teWzh.setSelected(false);
        list.removeAll(list);
        recycada_.notifyDataSetChanged();
        getdata();
    }


}
