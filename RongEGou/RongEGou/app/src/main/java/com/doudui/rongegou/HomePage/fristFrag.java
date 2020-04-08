package com.doudui.rongegou.HomePage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.doudui.rongegou.Goods.sharesdkui;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.configParams;
import baseTools.paramsDataBean;
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

public class fristFrag extends Fragment {
    View view;
    @BindView(R.id.hp_recy)
    RecyclerView recyclerView;
    @BindView(R.id.hp_smtr)
    SmartRefreshLayout hpSmtr;
    Unbinder unbinder;

    @BindView(R.id.hp_totop)
    ImageView im_totop;


    MyAdapter adapter;
    List<adapterdata> list = new ArrayList<>();
    int page = 1;
    HPHeader hpHeader;
    android.app.FragmentManager fragmentManager;
    GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fristfrag, container, false);
            ButterKnife.bind(this, view);
        }
        getdata();
        AddView();
        SetViewListen();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    protected void AddView() {
        Bundle bundle = getArguments();
        manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new MyAdapter(list, getActivity());
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//解决glide4.0刷新数据闪烁bug3步  bu1
        adapter.setHasStableIds(true);//bu2

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        setHeaderView(recyclerView);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeaderView(position) ? manager.getSpanCount() : 1;
            }
        });

    }

    protected void SetViewListen() {
        hpSmtr.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getdata();
                hpSmtr.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                list.removeAll(list);
                getdata();
                hpSmtr.finishRefresh();
            }
        });
        im_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                im_totop.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);

            }
        });


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (manager != null) {
                    //获取RecyclerView当前顶部显示的第一个条目对应的索引
                    int position = manager.findFirstVisibleItemPosition();
                    //根据索引来获取对应的itemView
                    View firstVisiableChildView = manager.findViewByPosition(position);
                    //获取当前显示条目的高度
                    int itemHeight = firstVisiableChildView.getHeight();
                    //获取当前Recyclerview 偏移量
                    int flag = (position) * itemHeight - firstVisiableChildView.getTop();

                    if (flag > 10000)
                        im_totop.setVisibility(View.VISIBLE);
                    else im_totop.setVisibility(View.GONE);
                }
            }
        });
    }


    private void setHeaderView(RecyclerView view) {
        hpHeader = new HPHeader(getActivity(), null);
        adapter.setHeaderView(hpHeader);
        fragmentManager = getActivity().getFragmentManager();
        hpHeader.initialize(fragmentManager, getActivity());
    }

    public void getdata() {
        if (page >= 3)
            im_totop.setVisibility(View.VISIBLE);
        else im_totop.setVisibility(View.GONE);
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("page", page + "");
            jsonObject1.put("user_id", getSharePre("user_id", getActivity()));
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetIndexPage");
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
                        JSONObject jso_lbo = jsonObject.getJSONObject("Value");

                        String wxh = jso_lbo.getString("wechat");
                        if (page==1)
                        if (TextUtils.isEmpty(wxh)) {
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.banndingwxh);
                            EventBus.getDefault().post(databean);
                        }

                        JSONArray jsa = jso_lbo.getJSONArray("dt_area");
                        if (page == 1)
                            hpHeader.getjson_totop(jsa);
                        if (page == 1)
                            hpHeader.getjsonobj(jso_lbo, fragmentManager, getActivity());
                        JSONArray jsa_sp = jsonObject.getJSONObject("Value").getJSONArray("goods");
                        if (page != 1) {
                            if (jsa_sp.length() <= 0) {
                                Toast.makeText(getActivity(), "无更多商品", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), jsonObject.getString("Details"), Toast.LENGTH_SHORT).show();
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
    SharedPreferences preferences;
    protected String getSharePre(String key, Context context) {
        if (preferences==null)
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return preferences.getString(key, "0");
    }

}
