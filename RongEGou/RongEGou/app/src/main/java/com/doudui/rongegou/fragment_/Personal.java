package com.doudui.rongegou.fragment_;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.doudui.rongegou.HomePage.fristFragTop4;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.LoginAct.Loginact;
import com.doudui.rongegou.Order.neworder.orderyusou;
import com.doudui.rongegou.Order.order;
import com.doudui.rongegou.Order.payinfoact;
import com.doudui.rongegou.Percenter.Address.AddressList;
import com.doudui.rongegou.Percenter.Gonyinshang.gongyinshang;
import com.doudui.rongegou.Percenter.Gonyinshang.xiaoshoue;
import com.doudui.rongegou.Percenter.JFRules;
import com.doudui.rongegou.Percenter.Perziliao.Perziliaox;
import com.doudui.rongegou.Percenter.testWeb;
import com.doudui.rongegou.Percenter.zijinpac.tixian;
import com.doudui.rongegou.Percenter.zijinpac.zijinmingxi;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseTools.BaseFragment_;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Personal extends BaseFragment_ {

    @BindView(R.id.per_tename)
    TextView perTename;
    @BindView(R.id.per_tevip)
    TextView perTevip;
    @BindView(R.id.per_tejf)
    TextView perTejf;
    @BindView(R.id.per_imhead)
    ImageView im_head;

    @BindView(R.id.per_linAllOrder)
    LinearLayout perLinAllOrder;
    @BindView(R.id.per_orderdtj)
    LinearLayout perOrderdtj;
    @BindView(R.id.per_orderdzf)
    LinearLayout perOrderdzf;
    @BindView(R.id.per_orderdfh)
    LinearLayout perOrderdfh;
    @BindView(R.id.per_orderdsh)
    LinearLayout perOrderdsh;
    @BindView(R.id.per_linziliao)
    LinearLayout perLinziliao;
    @BindView(R.id.per_linduizhang)
    LinearLayout perLinduizhang;

//    @BindView(R.id.per_xr)
//    LinearLayout perLinxinren;

    @BindView(R.id.per_linxsale)
    LinearLayout perLinxsale;
    @BindView(R.id.per_imbot)
    ImageView perImbot;

    @BindView(R.id.per_imjf)
    ImageView perjf;

    @BindView(R.id.per_litest)
    LinearLayout pertest;

    @BindView(R.id.per_lincd)
    LinearLayout lin_cd;
    @BindView(R.id.per_cdim)
    ImageView im_cd;
    @BindView(R.id.per_cdte)
    TextView te_cd;

    @BindView(R.id.per_xr)
    LinearLayout lin_add;
    @BindView(R.id.per_xrim)
    ImageView im_xr;
    @BindView(R.id.per_xrte)
    TextView te_xr;

    @BindView(R.id.per_linyugou)
    LinearLayout lin_yg;
    @BindView(R.id.per_ygim)
    ImageView im_yg;
    @BindView(R.id.per_ygte)
    TextView te_yg;

    @BindView(R.id.per_viewkb1)
    View v_kb1;
    @BindView(R.id.per_viewkb2)
    View v_kb2;
    @BindView(R.id.per_tenum)
    TextView te_num;


    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    String code = "", pho = "";
    @BindView(R.id.per_linzijin)
    RelativeLayout perLinzijin;
    @BindView(R.id.per_tenumtixian)
    TextView perTenumtixian;
    @BindView(R.id.per_lintixian)
    RelativeLayout perLintixian;
    @BindView(R.id.per_lindizhi)
    LinearLayout perLindizhi;
    @BindView(R.id.per_linyugouorder)
    LinearLayout perLinyugouorder;
    @BindView(R.id.per_linadd)
    LinearLayout perLinadd;
    Unbinder unbinder;

    @Override
    protected void AddView() {
        pho = getSharePre("user_pho", getActivity());
        getuserinfo();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void SetViewListen() {
        perjf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(getActivity(), JFRules.class, false);
            }
        });
        perLinduizhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(getActivity(), payinfoact.class, false);
            }
        });
        pertest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(getActivity(), testWeb.class, false);
            }
        });
        perLintixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityByIntent(getActivity(), tixian.class,false);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.personal;
    }


    @OnClick({R.id.per_tename, R.id.per_tevip, R.id.per_tejf, R.id.per_linAllOrder, R.id.per_orderdtj, R.id.per_orderdzf, R.id.per_orderdfh, R.id.per_orderdsh, R.id.per_linziliao,
            R.id.per_lindizhi, R.id.per_linxsale, R.id.per_imbot,
//            R.id.per_xr, R.id.per_lincd, R.id.per_linyugou,
            R.id.per_linyugouorder, R.id.per_linzijin})
    public void onViewClicked(View view) {
        Intent i;
        if (!getSharePre("user_id", getActivity()).equals("0"))
            switch (view.getId()) {
                case R.id.per_tename:
                    break;
                case R.id.per_tevip:
                    break;
                case R.id.per_tejf:
                    break;
                case R.id.per_linAllOrder:
                    i = new Intent(getActivity(), order.class);
                    i.putExtra("pos", 0);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_orderdtj:
                    i = new Intent(getActivity(), order.class);
                    i.putExtra("pos", 1);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_orderdzf:
                    i = new Intent(getActivity(), order.class);
                    i.putExtra("pos", 2);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_orderdfh:
                    i = new Intent(getActivity(), order.class);
                    i.putExtra("pos", 3);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_orderdsh:
                    i = new Intent(getActivity(), order.class);
                    i.putExtra("pos", 4);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_linziliao:
                    startActivityByIntent(getActivity(), Perziliaox.class, false);
                    break;
                case R.id.per_lindizhi:
                    i = new Intent(getActivity(), AddressList.class);
                    i.putExtra("getlist", "0");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_linxsale:
                    startActivityByIntent(getActivity(), xiaoshoue.class, false);

                    break;
                case R.id.per_imbot:
                    i = new Intent(getActivity(), gongyinshang.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;

//                case R.id.per_xr:
//                    i = new Intent(getActivity(), fristFragTop4.class);
//                    i.putExtra("tit", "新人专属");
//                    i.putExtra("txt", "is_newguys");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.push_left_in,
//                            R.anim.push_left_out);
//                    break;
//                case R.id.per_lincd:
//                    i = new Intent(getActivity(), fristFragTop4.class);
//                    i.putExtra("tit", "成都专区");
//                    i.putExtra("txt", "is_chengdu");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.push_left_in,
//                            R.anim.push_left_out);
//                    break;
//
//                case R.id.per_linyugou:
//                    i = new Intent(getActivity(), fristFragTop4.class);
//                    i.putExtra("tit", "预购专区");
//                    i.putExtra("txt", "pre_purchase");
//                    startActivity(i);
//                    getActivity().overridePendingTransition(R.anim.push_left_in,
//                            R.anim.push_left_out);
//                    break;
                case R.id.per_linyugouorder:
                    i = new Intent(getActivity(), orderyusou.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
                case R.id.per_linzijin:
                    i = new Intent(getActivity(), zijinmingxi.class);
                    i.putExtra("type", "0");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    break;
            }
        else {
            toaste_ut(getActivity(), "请登录");
            i = new Intent();
            i.putExtra("type", "0");
            i.setClass(getActivity(), Loginact.class);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }
    }

    public void getuserinfo() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_tel", pho);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetUserInfo");
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
                        JSONArray jsonArray = jsonObject.getJSONArray("Value");
                        JSONObject jso = jsonArray.getJSONObject(0);
                        sharePre("user_id", jso.getString("user_id"), getActivity());
                        sharePre("user_email", jso.getString("user_email"), getActivity());
                        sharePre("user_pho", pho, getActivity());
                        perTename.setText(jso.getString("user_name"));
                        perTevip.setText(jso.getString("user_level"));
                        perTejf.setText("总积分" + jso.getString("user_goldnumminus"));

                        JSONArray jsapos = jsonArray.getJSONArray(1);

                        if (jsapos.toString().contains("\"id\":\"6\"")) {
                            lin_add.setVisibility(View.VISIBLE);
                            System.out.println("1111666");
                        } else
                            lin_add.setVisibility(View.GONE);

                        if (jsapos.toString().contains("\"id\":\"7\"")) {
                            lin_yg.setVisibility(View.VISIBLE);
                            System.out.println("1111777");
                        } else
                            lin_yg.setVisibility(View.GONE);

                        if (jsapos.toString().contains("\"id\":\"8\"")) {
                            lin_cd.setVisibility(View.VISIBLE);
                            System.out.println("1111888");
                        } else
                            lin_cd.setVisibility(View.GONE);

                        for (int i = 0; i < jsapos.length(); i++) {
                            JSONObject jso_ = jsapos.getJSONObject(i);
                            if (jso_.getString("id").equals("6")) {
                                te_xr.setText(jso_.getString("areaname"));
                                Glide.with(getActivity()).load(jso_.getString("areaimage")).into(im_xr);
                                setonc(lin_add, jso_.getString("id"), jso_.getString("areaname"));
                            } else if (jso_.getString("id").equals("7")) {
                                te_yg.setText(jso_.getString("areaname"));
                                Glide.with(getActivity()).load(jso_.getString("areaimage")).into(im_yg);
                                setonc(lin_yg, jso_.getString("id"), jso_.getString("areaname"));
                            } else if (jso_.getString("id").equals("8")) {
                                te_cd.setText(jso_.getString("areaname"));
                                Glide.with(getActivity()).load(jso_.getString("areaimage")).into(im_cd);
                                setonc(lin_cd, jso_.getString("id"), jso_.getString("areaname"));
                            }
                        }
                        if (jsapos.length() >= 3) {
                            v_kb1.setVisibility(View.GONE);
                            v_kb2.setVisibility(View.GONE);

                        } else if (jsapos.length() == 2) {
                            v_kb1.setVisibility(View.VISIBLE);
                            v_kb2.setVisibility(View.GONE);
                        } else if (jsapos.length() == 1) {
                            v_kb1.setVisibility(View.VISIBLE);
                            v_kb2.setVisibility(View.VISIBLE);
                        }
                        te_num.setText( jso.getString("available_predeposit"));
                        perTenumtixian.setText(jso.getString("freeze_predeposit"));

                        Glide.with(getActivity()).load(jso.getString("user_avatar")).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(im_head);
                        sharePre("user_head", jso.getString("user_avatar"), getActivity());
                        sharePre("user_name", jso.getString("user_name"), getActivity());
                        sharePre("user_pho", jso.getString("user_tel"), getActivity());
                        sharePre("user_sr", jso.getString("user_birthday"), getActivity());
                        sharePre("isvip", jso.getString("is_newguy"), getActivity());
                        sharePre("user_id", jso.getString("user_id"), getActivity());
                        // TODO: 2018/11/27 0027 //存储id phone等
                    } else {
                        toaste_ut(getActivity(), jsonObject.getString("Details"));
                        sharePreclear();
                        Glide.with(getActivity()).load(R.mipmap.logo).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(im_head);
                        perTename.setText("未登录");
                        perTevip.setText("未登录");
                        perTejf.setText("总积分0");
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

    public void setonc(View view, final String id, final String tit) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.equals("7")) {
                    Intent i;
                    i = new Intent(getActivity(), fristFragTop4.class);
                    i.putExtra("tit", tit);
                    i.putExtra("txt", id);
                    i.putExtra("isneedshare", "0");//0不需要分享
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else {
                    Intent i;
                    i = new Intent(getActivity(), fristFragTop4.class);
                    i.putExtra("tit", tit);
                    i.putExtra("txt", id);
                    i.putExtra("isneedshare", "0");
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.grzxzl)) {
                pho = getSharePre("user_pho", getActivity());
                getuserinfo();
                return;
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
