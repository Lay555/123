package com.doudui.rongegou.Percenter.Perziliao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.LoginAct.Loginact;
import com.doudui.rongegou.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


public class Perziliaox extends BaseActivity_ implements TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao {
    @BindView(R.id.perzl_dh)
    DaoHang_top perzlDh;
    @BindView(R.id.perzl_tesq)
    TextView perzlTesq;
    @BindView(R.id.perzl_imhead)
    ImageView perzlImhead;
    @BindView(R.id.xiadan_kdfx)
    TextView xiadanKdf;
    @BindView(R.id.xiadan_sjhx)
    TextView xiadanSjh;
    @BindView(R.id.xiadan_srx)
    TextView xiadanSr;
    @BindView(R.id.xiadan_tequitx)
    TextView te_quit;

    @BindView(R.id.lin_wx)
    LinearLayout lin_wx;
    @BindView(R.id.xiadan_wx)
    TextView te_wx;


    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    retro_intf serivce = retrofit_single.getInstence().getserivce(1);

    @Override
    protected void AddView() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        perzlDh.settext_("个人资料");
        EventBus.getDefault().register(this);
        Glide.with(this).load(getSharePre("user_head", Perziliaox.this)).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(perzlImhead);
        getuserinfo();
    }

    @Override
    protected void SetViewListen() {
        te_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = preferences.edit();
                editor.clear();
                editor.commit();
                xiadanKdf.setText("未登录");
                xiadanSjh.setText("未登录");
                xiadanSr.setText("未登录");
                Glide.with(Perziliaox.this).load("").apply(RequestOptions.bitmapTransform(new CircleCrop())).into(perzlImhead);
                te_quit.setVisibility(View.GONE);
                Intent i = new Intent();
                i.putExtra("type", "1");
                i.setClass(Perziliaox.this, Loginact.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        lin_wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(Perziliaox.this, editwxact.class);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_perziliao;
    }


    @OnClick({R.id.perzl_tesq, R.id.perzl_imhead, R.id.xiadan_kdfx, R.id.xiadan_sjhx, R.id.xiadan_srx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.perzl_tesq:
                break;
            case R.id.perzl_imhead:
                break;
            case R.id.xiadan_kdfx:
//                startActivityByIntent(this, xiugainame.class);
                break;
            case R.id.xiadan_sjhx:
//                startActivityByIntent(this, ChangePho.class);
                break;
            case R.id.xiadan_srx:
                break;
        }
    }


    public void getuserinfo() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_tel", getSharePre("user_pho", Perziliaox.this));
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
                        if (xiadanKdf != null) {
                            xiadanKdf.setText(jso.getString("user_name"));
                            xiadanSjh.setText(jso.getString("user_tel"));
                            xiadanSr.setText(jso.getString("user_birthday"));
                            te_wx.setText(jso.getString("user_sinaopenid"));
                            perzlTesq.setText("社群号" + jso.getString("user_email") + " 推荐号" + jso.getString("user_qq"));
                            Glide.with(Perziliaox.this).load(jso.getString("user_avatar")).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(perzlImhead);
                            sharePre("user_head", jso.getString("user_avatar"), Perziliaox.this);
                            sharePre("user_name", jso.getString("user_name"), Perziliaox.this);
                            sharePre("user_pho", jso.getString("user_tel"), Perziliaox.this);
                            sharePre("user_sr", jso.getString("user_birthday"), Perziliaox.this);
                            sharePre("userwx", jso.getString("user_sinaopenid"), Perziliaox.this);
                            te_quit.setVisibility(View.VISIBLE);
                        }
                        // TODO: 2018/11/27 0027 //存储id phone等
                    } else {
                        editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        if (xiadanKdf != null) {
                            xiadanKdf.setText("未登录");
                            xiadanSjh.setText("未登录");
                            xiadanSr.setText("未登录");
                            Glide.with(Perziliaox.this).load("").apply(RequestOptions.bitmapTransform(new CircleCrop())).into(perzlImhead);
                            perzlTesq.setText("请先登录");
                            te_quit.setVisibility(View.GONE);
                        }
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

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
//        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true);
        return takePhoto;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeSuccess(TResult result) {
        File test = new File(result.getImage().getOriginalPath());
        if (test.exists()) {
            Luban.with(this).load(test)
                    .ignoreBy(100)
                    .setTargetDir("")
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            if (!jdt.isAdded())
                                jdt.show(getFragmentManager(), "jdt");
                            try {
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onSuccess(File file) {
                            if (jdt.isAdded())
                                jdt.dismiss();
                            String str = "";
                            try {
                                str = encodeBase64File(file);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            uploadpic(ix, str);
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (jdt.isAdded())
                                jdt.dismiss();
                        }
                    }).launch();    //启动压缩
        }
    }

    public String encodeBase64File(File file) throws Exception {
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }


    @Override
    public void getPaiZ(int pz) {
        if (pz == 1) {
            getpic(1);
        } else if (pz == 2) {
            getpic(2);
        }
    }


    //获取手机图片
    public void getpic(int flag) {
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        Uri uri = Uri.fromFile(file);
        int size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        CropOptions cropOptions = new CropOptions.Builder().setOutputX(size).setOutputX(size).setWithOwnCrop(false).create();
        if (flag == 1) { //相机获取照片并剪裁
            takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
            //相机获取不剪裁
//            takePhoto.onPickFromCapture(uri);
        } else if (flag == 2) { //相册获取照片并剪裁
            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
            // 相册获取不剪裁
//            takePhoto.onPickFromGallery();
        } else if (flag == 3) { //多选，并剪裁
//            takePhoto.onPickMultipleWithCrop(9, cropOptions); //多选，不剪裁 //
            takePhoto.onPickMultiple(1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.perziliao)) {
                getuserinfo();
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.grzxzl);
        EventBus.getDefault().post(databean);
        EventBus.getDefault().unregister(this);
    }
}
