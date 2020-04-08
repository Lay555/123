package com.doudui.rongegou.Order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.MainActivity;
import com.doudui.rongegou.Order.fragment.PayResult;
import com.doudui.rongegou.Order.fragment.cancelOrder;
import com.doudui.rongegou.Order.fragment.payorder;
import com.doudui.rongegou.Order.paysuc_fail.payfaile;
import com.doudui.rongegou.Order.paysuc_fail.paysucc;
import com.doudui.rongegou.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class order extends BaseActivity_ implements recycada_hen.getit_hen, payorder.zffs, recycada_.cancelord {

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
    @BindView(R.id.order_recychen)
    RecyclerView orderRecychen;
    @BindView(R.id.order_recy)
    RecyclerView orderRecy;
    @BindView(R.id.order_smtr)
    SmartRefreshLayout orderSmtr;
    @BindView(R.id.order_teprice)
    TextView tepreice;
    @BindView(R.id.order_tepay)
    TextView orderTepay;
    @BindView(R.id.order_linbot)
    LinearLayout orderLinbot;

    @BindView(R.id.order_noorder)
    LinearLayout layout_nogood;

    @BindView(R.id.noorder_tego)
    TextView te_gohome;

    recycada_hen recycadaHen;
    List<recycada_hendata> list_hen = new ArrayList<>();
    int pos_ = 0;

    recycada_ recycada_;
    List<recycada_data> list = new ArrayList<>();

    int page = 1;
    String orderstr = "10,20,30,40", txt = "";
    payorder payorder = new payorder();
    String order_sn = "", oder_info = "";
    private IWXAPI api;
    cancelOrder cancelOrder = new cancelOrder();

    @Override
    protected void AddView() {
        api = WXAPIFactory.createWXAPI(order.this, "wx8179ac04473e44c0");
        api.registerApp("wx8179ac04473e44c0");

        EventBus.getDefault().register(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        list_hen.add(new recycada_hendata("全部", "0"));
        list_hen.add(new recycada_hendata("待提交", "0"));
        list_hen.add(new recycada_hendata("待支付", "0"));
        list_hen.add(new recycada_hendata("待发货", "0"));
        list_hen.add(new recycada_hendata("待收货", "0"));

        recycadaHen = new recycada_hen(order.this, list_hen);
        orderRecychen.setLayoutManager(layoutManager);
        orderRecychen.setAdapter(recycadaHen);
        recycadaHen.setinter_hen(this);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(OrientationHelper.VERTICAL);

        recycada_ = new recycada_(this, list);
        orderRecy.setLayoutManager(layoutManager1);
        orderRecy.setAdapter(recycada_);
        recycada_.setinter_hen(this);

        pos_ = getIntent().getIntExtra("pos", 0);
        if (pos_ == 0) {
            list_hen.get(0).setIssect("1");
            orderstr = "10,20,30";
        } else if (pos_ == 1) {
            list_hen.get(1).setIssect("1");
            orderstr = "10";
        } else if (pos_ == 2) {
            list_hen.get(2).setIssect("1");
            orderstr = "40";
        } else if (pos_ == 3) {
            list_hen.get(3).setIssect("1");
            orderstr = "20";
        } else if (pos_ == 4) {
            list_hen.get(4).setIssect("1");
            orderstr = "30";
        } else if (pos_ == 5) {
            list_hen.get(5).setIssect("1");
            orderstr = "10,20,30,40";
        }
        recycadaHen.notifyDataSetChanged();
        orderRecychen.smoothScrollBy(pos_, 0);
        if (pos_ == 2) {
            orderLinbot.setVisibility(View.VISIBLE);
        } else orderLinbot.setVisibility(View.GONE);
        getdata();
    }

    @Override
    protected void SetViewListen() {

        orderImreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });

        orderSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdata();
                orderSmtr.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                list.removeAll(list);
                txt = "";
                getdata();
                orderSmtr.finishRefresh();
            }
        });
        orderTess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(orderEdit.getText().toString())) {
                    toaste_ut(order.this, "请输入内容");
                } else {
                    page = 1;
                    list.removeAll(list);
                    txt = orderEdit.getText().toString();
                    getdata();
                }
            }
        });
        orderTepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payorder.show(getFragmentManager(), "pay");
            }
        });

        te_gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.sypage1);
                databean.setT("");
                EventBus.getDefault().post(databean);
                startActivityByIntent(order.this, MainActivity.class);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void getit_hen(int pos, String str) {
        list_hen.get(pos_).setIssect("0");
        pos_ = pos;
        list_hen.get(pos).setIssect("1");
        recycadaHen.notifyDataSetChanged();
        // TODO: 2018/11/13 0013 刷新数据
        if (pos_ == 0) {
            orderstr = "10,20,30,40";
        } else if (pos_ == 1) {
            orderstr = "10";
        } else if (pos_ == 2) {
            orderstr = "40";
        } else if (pos_ == 3) {
            orderstr = "20";
        } else if (pos_ == 4) {
            orderstr = "30";
        } else if (pos_ == 5) {
            orderstr = "10,20,30,40";
        }
        page = 1;
        txt = "";
        list.removeAll(list);
        getdata();
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", getSharePre("user_id", order.this));
            jsonObject1.put("page", page);
            jsonObject1.put("text", txt);
            jsonObject1.put("order_status", orderstr);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetOrders");
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
                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        if (page == 1)
                            list.removeAll(list);
                        JSONObject jsox = jsonObject.getJSONObject("Value");
                        JSONArray jsa_ = jsox.getJSONArray("orders");

                        if (page != 1) {
                            if (jsa_.length() <= 0) {
                                toaste_ut(order.this, "没有更多数据了");
                            }
                        }
                        for (int i = 0; i < jsa_.length(); i++) {
                            JSONObject jso = jsa_.getJSONObject(i);
                            List<recycada_datazi> list1 = new ArrayList<>();
                            //下单时间 单个商品数量  运费
                            list1.add(new recycada_datazi(jso.getString("order_id"), jso.getString("goods_image"), jso.getString("goods_name"), jso.getString("goods_sku_info"), jso.getString("goods_price"), jso.getString("goods_num")));
                            list.add(new recycada_data(jso.getString("order_id"), jso.getString("order_no"), jso.getString("order_status"), jso.getString("goods_num"), jso.getString("final_amount") + "(运费:￥" + jso.getString("express_amount") + ")",
                                    jso.getString("Receiver"), jso.getString("create_time"), list1, jso.getString("afterstate"), jso.getString("exp_no"), jso.getString("exp_id"), "", "",jso.getString("order_from")));
                        }
                        recycada_.notifyDataSetChanged();


                        if (page == 1) {
                            if (pos_ == 2) {
                                if ((tepreice != null) & (!TextUtils.isEmpty(jsox.getString("amount")))) {
                                    tepreice.setText("合计:￥" + jsox.getString("amount"));
                                }
                                if (list.size() == 0) {
                                    if (orderLinbot != null)
                                        orderLinbot.setVisibility(View.GONE);
                                } else {
                                    if (orderLinbot != null)
                                        orderLinbot.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (orderLinbot != null)
                                    orderLinbot.setVisibility(View.GONE);
                            }
                            if (list.size() == 0) {
                                if (layout_nogood != null)
                                    layout_nogood.setVisibility(View.VISIBLE);
                            } else {
                                if (layout_nogood != null)
                                    layout_nogood.setVisibility(View.GONE);
                            }
                        }
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(order.this, jsonObject.getString("Details"));
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
            if (data.getMsg().equals(configParams.orderlist)) {
                list.removeAll(list);
                page = 1;
                getdata();
                return;
            }
        }
    }

    public void zfb() {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                PayTask alipay = new PayTask(order.this);
                Map<String, String> result = alipay.payV2(oder_info, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private final int SDK_PAY_FLAG = 1;
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult(
                            (Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        finish();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        Intent i = new Intent();
                        i.putExtra("type", "1");// 1订单列表，2详情页面进入
//                        i.putExtra("orderid", order_sn);
//                        i.putExtra("zffs", "支付宝");
                        i.setClass(order.this, paysucc.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(order.this, "支付失败", Toast.LENGTH_SHORT)
                                .show();
                        // 跳转支付失败页面，目前写跳转成功页面
                        Intent i = new Intent();
                        i.putExtra("type", "1");// 1订单列表，2详情页面进入
                        i.setClass(order.this, payfaile.class);
                        startActivity(i);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    /**
     * 微信支付
     */

    public void weixin(String partnerid, String prepayid, String noncestr,
                       String timeStamp, String sign1) {
        PayReq req = new PayReq();
        req.appId = "wx8179ac04473e44c0";
        req.partnerId = partnerid;
        req.prepayId = prepayid;
        req.nonceStr = noncestr;
        req.timeStamp = timeStamp;
        req.packageValue = "Sign=WXPay";
        req.sign = sign1;
        req.extData = "app data"; // optional
        api.sendReq(req);
    }

    @Override
    public void getzffs(int pz) {
        if (pz == 0) {
            String ids = "";
            for (int i = 0; i < list.size(); i++) {
                ids += list.get(i).getId() + ",";
            }
            ids = ids.substring(0, ids.length() - 1);
            getdata_zfb(ids);
        } else if (pz == 1) {
            String ids = "";
            for (int i = 0; i < list.size(); i++) {
                ids += list.get(i).getId() + ",";
            }
            ids = ids.substring(0, ids.length() - 1);
            getdata_weixin(ids);
        }
    }

    public void getdata_weixin(String ids) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orders", ids);
            jsonObject1.put("userid", getSharePre("user_id", order.this));
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "PaySpecifiedOrders");
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
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONObject jso = jsonObject.getJSONObject("Value");
                        weixin(jso.getString("partnerid"), jso.getString("prepayid"), jso.getString("noncestr"), jso.getString("timestamp"), jso.getString("sign"));
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(order.this, jsonObject.getString("Details"));
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

    public void getdata_zfb(String ids) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orders", ids);
            jsonObject1.put("userid", getSharePre("user_id", order.this));
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "PaySpecifiedOrders2");
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
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        oder_info = jsonObject.getString("Value");
                        zfb();
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(order.this, jsonObject.getString("Details"));
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
    public void cancel_order(int pos, String id) {
        //取消订单
        if (!cancelOrder.isAdded()) {
            Bundle b = new Bundle();
            b.putString("id", id);
            b.putString("user_id", getSharePre("user_id", order.this));
            b.putString("type", "1");
            cancelOrder.setArguments(b);
            cancelOrder.show(getFragmentManager(), "can" + pos);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
