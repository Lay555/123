package com.doudui.rongegou.HomePage;

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

import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.Goods.sharesdkui;
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

public class otherFrag extends Fragment {
    View view;
    @BindView(R.id.hp_recy)
    RecyclerView recyclerView;
    @BindView(R.id.hp_smtr)
    SmartRefreshLayout hpSmtr;
    Unbinder unbinder;

    MyAdapter adapter;
    List<adapterdata> list = new ArrayList<>();

    int page = 1;
    HPHeaderother hpHeader;
    GridLayoutManager manager;
    String typeid = "26", name = "";

    @BindView(R.id.hp_totop)
    ImageView im_totop;
    @BindView(R.id.hp_share)
    ImageView im_share;
    sharesdkui sharesdkunew = new sharesdkui();

    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.otherfrag, container, false);
            ButterKnife.bind(this, view);
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            im_totop.setVisibility(View.GONE);
            AddView();
            SetViewListen();
        }

        return view;
    }

    protected void AddView() {
        Bundle bundle = getArguments();
        typeid = bundle.getString("typeid");
        name = bundle.getString("name");

        manager = new GridLayoutManager(getActivity(), 2);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new MyAdapter(list, getActivity());

        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//解决glide4.0刷新数据闪烁bug3步  bu1
        adapter.setHasStableIds(true);//bu2


        recyclerView.setLayoutManager(manager);
        setHeaderView(recyclerView);
        recyclerView.setAdapter(adapter);


        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeaderView(position) ? manager.getSpanCount() : 1;
            }
        });
        getdata();
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

        im_totop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                im_totop.setVisibility(View.GONE);
                recyclerView.scrollToPosition(0);

            }
        });
        im_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharesdkunew != null)
                    if (!sharesdkunew.isAdded() && !sharesdkunew.isVisible()
                            && !sharesdkunew.isRemoving()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("title", "N0."+preferences.getString("user_email","")+"嵘e购在售商品(" + name + ")");
                        bundle.putString("txt", "");
                        bundle.putString("user_id", preferences.getString("user_id",""));
                        bundle.putString("sptp", "http://rongegou.oss-cn-beijing.aliyuncs.com/Other/rongegou-logo.png");//icon缺少url
//                        bundle.putString("url", "https://reg.rongegou.net/home/list.aspx?type=" + typeid+"&shareuid="+preferences.getString("user_id","")); //正式
                        bundle.putString("url", BuildConfig.BASEURLFX+"/home/list.aspx?type=" + typeid+"&shareuid="+preferences.getString("user_id",""));
                        bundle.putString("type", "1");//新增type参数，等于1的时候隐藏海报分享功能
                        sharesdkunew.setArguments(bundle);
                        sharesdkunew.show(getChildFragmentManager(), "xx");
                    }
            }
        });

    }


    private void setHeaderView(RecyclerView view) {
        hpHeader = new HPHeaderother(getActivity(), null);
        adapter.setHeaderView(hpHeader);
        android.app.FragmentManager fragmentManager = getActivity().getFragmentManager();
        Bundle bundle = getArguments();
        int t = bundle.getInt("pos");
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("page", page + "");
            jsonObject1.put("typeid", typeid);
            jsonObject1.put("catid", "0");
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetFirstCategoryPage");
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
                    JSONObject jso_ = jsonObject.getJSONObject("Value");
                    JSONArray jsa_grid = jso_.getJSONArray("categories");
                    if (page == 1) {
                        List<otherheadadapterdata> listgrid = new ArrayList<>();
                        for (int i = 0; i < jsa_grid.length(); i++) {
                            JSONObject jso = jsa_grid.getJSONObject(i);
                            listgrid.add(new otherheadadapterdata(jso.getString("gcat_id"), jso.getString("gcat_description"), jso.getString("gcat_name"), name, typeid));
                        }
//                        if (listgrid.size() == 9)
//                            listgrid.add(new otherheadadapterdata("0", "seee", "全部", name, typeid));
                        hpHeader.addlist(listgrid);
                    }

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
