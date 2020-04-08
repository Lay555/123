package com.doudui.rongegou.Percenter.Gonyinshang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.Order.order_shouhou;
import com.doudui.rongegou.Percenter.Gonyinshang.tbcpada.recycada_;
import com.doudui.rongegou.Percenter.Gonyinshang.tbcpada.recycada_data;
import com.doudui.rongegou.Percenter.Gonyinshang.tbcpada.tbcg;
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

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import baseTools.retrofit2base.retro_intf;
import baseTools.retrofit2base.retrofit_single;
import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class tibaocpin extends BaseActivity_ implements TakePhoto.TakeResultListener, InvokeListener, getpicdialogfragment.paizao, tbcg.mappos {

    @BindView(R.id.tbcp_fh)
    DaoHang_top tbcpFh;
    @BindView(R.id.tbcp_recyc)
    RecyclerView tbcpRecyc;
    @BindView(R.id.tbcp_edname)
    EditText tbcpEdname;
    @BindView(R.id.tbcp_edzuhe)
    EditText tbcpEdzuhe;
    @BindView(R.id.salevalue_imtp1)
    ImageView salevalueImtp1;
    @BindView(R.id.salevalue_imtp2)
    ImageView salevalueImtp2;
    @BindView(R.id.salevalue_imtp3)
    ImageView salevalueImtp3;
    @BindView(R.id.salevalue_imtp4)
    ImageView salevalueImtp4;
    @BindView(R.id.tbcp_edprice)
    EditText tbcpEdprice;
    @BindView(R.id.tbcp_edkcun)
    EditText tbcpEdkcun;
    @BindView(R.id.tbcp_edbuhuo)
    EditText tbcpEdbuhuo;
    @BindView(R.id.tbcp_edmiaoshu)
    EditText tbcpEdmiaoshu;
    @BindView(R.id.tbcp_tenum)
    TextView tbcpTenum;
    @BindView(R.id.heka_reinp)
    RelativeLayout hekaReinp;
    @BindView(R.id.salevalue_imtpc1)
    ImageView salevalueImtpc1;
    @BindView(R.id.salevalue_imtpc2)
    ImageView salevalueImtpc2;
    @BindView(R.id.salevalue_imtpc3)
    ImageView salevalueImtpc3;
    @BindView(R.id.salevalue_imtpc4)
    ImageView salevalueImtpc4;
    @BindView(R.id.tbcp_edscrq)
    EditText tbcpEdscrq;
    @BindView(R.id.tbcp_edbzq)
    EditText tbcpEdbzq;
    @BindView(R.id.tbcp_edzczl)
    EditText tbcpEdzczl;
    @BindView(R.id.tbcp_edbz)
    EditText tbcpEdbz;
    @BindView(R.id.tbcp_tj)
    TextView tbcpTj;

    recycada_ ada;
    List<recycada_data> list = new ArrayList<>();

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    com.doudui.rongegou.Percenter.Perziliao.getpicdialogfragment getpicdialogfragment = new getpicdialogfragment();
    int pos = 1;//12345678
    String[] paizaourl = {
            "", "", "", ""};
    String[] paizaourl1 = {
            "", "", "", ""};
    String user_id = "";
    tbcg tbcg = new tbcg();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void AddView() {
        tbcpFh.settext_("提报产品");

        user_id = getSharePre("user_id", this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);

        list.add(new recycada_data("26", "家居用品", "1"));
        list.add(new recycada_data("28", " 家纺", "0"));
        list.add(new recycada_data("27", "厨房用品", "0"));
        list.add(new recycada_data("29", "服装", "0"));
        list.add(new recycada_data("30", "小家电", "0"));
        list.add(new recycada_data("31", "食品", "0"));
        list.add(new recycada_data("32", "保健品", "0"));
        list.add(new recycada_data("33", "美妆护肤", "0"));
        list.add(new recycada_data("34", "收藏装饰", "0"));
        ada = new recycada_(this, list);
        tbcpRecyc.setLayoutManager(manager);
        tbcpRecyc.setAdapter(ada);
    }

    @Override
    protected void SetViewListen() {
        salevalueImtp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 1;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        salevalueImtp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 2;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        salevalueImtp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 3;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        salevalueImtp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 4;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });

        salevalueImtpc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 5;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        salevalueImtpc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 6;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        salevalueImtpc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 7;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });
        salevalueImtpc4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getpicdialogfragment.isAdded()) {
                    pos = 8;
                    getpicdialogfragment.show(getFragmentManager(), "pz");
                }
            }
        });

        tbcpEdmiaoshu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tbcpTenum.setText((300 - tbcpEdmiaoshu.getText().toString().length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tbcpTj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tbcpEdname.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入产品名称");
                    return;
                }
                if (TextUtils.isEmpty(tbcpEdzuhe.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入产品组合");
                    return;
                }
                String url1 = "";
                for (int i = 0; i < paizaourl.length; i++) {
                    if (paizaourl[i].length() >= 3) {
                        url1 += paizaourl[i];
                    }
                }
                if (url1.contains("|")) {
                    String[] tt = url1.split("\\|");
                    if (tt.length <= 1) {
                        toaste_ut(tibaocpin.this, "请至少上传2张在售价格页面截图");
                        return;
                    }
                } else {
                    toaste_ut(tibaocpin.this, "请至少上传2张在售价格页面截图");
                    return;
                }

                if (TextUtils.isEmpty(tbcpEdkcun.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入最低库存数量");
                    return;
                }
                if (TextUtils.isEmpty(tbcpEdbuhuo.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入补货周期");
                    return;
                }
                if (tbcpEdmiaoshu.getText().toString().length() < 100) {
                    toaste_ut(tibaocpin.this, "产品买点介绍（不少于100字）");
                    return;
                }

                String url2 = "";
                for (int i = 0; i < paizaourl1.length; i++) {
                    if (paizaourl1[i].length() >= 3) {
                        url2 += paizaourl1[i];
                    }
                }
                if (url2.contains("|")) {
                    String[] tt = url2.split("\\|");
                    if (tt.length <= 1) {
                        toaste_ut(tibaocpin.this, "请至少上传2张产品图片");
                        return;
                    }
                } else {
                    toaste_ut(tibaocpin.this, "请至少上传2张产品图片");
                    return;
                }
                if (TextUtils.isEmpty(tbcpEdscrq.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入生产日期");
                    return;
                }
                if (TextUtils.isEmpty(tbcpEdbzq.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入保质期");
                    return;
                }
                if (TextUtils.isEmpty(tbcpEdzczl.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入组合重量");
                    return;
                }
                if (TextUtils.isEmpty(tbcpEdbz.getText().toString())) {
                    toaste_ut(tibaocpin.this, "请输入包装尺寸");
                    return;
                }
                String cplx = "";
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getIsselsec().equals("1")) {
                        cplx = list.get(i).getTxt();
                        break;
                    }
                }
                getdata_applay(cplx, url1, url2);

            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_tibaocpin;
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
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtp1);
                            } else if (pos == 2) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtp2);
                            } else if (pos == 3) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtp3);
                            } else if (pos == 4) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtp4);
                            } else if (pos == 5) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtpc1);
                            } else if (pos == 6) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtpc2);
                            } else if (pos == 7) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtpc3);
                            } else if (pos == 8) {
                                Glide.with(tibaocpin.this).load(file).into(salevalueImtpc4);
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
            jsonObject.put("MethodName", "TibaoImages");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());
        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        if (pos == 1) {
                            paizaourl[0] = jsonObject.getString("Value");
                        } else if (pos == 2) {
                            paizaourl[1] = jsonObject.getString("Value");
                        } else if (pos == 3) {
                            paizaourl[2] = jsonObject.getString("Value");
                        } else if (pos == 4) {
                            paizaourl[3] = jsonObject.getString("Value");
                        } else if (pos == 5) {
                            paizaourl1[0] = jsonObject.getString("Value");
                        } else if (pos == 6) {
                            paizaourl1[1] = jsonObject.getString("Value");
                        } else if (pos == 7) {
                            paizaourl1[2] = jsonObject.getString("Value");
                        } else if (pos == 8) {
                            paizaourl1[3] = jsonObject.getString("Value");
                        }


                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdata_upimg(str);
                    } else
                        toaste_ut(tibaocpin.this, jsonObject.getString("Details"));
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

    public void getdata_applay(String cplx, final String urls1, String urls2) {
        if (!jdt.isAdded())
            jdt.show(getFragmentManager(), "addr");
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("userid", user_id);
            jsonObject1.put("chanpinleixing", cplx);
            jsonObject1.put("chanpinmingcheng", tbcpEdname.getText().toString());
            jsonObject1.put("chanpinzuhe", tbcpEdzuhe.getText().toString());
            jsonObject1.put("images_jietu", urls1);
            jsonObject1.put("gonghuojia", tbcpEdprice.getText().toString());
            jsonObject1.put("zuidikucun", tbcpEdkcun.getText().toString());
            jsonObject1.put("buhuozhouqi", tbcpEdbuhuo.getText().toString());
            jsonObject1.put("maidianjieshao", tbcpEdmiaoshu.getText().toString());
            jsonObject1.put("images_chanpin", urls2);
            jsonObject1.put("shengchanriqi", tbcpEdscrq.getText().toString());
            jsonObject1.put("baozhiqi", tbcpEdbzq.getText().toString());
            jsonObject1.put("zuhezhongliang", tbcpEdzczl.getText().toString());
            jsonObject1.put("baozhuangchicun", tbcpEdbz.getText().toString());
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "Tibaochanpin");
            jsonObject.put("ModuleName", "DataAccess");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toString());
        Call<ResponseBody> call = serivce.getData(requestBody);
        call.enqueue(new Callback<ResponseBody>()

        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (jdt.isAdded()) jdt.dismiss();
                if (response.body() == null)
                    return;
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        if (!tbcg.isAdded()) {
                            tbcg.show(getSupportFragmentManager(), "zz");
                        }

                    } else if (jsonObject.getString("Status").equals("1101")) {
                    } else
                        toaste_ut(tibaocpin.this, jsonObject.getString("Details"));
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

    @Override
    public void mappos() {
        finish();
        overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);
    }
}
