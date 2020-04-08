package com.doudui.rongegou.Search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doudui.rongegou.HomePage.MyAdapter;
import com.doudui.rongegou.HomePage.MyAdapter1;
import com.doudui.rongegou.HomePage.adapterdata;
import com.doudui.rongegou.HomePage.header.otherheadadapterdata;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
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

public class Search1 extends BaseActivity_ {

    @BindView(R.id.flss_imreturn)
    ImageView flssImreturn;
    @BindView(R.id.flss_edit)
    EditText flssEdit;
    @BindView(R.id.flss_imss)
    ImageView flssImss;
    @BindView(R.id.flss_tess)
    TextView flssTess;
    @BindView(R.id.flss_retop)
    RelativeLayout flssRetop;
    @BindView(R.id.search1_recyc)
    RecyclerView search1Recyc;

    @BindView(R.id.search1_teno)
    TextView te_nosp;

    MyAdapter1 adapter;
    List<adapterdata> list = new ArrayList<>();
    GridLayoutManager manager;
    String value = "";

    @Override
    protected void AddView() {
        manager = new GridLayoutManager(this, 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new MyAdapter1(list, this);
        ((DefaultItemAnimator) search1Recyc.getItemAnimator()).setSupportsChangeAnimations(false);//解决glide4.0刷新数据闪烁bug3步  bu1
        adapter.setHasStableIds(true);//bu2

        search1Recyc.setLayoutManager(manager);
        search1Recyc.setAdapter(adapter);

        Intent i = getIntent();
        value = i.getStringExtra("value");
        flssEdit.setText(value);
        search1Recyc.setFocusable(true);
        search1Recyc.setFocusableInTouchMode(true);
//        getdata();
    }

    @Override
    protected void SetViewListen() {

        flssImreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
        flssTess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fastClick()) {
                    if (flssEdit.getText().toString().length() <= 0) {
                        toaste_ut(Search1.this, "请输入搜索内容");
                    } else {
                        list.removeAll(list);
                        value = flssEdit.getText().toString();
                        getdata();
                    }
                }
            }
        });
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("text", value);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SearchGoods");
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
                    for (int i = 0; i < jsa_.length(); i++) {
                        JSONObject jsosp = jsa_.getJSONObject(i);
                        list.add(new adapterdata(jsosp.getString("goods_id"), jsosp.getString("goods_conver"), jsosp.getString("goods_name"), jsosp.getString("goods_store_price"),"bushi"));
                    }
                    adapter.notifyDataSetChanged();
                    if (list.size() <= 0) {
                        te_nosp.setVisibility(View.VISIBLE);
                    } else te_nosp.setVisibility(View.GONE);
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
        return R.layout.activity_search1;
    }

}
