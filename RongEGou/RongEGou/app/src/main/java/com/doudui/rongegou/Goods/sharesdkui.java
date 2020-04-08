package com.doudui.rongegou.Goods;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import baseTools.configParams;
import baseTools.paramsDataBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class sharesdkui extends DialogFragment {
    View view;
    @BindView(R.id.shareui_tepy)
    TextView shareuiTepy;
    @BindView(R.id.shareui_tepyq)
    TextView shareuiTepyq;
    @BindView(R.id.shareui_tehb)
    TextView shareuiTehb;
    @BindView(R.id.shareui_tecancel)
    TextView tecancel;

    Unbinder unbinder;

    String url = "", tp = "", name = "", sptp = "", type = "0", txt = "",user_id="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setLayout(-1, -2);
        view = inflater.inflate(R.layout.sharesdkui, container, false);

        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        type = bundle.getString("type");
        if (type.equals("1")) {
            shareuiTehb.setVisibility(View.GONE);
            shareuiTepyq.setVisibility(View.GONE);


//        bundle.putString("title", sharetxt[0]);
//        bundle.putString("txt", sharetxt[1]);
//        bundle.putString("sptp", sharetxt[2]);
//        bundle.putString("url", sharetxt[3]);
            url = bundle.getString("url");
            txt = bundle.getString("txt");
            name = bundle.getString("title");
            sptp = bundle.getString("sptp");


        } else {
            user_id = bundle.getString("user_id");
            url = BuildConfig.BASEURLFX+"/newHome/item.aspx?shareuid="+user_id+"&"+"id=" + bundle.getString("gdid");//要改成正式库
            tp = bundle.getString("hb");
            name = bundle.getString("goodsname");
            sptp = bundle.getString("sptp");
        }
        return view;
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
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.shareui_tepy, R.id.shareui_tepyq, R.id.shareui_tehb, R.id.shareui_tecancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shareui_tepy:

                showShare(Wechat.NAME);
                break;
            case R.id.shareui_tepyq:
                showShare(WechatMoments.NAME);
                break;
            case R.id.shareui_tehb:
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.share1);
                EventBus.getDefault().post(databean);
                dismiss();
                break;
            case R.id.shareui_tecancel:
                dismiss();
                break;
        }
    }

    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.disableSSOWhenAuthorize();
        oks.setTitle(name);

        System.out.println(url);
        if (type.equals("1")) {
            oks.setText(txt);
        } else
            oks.setText("嵘e购");
        oks.setImageUrl(sptp);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                dismiss();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        });

        //启动分享
        oks.show(getActivity());
        dismiss();
    }


}
