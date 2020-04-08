package com.doudui.rongegou.Order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.BuildConfig;
import com.doudui.rongegou.Goods.guigefrag;
import com.doudui.rongegou.Goods.sharesdkui;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Percenter.Perziliao.getpicdialogfragment;
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
import com.mob.tools.utils.FileUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class order_shouhou extends BaseActivity_ implements TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao {

    @BindView(R.id.shouhou_top)
    DaoHang_top shouhouTop;
    @BindView(R.id.shouhou_tetop1)
    TextView shouhouTetop1;
    @BindView(R.id.shouhou_tetop2)
    TextView shouhouTetop2;
    @BindView(R.id.shouhou_tetop3)
    TextView shouhouTetop3;
    @BindView(R.id.shouhou_tetop4)
    TextView shouhouTetop4;
    @BindView(R.id.shouhou_tetop5)
    TextView shouhouTetop5;
    @BindView(R.id.shouhou_tetop6)
    TextView shouhouTetop6;
    @BindView(R.id.shouhou_imsp)
    ImageView shouhouImsp;
    @BindView(R.id.shouhou_tename)
    TextView shouhouTename;
    @BindView(R.id.shouhou_tegg)
    TextView shouhouTegg;
    @BindView(R.id.shouhou_teprice)
    TextView shouhouTeprice;
    @BindView(R.id.shouhou_tenum)
    TextView shouhouTenum;
    @BindView(R.id.ordde_tealprice)
    TextView orddeTealprice;
    @BindView(R.id.ordde_teyufei)
    TextView orddeTeyufei;
    @BindView(R.id.heka_edheka)
    EditText hekaEdheka;
    @BindView(R.id.heka_tezi)
    TextView hekaTezi;
    @BindView(R.id.heka_reinp)
    RelativeLayout hekaReinp;
    @BindView(R.id.pinjia_imtp1)
    ImageView pinjiaImtp1;
    @BindView(R.id.pinjia_imtp2)
    ImageView pinjiaImtp2;
    @BindView(R.id.pinjia_imtp3)
    ImageView pinjiaImtp3;
    @BindView(R.id.pinjia_imtp4)
    ImageView pinjiaImtp4;
    @BindView(R.id.addressit_teadd)
    TextView addressitTeadd;

    sharesdkui sharesdkunew = new sharesdkui();

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    com.doudui.rongegou.Percenter.Perziliao.getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();

    int pos = 1;//1234
    String[] paizaourl = {
            "", "", "", ""};
    String orderId = "";

    String type = "1";//1是订单列表进入，2是订单详情
    String type_shouhou = "";
    String[] sharetxt = new String[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void AddView() {
        shouhouTop.settext_("申请售后");
        shouhouTop.setvis1();
        Glide.with(this).load(R.mipmap.share).into(shouhouTop.im_d);
        orderId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");

        type_shouhou = getIntent().getStringExtra("type_shouhou");//afterstate:售后状态（0售后处理中，1售后完成，1申请售后/未提交售后）,
        if (type_shouhou.equals("0")) {
            addressitTeadd.setText("售后处理中");
            addressitTeadd.setEnabled(false);
        } else if (type_shouhou.equals("1")) {
            addressitTeadd.setText("完成售后");
            addressitTeadd.setEnabled(false);
        } else if (type_shouhou.equals("-1")) {
            addressitTeadd.setText("申请售后");
        }

        getdata();
    }

    @Override
    protected void SetViewListen() {
        hekaEdheka.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hekaTezi.setText((200 - hekaEdheka.getText().toString().length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pinjiaImtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 1;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        pinjiaImtp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 2;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        pinjiaImtp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 3;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        pinjiaImtp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 4;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        addressitTeadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                for (int i = 0; i < paizaourl.length; i++) {
                    if (paizaourl[i].length() >= 1) {
                        url += paizaourl[i];
                    }
                }
                if (url.length() >= 2)
                    url = url.substring(0, url.length() - 1);

                if (!TextUtils.isEmpty(hekaEdheka.getText().toString()))
                    getdata_applay(getSharePre("user_id", order_shouhou.this), hekaEdheka.getText().toString(), url);
                else toaste_ut(order_shouhou.this, "请输入问题描述");
            }
        });
        shouhouTop.im_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sharesdkunew != null)
                    if (!sharesdkunew.isAdded() && !sharesdkunew.isVisible()
                            && !sharesdkunew.isRemoving()) {
                        if (!TextUtils.isEmpty(sharetxt[0])) {
                            Bundle bundle = new Bundle();
                            bundle.putString("title", sharetxt[0]);
                            bundle.putString("txt", sharetxt[1]);
                            bundle.putString("sptp", sharetxt[2]);//icon缺少url
                            bundle.putString("url", sharetxt[3]);
                            bundle.putString("type", "1");//新增type参数，等于1的时候隐藏海报分享功能
                            sharesdkunew.setArguments(bundle);
                            sharesdkunew.show(getSupportFragmentManager(), "xx");
                        }
                    }
            }

        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_order_shouhou;
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
                        public void onSuccess(final File file) {
                            if (jdt.isAdded())
                                jdt.dismiss();

                            if (pos == 1) {
                                Glide.with(order_shouhou.this).load(file).into(pinjiaImtp1);
                            } else if (pos == 2) {
                                Glide.with(order_shouhou.this).load(file).into(pinjiaImtp2);
                            } else if (pos == 3) {
                                Glide.with(order_shouhou.this).load(file).into(pinjiaImtp3);
                            } else if (pos == 4) {
                                Glide.with(order_shouhou.this).load(file).into(pinjiaImtp4);
                            }

                            try {
                                getdata_upimg(encodeBase64File(file));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
//            takePhoto.onPickFromCaptureWithCrop(uri, cropOptions);
            //相机获取不剪裁
            takePhoto.onPickFromCapture(uri);
        } else if (flag == 2) { //相册获取照片并剪裁
//            takePhoto.onPickFromGalleryWithCrop(uri, cropOptions);
            // 相册获取不剪裁
            takePhoto.onPickFromGallery();
        } else if (flag == 3) { //多选，并剪裁
//            takePhoto.onPickMultipleWithCrop(9, cropOptions); //多选，不剪裁 //
            takePhoto.onPickMultiple(1);
        }
    }

    public void getdata() {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "addr");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("orderId", orderId);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetAftersales");
            jsonObject.put("ModuleName", "OrderManage");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

            System.out.println(jsonObject);

