package com.doudui.rongegou.Goods;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;

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

public class sharesdkui1 extends DialogFragment {
    View view;
    Unbinder unbinder;

    String url = "", tp = "", name = "", sptp = "";//要改成正式库
    @BindView(R.id.shareui_imtp1)
    ImageView shareuiImtp1;
    @BindView(R.id.shareui_tepy1)
    TextView shareuiTepy1;
    @BindView(R.id.shareui_save1)
    TextView shareuiSave1;
    @BindView(R.id.shareui_imclose)
    ImageView imclose;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setLayout(-1, -2);
        view = inflater.inflate(R.layout.sharesdkui1, container, false);

        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        url = BuildConfig.BASEURLFX+ "/newHome/item.aspx?id=" + bundle.getString("gdid")+"&shareuid="+bundle.getString("user_id");
        tp = bundle.getString("hb");
        name = bundle.getString("goodsname");
        sptp = bundle.getString("sptp");
        Glide.with(getActivity()).load(tp).into(shareuiImtp1);

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

    @OnClick({R.id.shareui_tepy1, R.id.shareui_save1, R.id.shareui_imclose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shareui_tepy1:

                showShare1(Wechat.NAME);
                break;
            case R.id.shareui_imclose:
                dismiss();
                break;
            case R.id.shareui_save1:

                Glide.with(getActivity())
                        .load(tp)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                BitmapDrawable bd = (BitmapDrawable) resource;
                                Bitmap bm = bd.getBitmap();
                                paramsDataBean databean = new paramsDataBean();
                                databean.setMsg(configParams.share2);
                                databean.setT(bm);
                                EventBus.getDefault().post(databean);
                            }
                        });
                break;
        }
    }

    private void showShare1(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        oks.disableSSOWhenAuthorize();
        oks.setImageUrl(tp);
//        oks.setImagePath(getResourcesUri(R.mipmap.add_image));
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                dismiss();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                dismiss();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                dismiss();
            }
        });
        oks.show(getActivity());
        dismiss();
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }
}
