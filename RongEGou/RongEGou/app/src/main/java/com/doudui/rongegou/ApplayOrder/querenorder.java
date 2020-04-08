package com.doudui.rongegou.ApplayOrder;

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
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class querenorder extends android.support.v4.app.DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.xiadan_teok)
    TextView xiadanTeok;
    Unbinder unbinder;
    String type = "sy";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.querenorder, container, false);
        unbinder = ButterKnife.bind(this, view);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        Bundle b = getArguments();
        type = b.getString("type");
        setdata();
        setviewlisten();


        return view;
    }

    private void setviewlisten() {
        xiadanTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getArguments();
                type = b.getString("type");
                if (type.equals("sy")) {
                    paramsDataBean databean = new paramsDataBean();
                    databean.setMsg(configParams.syxdcg);
                    databean.setT("");
                    EventBus.getDefault().post(databean);
                } else {
                    paramsDataBean databean = new paramsDataBean();
                    databean.setMsg(configParams.syxdcg1);
                    databean.setT("");
                    EventBus.getDefault().post(databean);
                }
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
        type = "sy";
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


}