//            System.out.println(AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
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
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());

                    if (jsonObject.getString("Status").equals("1")) {
                        JSONObject jso = jsonObject.getJSONObject("Value");
                        shouhouTetop1.setText("订单编号：  " + jso.getString("order_no"));
                        shouhouTetop2.setText("社长编号：  " + jso.getString("buyer_email"));
                        shouhouTetop3.setText("收货人：  " + jso.getString("Receiver"));
                        shouhouTetop4.setText("收货地址：  " + jso.getString("areainfo"));
                        shouhouTetop5.setText("快递单号：  " + jso.getString("exp_no"));
                        shouhouTetop6.setText("下单时间：  " + jso.getString("create_time"));

                        if (getSharePre("isvip", order_shouhou.this).equals("0"))
                            sharetxt[0] = "售后,社长编号：  " + jso.getString("buyer_email");
                        else sharetxt[0] = "售后,社长编号：  VIP" + jso.getString("buyer_email");

                        Glide.with(order_shouhou.this).load(jso.getString("goods_image")).into(shouhouImsp);
                        shouhouTegg.setText(jso.getString("specnames"));
                        shouhouTename.setText(jso.getString("goods_name"));
                        shouhouTeprice.setText("￥" + jso.getString("goods_amount"));
                        shouhouTenum.setText("X" + jso.getString("goods_num"));

                        sharetxt[1] = jso.getString("goods_name");
                        sharetxt[2] = "http://rongegou.oss-cn-beijing.aliyuncs.com/Other/rongegou-logo.png";
                        sharetxt[3] = BuildConfig.BASEURLFX+"/CustomerStore/aftersale.aspx?order_id=" + orderId;

                        orddeTealprice.setText("￥" + jso.getString("goods_amount"));
                        orddeTeyufei.setText("￥" + jso.getString("trans_amount"));

                        hekaEdheka.setText(jso.getString("questions"));
                        JSONArray jsaurl = jso.getJSONArray("images");
                        if (jsaurl.length() >= 1) {
                            for (int i = 0; i < jsaurl.length(); i++) {
                                if (i == 0) {
                                    Glide.with(order_shouhou.this).load(jsaurl.getString(0).toString()).into(pinjiaImtp1);
                                } else if (i == 1) {
                                    Glide.with(order_shouhou.this).load(jsaurl.getString(0).toString()).into(pinjiaImtp2);
                                } else if (i == 2) {
                                    Glide.with(order_shouhou.this).load(jsaurl.getString(0).toString()).into(pinjiaImtp3);
                                } else if (i == 4) {
                                    Glide.with(order_shouhou.this).load(jsaurl.getString(0).toString()).into(pinjiaImtp4);
                                }
                            }
                        }
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata();
                    } else
                        toaste_ut(order_shouhou.this, jsonObject.getString("Details"));
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

    public void getdata_upimg(final String str) {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "addr");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("filebases", str);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "UploadImages");
            jsonObject.put("ModuleName", "OrderManage");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

//            System.out.println(AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));
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
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        if (pos == 1) {
                            paizaourl[0] = jsonObject.getString("Value");
                        } else if (pos == 2) {
                            paizaourl[1] = jsonObject.getString("Value");
                        } else if (pos == 3) {
                            paizaourl[2] = jsonObject.getString("Value");
                        } else if (pos == 4) {
                            paizaourl[3] = jsonObject.getString("Value");
                        }
                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata_upimg(str);
                    } else
                        toaste_ut(order_shouhou.this, jsonObject.getString("Details"));
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

    public void getdata_applay(String userid, String text, final String str) {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "addr");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("userid", userid);
            jsonObject1.put("orderId", orderId);
            jsonObject1.put("text", text);
            jsonObject1.put("filenames", str);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SaveAftersales");
            jsonObject.put("ModuleName", "OrderManage");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

//            System.out.println(jsonObject);
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
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getString("Status").equals("1")) {
                        toaste_ut(order_shouhou.this, "申请售后提交成功");
                        paramsDataBean databean = new paramsDataBean();
                        databean.setMsg(configParams.orderlist);
                        EventBus.getDefault().post(databean);
                        finish();
                        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);

                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata_upimg(str);
                    } else
                        toaste_ut(order_shouhou.this, jsonObject.getString("Details"));
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


}
