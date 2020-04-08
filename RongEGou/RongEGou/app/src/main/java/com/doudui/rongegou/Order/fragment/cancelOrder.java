package com.doudui.rongegou.Order.fragment;

import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Order.order;
import com.doudui.rongegou.Order.recycada_data;
import com.doudui.rongegou.Order.recycada_datazi;
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

public class cancelOrder extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.jiedan_tequxiao)
    TextView jiedanTequxiao;
    @BindView(R.id.jiedan_teok)
    TextView jiedanTeok;
    Unbinder unbinder;
    String orderid = "", user_id = "", type = "1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.cancelorder, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDialog().setCanceledOnTouchOutside(false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        Bundle b = getArguments();
        user_id = b.getString("user_id");
        orderid = b.getString("id");
        type = b.getString("type");


        setdata();
        setviewlisten();


        return view;
    }

    private void setviewlisten() {
        jiedanTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
            }
        });
        jiedanTequxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void setdata() {

    }

    public interface mappos {
        void mappos(int pos);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        orderid = "";
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 100, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orderId", orderid);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "CancelOrder");
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
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        Toast.makeText(getActivity(), "取消订单成功", Toast.LENGTH_SHORT).show();
                        if (type.equals("1")) {
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.orderlist);
                            EventBus.getDefault().post(databean);
                        } else {
                            paramsDataBean databean = new paramsDataBean();
                            databean.setMsg(configParams.orderlist1);
                            EventBus.getDefault().post(databean);
                        }
                        dismiss();
                    } else if (jsonObject.getString("Status").equals("1101")) {
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


}
