package com.doudui.rongegou.HomePage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.Goods.sharesdkui;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fristFragTop4 extends BaseActivity_ {

    @BindView(R.id.fristtop4_dh)
    DaoHang_top fristtop4Dh;
    @BindView(R.id.fristtop4_recy)
    RecyclerView fristtop4Recy;
    @BindView(R.id.fristtop4_smtr)
    SmartRefreshLayout fristtop4Smtr;
    int page = 1;
    String txt = "", tit = "";

    MyAdapter1 adapter;
    List<adapterdata> list = new ArrayList<>();
    sharesdkui sharesdkunew = new sharesdkui();
    SharedPreferences preferences;

    @Override
    protected void AddView() {
        Intent i = getIntent();
        fristtop4Dh.settext_(i.getStringExtra("tit"));
        tit = i.getStringExtra("tit");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String isneedshare = i.getStringExtra("isneedshare");
        if (isneedshare.equals("1"))
//        if (!tit.equals("预购专区"))
            fristtop4Dh.setvis1();
        Glide.with(this).load(R.mipmap.share).into(fristtop4Dh.im_d);
        txt = i.getStringExtra("txt");
        if (txt.equals("7"))
            tit = "预购专区";
        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new MyAdapter1(list, this);

        ((DefaultItemAnimator) fristtop4Recy.getItemAnimator()).setSupportsChangeAnimations(false);//解决glide4.0刷新数据闪烁bug3步  bu1
        adapter.setHasStableIds(true);//bu2

        fristtop4Recy.setLayoutManager(manager);
        fristtop4Recy.setAdapter(adapter);

        getdata();
    }

    @Override
    protected void SetViewListen() {
        fristtop4Smtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdata();
                fristtop4Smtr.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                list.removeAll(list);
                getdata();
                fristtop4Smtr.finishRefresh();
            }
        });
        fristtop4Dh.im_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharesdkunew != null)
                    if (!sharesdkunew.isAdded() && !sharesdkunew.isVisible()
                            && !sharesdkunew.isRemoving()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "N0."+getSharePre("user_email",fristFragTop4.this)+"嵘e购在售商品(" + tit + ")");
                        bundle.putString("txt", "");
                        bundle.putString("user_id", preferences.getString("user_id",""));
                        bundle.putString("sptp", "http://rongegou.oss-cn-beijing.aliyuncs.com/Other/rongegou-logo.png");
                        bundle.putString("url", BuildConfig.BASEURLFX+"/home/list.aspx?areaid=" + txt+"&shareuid="+preferences.getString("user_id",""));

                        bundle.putString("type", "1");//新增type参数，等于1的时候隐藏海报分享功能
                        sharesdkunew.setArguments(bundle);
                        sharesdkunew.show(getSupportFragmentManager(), "xx");
                    }

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_frist_frag_top4;
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("id", txt);
            jsonObject1.put("page", page + "");
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetSpecialGoods");
            jsonObject.put("ModuleName", "DataAccess");
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
                    if (jsonObject.getString("Status").equals("1")) {

                        JSONArray jsa_sp = jsonObject.getJSONArray("Value");
                        for (int i = 0; i < jsa_sp.length(); i++) {
                            JSONObject jsosp = jsa_sp.getJSONObject(i);
                            if (txt.equals("7"))
                                list.add(new adapterdata(jsosp.getString("goods_id"), jsosp.getString("goods_conver"), jsosp.getString("goods_name"), jsosp.getString("goods_store_price"), "pre_purchase"));//pre_purchase预购商品
                            else list.add(new adapterdata(jsosp.getString("goods_id"), jsosp.getString("goods_conver"), jsosp.getString("goods_name"), jsosp.getString("goods_store_price"), txt));//pre_purchase预购商品
                        }
                        adapter.notifyDataSetChanged();

                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        Toast.makeText(fristFragTop4.this, jsonObject.getString("Details"), Toast.LENGTH_SHORT).show();
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
}
