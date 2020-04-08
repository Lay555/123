package com.doudui.rongegou.Goods;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.ApplayOrder.xiadan;
import com.doudui.rongegou.Goods.guige.sp_ggada;
import com.doudui.rongegou.Goods.guige.sp_ggadadadata;
import com.doudui.rongegou.Goods.guige.sp_ggadadadata1;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class guigefrag extends DialogFragment implements sp_ggada.xxshua {
    View view;
    @BindView(R.id.guige_imatp)
    ImageView guigeImatp;
    @BindView(R.id.guige_teprice)
    TextView guigeTeprice;
    @BindView(R.id.guige_teprice1)
    TextView guigeTeprice1;
    @BindView(R.id.guige_imclose)
    ImageView guigeImclose;
    @BindView(R.id.guige_tekucun)
    TextView guigeTekucun;
    @BindView(R.id.guige_teguige)
    TextView guigeTeguige;
    @BindView(R.id.guige_recyc)
    RecyclerView guigeRecyc;
    @BindView(R.id.mcarada_imdcrease)
    Button mcaradaImdcrease;
    @BindView(R.id.mcarada_edcount)
    EditText mcaradaEdcount;
    @BindView(R.id.mcarada_increase)
    Button mcaradaIncrease;
    @BindView(R.id.mcarada_lin2)
    LinearLayout mcaradaLin2;
    @BindView(R.id.addressit_teadd)
    TextView addressitTeadd;
    Unbinder unbinder;

    sp_ggada sp_ggada;
    List<sp_ggadadadata> list = new ArrayList<>();
    String goodsid = "", spec_ids = "";
    int num = 1, maxnum = 33;
    String skuid = "", price = "", kucun = "", gge = "", goodsname = "", isyugou = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setLayout(-1, -2);
        view = inflater.inflate(R.layout.guigefrag, container, false);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        guigeRecyc.setLayoutManager(manager);

        sp_ggada = new sp_ggada(list, getActivity());
        guigeRecyc.setAdapter(sp_ggada);
        sp_ggada.setshaxin(this);


        Bundle bundle = getArguments();
        goodsid = bundle.getString("gdid");

        if (bundle.getString("isyugou") != null)
            isyugou = bundle.getString("isyugou");

        Glide.with(getActivity()).load(bundle.getString("gdpic")).into(guigeImatp);
        goodsname = bundle.getString("goodsname");
        guigeTeprice.setText(price);
        guigeTekucun.setText(kucun);
        guigeTeguige.setText(gge);

        if (list.size() <= 0) {
            getdata();
        }
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        mcaradaImdcrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isyugou.equals("pre_purchase"))//pre_purchase预购商品
                {
                    if (num >= 2) {
                        num--;
                        mcaradaEdcount.setText(num + "");
                    }
                } else {

                    if (num > maxnum)
                        num = maxnum;
                    if (maxnum == 0)
                        num = 0;
                    if (num >= 2) {
                        num--;
                        mcaradaEdcount.setText(num + "");
                        //计算价格
                        String[] id = spec_ids.split(",");
                        if (id.length == list.size()) {
                            getdata_gg();
                        }
                    }
                }
            }
        });
        mcaradaIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isyugou.equals("pre_purchase"))//pre_purchase预购商品
                {
                    num++;
                    mcaradaEdcount.setText(num + "");
                } else {
                    if (maxnum == 0)
                        num = 0;
                    if (num < maxnum) {
                        num++;
                        mcaradaEdcount.setText(num + "");
                        String[] id = spec_ids.split(",");
                        if (id.length == list.size()) {
                            getdata_gg();
                        }
                    }
                }
            }
        });

        mcaradaEdcount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mcaradaEdcount.getText().toString())) {
                    if (maxnum == 0) {
                        mcaradaEdcount.setText("0");
                    } else mcaradaEdcount.setText("1");
                } else {
                    int num_ = Integer.valueOf(mcaradaEdcount.getText().toString()).intValue();
                    if (num_ > 1000) {
                        num_ = 999;
                        mcaradaEdcount.setText(num_ + "");
                    }
                    num = num_;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addressitTeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num != 0) {
                    String[] id = spec_ids.split(",");
                    if (id.length == list.size()) {
                        dismiss();
                        Intent i = new Intent(getActivity(), xiadan.class);
                        i.putExtra("skuid", skuid);
                        i.putExtra("goodsid", goodsid);
                        i.putExtra("num", num);
                        i.putExtra("name", goodsname);
                        i.putExtra("isyugou", isyugou);
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    } else
                        Toast.makeText(getActivity(), "请选择完规格", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getActivity(), "库存不足", Toast.LENGTH_LONG).show();
            }
        });
        guigeImclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void shuaxin() {
        spec_ids = "";
        String gg = "";
        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.get(i).getList().size(); j++) {
                if (list.get(i).getList().get(j).isT_sele()) {
                    spec_ids += list.get(i).getList().get(j).getId() + ",";
                    gg += list.get(i).getList().get(j).getText() + ",";
                    break;
                }
            }
        }
        if (spec_ids.contains(",")) {
            spec_ids = spec_ids.substring(0, spec_ids.length() - 1);
            gg = gg.substring(0, gg.length() - 1);
        }
        gge = gg;
        guigeTeguige.setText("规格：" + gg);
        String[] id = spec_ids.split(",");
        if (id.length == list.size()) {
            num = 1;
            mcaradaEdcount.setText("1");
            getdata_gg();
        }
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("goods_id", goodsid);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetGoodsSpecifications");
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


                    JSONArray jsa_ = jsonObject.getJSONArray("Value");
                    String gg = "";
                    list.removeAll(list);
                    for (int i = 0; i < jsa_.length(); i++) {
                        JSONObject jso = jsa_.getJSONObject(i);

                        JSONArray jsa = jso.getJSONArray("values");
                        List<sp_ggadadadata1> list1 = new ArrayList<>();
                        for (int j = 0; j < jsa.length(); j++) {
                            JSONObject jso1 = jsa.getJSONObject(j);
                            if (j == 0) {
                                list1.add(new sp_ggadadadata1(true, jso1.getString("spec_value_id"), jso1.getString("spec_value_name"), "1"));
                                spec_ids += jso1.getString("spec_value_id") + ",";
                                gg += jso1.getString("spec_value_name") + ",";
                            } else
                                list1.add(new sp_ggadadadata1(false, jso1.getString("spec_value_id"), jso1.getString("spec_value_name"), "1"));
                        }
                        list.add(new sp_ggadadadata(jso.getString("spec_id"), jso.getString("spec_name"), list1));
                    }
                    sp_ggada.notifyDataSetChanged();


                    if (spec_ids.contains(",")) {
                        spec_ids = spec_ids.substring(0, spec_ids.length() - 1);
                        gg = gg.substring(0, gg.length() - 1);
                    }
                    guigeTeguige.setText("规格：" + gg);
                    gge = gg;
                    String[] id = spec_ids.split(",");
                    if (id.length == list.size()) {
                        getdata_gg();
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

                    Spannable span = new SpannableString("￥" + jso.getString("store_price"));
                    span.setSpan(new AbsoluteSizeSpan(11, true), 0, 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new AbsoluteSizeSpan(17, true), 1, jso.getString("store_price").length() + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    guigeTeprice.setText(span);

                    price = "￥" + jso.getString("store_price");
                    guigeTekucun.setText("库存:" + jso.getString("stock"));
                    kucun = "库存:" + jso.getString("stock");
                    maxnum = Integer.valueOf(jso.getString("stock")).intValue();
                    skuid = jso.getString("skuid");
                    if (maxnum == 0) {
                        if (isyugou.equals("pre_purchase"))
                            num = 1;
                        else
                            num = 0;
                        if (isyugou.equals("pre_purchase"))
                        mcaradaEdcount.setText("1");
                        else mcaradaEdcount.setText("0");
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
    public void dismiss() {
        super.dismiss();
        list.removeAll(list);
    }
}
