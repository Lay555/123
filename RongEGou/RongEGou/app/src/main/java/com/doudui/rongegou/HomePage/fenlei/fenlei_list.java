package com.doudui.rongegou.HomePage.fenlei;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.Goods.sharesdkui;
import com.doudui.rongegou.HomePage.MyAdapter;
import com.doudui.rongegou.HomePage.MyAdapter1;
import com.doudui.rongegou.HomePage.adapterdata;
import com.doudui.rongegou.HomePage.header.HPHeaderother;
import com.doudui.rongegou.HomePage.header.otherheadadapterdata;
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
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fenlei_list extends BaseActivity_ implements fenleitit_adapter1.gethenpos, fenleitit_adapter.gethenpos {

    @BindView(R.id.fenlei_dh)
    DaoHang_top fenleiDh;
    @BindView(R.id.fenlei_recyhen)
    RecyclerView fenleiRecyhen;
    @BindView(R.id.address_recy)
    RecyclerView addressRecy;
    @BindView(R.id.address_smtr)
    SmartRefreshLayout addressSmtr;
    @BindView(R.id.mainact_tesy1)
    TextView mainactTesy1;
    @BindView(R.id.fenlei_recyctop)
    RecyclerView fenleiRecyctop;
    @BindView(R.id.fenlei_lintop)
    LinearLayout fenleiLintop;
    @BindView(R.id.flss_imreturn1)
    ImageView flssImreturn1;

    @BindView(R.id.flss_viewkb)
    View v_kb;

    @BindView(R.id.hp_share)
    ImageView im_share;


    fenleitit_adapter adaptertop;
    List<fenleitit_data> listtop = new ArrayList<>();
    fenleitit_adapter1 adapterhen;
    List<fenleitit_data1> listhen = new ArrayList<>();

    MyAdapter1 adapter;
    List<adapterdata> list = new ArrayList<>();

    int page = 1;
    String typeid = "26", catid = "0", name = "",catid1 = "0";
    int poshen = 0;

    sharesdkui sharesdkunew = new sharesdkui();

    boolean canoncl = true;

    @Override
    protected void AddView() {
        Intent ix = getIntent();
        typeid = ix.getStringExtra("typeid");
        name = ix.getStringExtra("name");
        catid = ix.getStringExtra("catid");
        catid1 = ix.getStringExtra("catid");

//        fenleiDh.setvis1();
//        Glide.with(this).load(R.mipmap.share).into(fenleiDh.im_d);
        fenleiDh.settext_(name);
        adapterhen = new fenleitit_adapter1(this, listhen);
        adaptertop = new fenleitit_adapter(this, listtop);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        fenleiRecyctop.setLayoutManager(gridLayoutManager);
        fenleiRecyctop.setAdapter(adaptertop);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        fenleiRecyhen.setLayoutManager(linearLayoutManager);
        fenleiRecyhen.setAdapter(adapterhen);
        adapterhen.setheninter(this);
        adaptertop.setheninter(this);

        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new MyAdapter1(list, this);

        ((DefaultItemAnimator) addressRecy.getItemAnimator()).setSupportsChangeAnimations(false);//解决glide4.0刷新数据闪烁bug3步  bu1
        adapter.setHasStableIds(true);//bu2

        addressRecy.setLayoutManager(manager);
        addressRecy.setAdapter(adapter);


        getdata();
    }

    @Override
    protected void SetViewListen() {
        flssImreturn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fenleiLintop.isShown()) {
                    fenleiLintop.setVisibility(View.VISIBLE);
                    flssImreturn1.animate().rotation(180);
                } else {
                    fenleiLintop.setVisibility(View.GONE);
                    flssImreturn1.animate().rotation(0);
                }
            }
        });
        v_kb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fenleiLintop.setVisibility(View.GONE);
                flssImreturn1.animate().rotation(360);
            }
        });
        addressSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                canoncl = false;
                page++;
                getdata();
                addressSmtr.finishLoadmore();
                canoncl = true;
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                canoncl = false;
                page = 1;
                list.removeAll(list);
                getdata();
                addressSmtr.finishRefresh();
                canoncl = true;
            }
        });

        im_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    if (sharesdkunew != null)
                        if (!sharesdkunew.isAdded() && !sharesdkunew.isVisible()
                                && !sharesdkunew.isRemoving()) {
                            String name1 = "";
                            for (int i = 0; i < listtop.size(); i++) {
                                if (listtop.get(i).isselect.equals("1")) {
                                    if (i == 0) {
                                        name1 = name;
                                    } else name1 = listtop.get(i).getTxt();
                                    break;
                                }
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("title",  "N0."+getSharePre(("user_email"),fenlei_list.this)+"嵘e购在售商品(" + name1 + ")");
                            bundle.putString("txt", "");
                            bundle.putString("sptp", "http://rongegou.oss-cn-beijing.aliyuncs.com/Other/rongegou-logo.png");//icon缺少url
                            bundle.putString("url", BuildConfig.BASEURLFX+"/home/list.aspx?type=" + typeid+"&shareuid="+getSharePre("user_id",fenlei_list.this));
                            bundle.putString("type", "1");//新增type参数，等于1的时候隐藏海报分享功能
                            sharesdkunew.setArguments(bundle);
                            sharesdkunew.show(getSupportFragmentManager(), "fl");
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
            jsonObject1.put("page", page + "");
            jsonObject1.put("typeid", typeid);
            jsonObject1.put("catid", catid);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetSecondCategoryPage");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

            System.out.println(jsonObject);
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
                        JSONObject jso_ = jsonObject.getJSONObject("Value");
                        JSONArray jsa_grid = jso_.getJSONArray("categories");

                        if (page == 1) {
                            listhen.removeAll(listhen);
                            listtop.removeAll(listtop);
                            if (catid1.equals(catid))
                                listhen.add(new fenleitit_data1("0", "全部", "1"));
                            else listhen.add(new fenleitit_data1("0", "全部", "0"));
                            if (catid1.equals(catid))
                                listtop.add(new fenleitit_data("0", "全部", "1"));
                            else listtop.add(new fenleitit_data("0", "全部", "0"));
                            for (int i = 0; i < jsa_grid.length(); i++) {
                                JSONObject jso = jsa_grid.getJSONObject(i);
                                if (jso.getString("gcat_id").equals(catid)) {
                                    poshen = i + 1;
                                    listhen.add(new fenleitit_data1(jso.getString("gcat_id"), jso.getString("gcat_name"), "1"));
                                } else
                                    listhen.add(new fenleitit_data1(jso.getString("gcat_id"), jso.getString("gcat_name"), "0"));

                                if (jso.getString("gcat_id").equals(catid))
                                    listtop.add(new fenleitit_data(jso.getString("gcat_id"), jso.getString("gcat_name"), "1"));
                                else
                                    listtop.add(new fenleitit_data(jso.getString("gcat_id"), jso.getString("gcat_name"), "0"));
                            }
                            adapterhen.notifyDataSetChanged();
                            adaptertop.notifyDataSetChanged();
                            if (fenleiRecyhen != null)
                                fenleiRecyhen.smoothScrollToPosition(poshen);
                        }

                        JSONArray jsa_sp = jsonObject.getJSONObject("Value").getJSONArray("goods");
                        if (page != 1) {
                            if (jsa_sp.length() <= 0) {
                                Toast.makeText(fenlei_list.this, "无更多商品", Toast.LENGTH_SHORT).show();
                            }
                        }

                        for (int i = 0; i < jsa_sp.length(); i++) {
                            JSONObject jsosp = jsa_sp.getJSONObject(i);
                            list.add(new adapterdata(jsosp.getString("goods_id"), jsosp.getString("goods_conver"), jsosp.getString("goods_name"), jsosp.getString("goods_store_price"), "bushi"));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(fenlei_list.this, jsonObject.getString("Details"));
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
    protected int getLayout() {
        return R.layout.activity_fenlei_list;
    }

    @Override
    public void gethenpos(int pos) {
//        if (!canoncl) {
//            return;
//        }
        fenleiRecyhen.scrollToPosition(pos);
        listhen.get(pos).setIsselect("1");
        listhen.get(poshen).setIsselect("0");
        poshen = pos;
        if (pos == 0) {
            catid = catid1;
        }else
            catid = listhen.get(pos).getId();
        adapterhen.notifyDataSetChanged();

        listtop.get(pos).setIsselect("1");
        listtop.get(poshen).setIsselect("0");
        adaptertop.notifyDataSetChanged();
        page = 1;
//        addressSmtr.autoRefresh();

        list.removeAll(list);
        getdata();
    }

    @Override
    public void gethenpos1(int pos) {
//        if (!canoncl) {
//            return;
//        }
        if (pos == 0) {
            catid = catid1;
        }
        fenleiLintop.setVisibility(View.GONE);
        flssImreturn1.animate().rotation(360);
        fenleiRecyhen.scrollToPosition(pos);
        listhen.get(pos).setIsselect("1");
        if (poshen<=listhen.size()-1)
        listhen.get(poshen).setIsselect("0");
        poshen = pos;
        if (pos == 0) {
            catid = catid1;
        }else
        catid = listhen.get(pos).getId();
        adapterhen.notifyDataSetChanged();
        listtop.get(pos).setIsselect("1");
        listtop.get(poshen).setIsselect("0");
        adaptertop.notifyDataSetChanged();
        page = 1;
//        addressSmtr.autoRefresh();
        list.removeAll(list);
        getdata();
    }
}
