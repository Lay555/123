package com.doudui.rongegou.HomePage;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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

public class bandingwxh extends DialogFragment {
    View view;
    Unbinder unbinder;
    int pos = 0;
    String user_id = "0", addressid = "";
    @BindView(R.id.te_wxh)
    EditText teWxh;
    @BindView(R.id.te_banding)
    TextView teBanding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        view = inflater.inflate(R.layout.bandingwxh, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDialog().setCanceledOnTouchOutside(false);
        user_id = getSharePre("user_id",getActivity());
        setviewlisten();
        return view;
    }

    private void setviewlisten() {

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    Intent home = new Intent(Intent.ACTION_MAIN);
                    home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    home.addCategory(Intent.CATEGORY_HOME);
                    startActivity(home);
                    return true;
                }
                return false;
            }
        });

        teBanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(teWxh.getText().toString())) {
                    Toast.makeText(getActivity(), "微信号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                getdata_banding(getSharePre("user_id", getActivity()),teWxh.getText().toString());
            }
        });
    }



    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(-1, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    SharedPreferences preferences;

    protected String getSharePre(String key, Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return preferences.getString(key, "0");
    }

    public void getdata_banding(final String user_id, String wxh) {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", user_id);
            jsonObject1.put("wechat", wxh);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "EditWechat");
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
                        Toast.makeText(getActivity(), "绑定成功", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString("Details"), Toast.LENGTH_SHORT).show();
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
