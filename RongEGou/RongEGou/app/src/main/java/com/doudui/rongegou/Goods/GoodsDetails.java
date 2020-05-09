package com.doudui.rongegou.Goods;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.Goods.compat.ContextCompat;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
import baseTools.configParams;
import baseTools.lunbo.ADInfo;
import baseTools.lunbo.CycleViewPager;
import baseTools.lunbo.ViewFactory;
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

public class GoodsDetails extends BaseActivity_ {


    //12
    @BindView(R.id.gdt_imfh)
    ImageView gdtImfh;
    @BindView(R.id.gdt_imfx)
    ImageView gdtImfx;
    @BindView(R.id.gdt_refrag)
    RelativeLayout gdtRefrag;
    @BindView(R.id.gdt_teprice)
    TextView gdtTeprice;
    @BindView(R.id.gdt_tetxt)
    TextView gdtTetxt;
    @BindView(R.id.gdt_tegg)
    TextView gdtTegg;
    @BindView(R.id.gdt_lingg)
    LinearLayout gdtLingg;
    @BindView(R.id.gdt_web)
    WebView gdtWeb;
    @BindView(R.id.gdt_webmajiaxiu)
    WebView gdtWebmajiaxiu;

    @BindView(R.id.addressit_teadd)
    TextView addressitTeadd;

    @BindView(R.id.te_spxq)
    TextView te_spxq;
    @BindView(R.id.te_spmajiaxiu)
    TextView te_majiaxiu;
    @BindView(R.id.recyc_pics)
    RecyclerView recyc_pics;

    @BindView(R.id.gdt_nomaijiaxiu)
    View v_nomjxiu;

    sharesdkui sharesdkunew = new sharesdkui();
    sharesdkui1 sharesdkunew1 = new sharesdkui1();

    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    CycleViewPager cycleViewPager;
    private String[] imageUrls = {"", "", ""};// 轮播图test
    private String[] url_web;// 轮播图要跳转的网页
    private String[] url_webtit;// 轮播图要跳转的网页的标题
    private String[] goodsid_bin;// 轮播图要跳转的网页的标题

    String haibaourl = "";
    Bitmap bt = null;//保存图片到本地

    guigefrag guigefrag;
    String goodsid = "", goods_desc = "", goods_price = "", goods_kucun = "", goodsname = "", isyugou = "";

    maijiaxiuada ada;
    String maijiaxiu = "";
    List<majiaxiudata> list = new ArrayList<>();

    @Override
    protected void AddView() {
        EventBus.getDefault().register(this);
        guigefrag = new guigefrag();
        Intent i = getIntent();
        goodsid = i.getStringExtra("goodsid");//
        if (i.getStringExtra("isyugou") != null)
            isyugou = i.getStringExtra("isyugou");//pre_purchase

        te_spxq.setSelected(true);

        ada = new maijiaxiuada(list, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyc_pics.setAdapter(ada);
        recyc_pics.setLayoutManager(manager);

        getdata();
        getdata_haibao();
    }

    @Override
    protected void SetViewListen() {
        gdtImfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
        gdtLingg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(goods_price)) {
                    if (!guigefrag.isAdded()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("gdid", goodsid);
                        bundle.putString("gdpic", imageUrls[0]);
                        bundle.putString("gprice", goods_price);
                        bundle.putString("goodsname", goodsname);
                        bundle.putString("isyugou", isyugou);
                        guigefrag.setArguments(bundle);
                        guigefrag.show(getSupportFragmentManager(), "xx");
                    }
                }
            }
        });
        gdtWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
        gdtImfx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharesdkunew != null)
                    if (!sharesdkunew.isAdded() && !sharesdkunew.isVisible()
                            && !sharesdkunew.isRemoving()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("gdid", goodsid);
                        bundle.putString("hb", haibaourl);
                        bundle.putString("goodsname", "N0." + getSharePre(("user_email"), GoodsDetails.this) + "嵘e购在售商品(" + goodsname + ")");
                        bundle.putString("user_id", getSharePre("user_id", GoodsDetails.this));
                        bundle.putString("sptp", imageUrls[0]);
                        bundle.putString("type", "0");//新增type参数，等于1的时候隐藏海报分享功能
                        sharesdkunew.setArguments(bundle);
                        sharesdkunew.show(getSupportFragmentManager(), "xx");
                    }

            }
        });

        te_spxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_spxq.isSelected()) {
                    te_majiaxiu.setSelected(false);
                    te_spxq.setSelected(true);
                    gdtWeb.setVisibility(View.VISIBLE);
                    gdtWebmajiaxiu.setVisibility(View.GONE);
                    v_nomjxiu.setVisibility(View.GONE);
                }
            }
        });

        te_majiaxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!te_majiaxiu.isSelected()) {
                    te_spxq.setSelected(false);
                    te_majiaxiu.setSelected(true);
                    if (TextUtils.isEmpty(maijiaxiu)) {
                        v_nomjxiu.setVisibility(View.VISIBLE);
                        gdtWebmajiaxiu.setVisibility(View.GONE);
                    } else {
                        gdtWebmajiaxiu.setVisibility(View.VISIBLE);
                        v_nomjxiu.setVisibility(View.GONE);
                    }
                    gdtWeb.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_goods_details;
    }

    @OnClick({R.id.gdt_lingg, R.id.addressit_teadd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gdt_lingg:
                break;
            case R.id.addressit_teadd:
                if (!TextUtils.isEmpty(goods_price)) {
                    if (!guigefrag.isAdded()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("gdid", goodsid);
                        bundle.putString("gdpic", imageUrls[0]);
                        bundle.putString("gprice", goods_price);
                        bundle.putString("goodsname", goodsname);
                        bundle.putString("isyugou", isyugou);
                        guigefrag.setArguments(bundle);
                        guigefrag.show(getSupportFragmentManager(), "xx");
                    }
                }
                break;
        }
    }


    public void getdata() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("goods_id", goodsid);
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetGoodsDetails");
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
                    System.out.println(jsonObject);
                    if (jsonObject.getString("Status").equals("1")) {
                        JSONArray jsa_ = jsonObject.getJSONArray("Value");
                        JSONObject jso = jsa_.getJSONObject(0);
                        String lunbostr = jso.getString("goods_bigimages");
                        if (lunbostr.contains("|")) {
                            imageUrls = lunbostr.split("\\|");
                        } else {
                            imageUrls = new String[1];
                            imageUrls[0] = lunbostr;
                        }
                        initialize(getFragmentManager(), GoodsDetails.this);
                        gdtTetxt.setText(jso.getString("goods_name"));
                        goodsname = jso.getString("goods_name");
                        goods_price = jso.getString("goods_store_price");

                        Spannable span = new SpannableString("￥" + jso.getString("goods_store_price") + "  福利价");
                        span.setSpan(new AbsoluteSizeSpan(11, true), 0, 1,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        span.setSpan(new AbsoluteSizeSpan(17, true), 1, jso.getString("goods_store_price").length() + 1,
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        gdtTeprice.setText(span);
                        goods_desc = jso.getString("goods_body");
                        setviewweb(gdtWeb, goods_desc);

                        maijiaxiu = jso.getString("StrThree");

                        setviewweb(gdtWebmajiaxiu, maijiaxiu);

                        if (TextUtils.isEmpty(maijiaxiu)) {
                            v_nomjxiu.setVisibility(View.VISIBLE);
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

    public void getdata_haibao() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("goods_id", goodsid);
            jsonObject1.put("user_id", getSharePre("user_id", GoodsDetails.this));
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetSharePicture");
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
                    if (jsonObject.getString("Status").equals("1")) {
                        haibaourl = jsonObject.getString("Value");
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


    private void setviewweb(WebView gdtWeb, String goods_desc) {
        String st = "<style type=\"text/css\"> img{ width: 100%; height: auto; display: block; padding:0;margin:0;} p{padding:0;margin:0;} </style> ";
        gdtWeb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        WebSettings settings = gdtWeb.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        gdtWeb.getSettings().setDomStorageEnabled(true);
        gdtWeb.getSettings().setJavaScriptEnabled(true);
        gdtWeb.getSettings().setBlockNetworkImage(false);
        gdtWeb.setVerticalScrollBarEnabled(false);
        gdtWeb.setHorizontalScrollBarEnabled(false);
        gdtWeb.getSettings().setLoadWithOverviewMode(true);
        gdtWeb.loadDataWithBaseURL(null, st + goods_desc, "text/html", "UTF-8",
                null);
    }

    /**
     * 轮播图片
     */
    public void initialize(FragmentManager fragment, Context context) {
        cycleViewPager = (CycleViewPager) fragment
                .findFragmentById(R.id.gdt_viewpagerlunbos);

        if (cycleViewPager != null) {
            views.removeAll(views);
            infos.removeAll(infos);

            for (int i = 0; i < imageUrls.length; i++) {
                ADInfo info = new ADInfo();
                info.setUrl(imageUrls[i]);
                info.setContent("图片-->" + i);
                infos.add(info);
            }
            // 将最后一个ImageView添加进来
            views.add(ViewFactory.getImageView(context,
                    infos.get(infos.size() - 1).getUrl()));
            for (int i = 0; i < infos.size(); i++) {
                views.add(ViewFactory.getImageView(context, infos.get(i)
                        .getUrl()));
            }
            // 将第一个ImageView添加进来
            views.add(ViewFactory
                    .getImageView(context, infos.get(0).getUrl()));
            // 设置循环，在调用setData方法前调用
            cycleViewPager.setCycle(true);

            // 在加载数据前设置是否循环
            cycleViewPager.setData(views, infos, mAdCycleViewListener);
            // 设置轮播
            cycleViewPager.setWheel(true);

            // 设置轮播时间，默认4500ms
            cycleViewPager.setTime(4000);
            // 设置圆点指示图标组居中显示，默认靠右
            cycleViewPager.setIndicatorCenter();
        }
    }

    /**
     * 轮播点击监听
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                int pos = position - 1;

            }

        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.share1)) {
                if (!sharesdkunew1.isAdded()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("gdid", goodsid);
                    bundle.putString("hb", haibaourl);
                    bundle.putString("goodsname", goodsname);
                    bundle.putString("sptp", imageUrls[0]);
                    bundle.putString("user_id", getSharePre("user_id", GoodsDetails.this));
                    sharesdkunew1.setArguments(bundle);
                    sharesdkunew1.show(getSupportFragmentManager(), "xx");
                }
            } else if (data != null) {
                if (data.getMsg().equals(configParams.share2)) {
                    bt = (Bitmap) data.getT();
                    getpermission();
                    if (sharesdkunew1.isAdded()) {
                        sharesdkunew1.dismiss();
                    }
                }
            }
        }
    }


    public void saveImageToGallery(Context context, Bitmap bmp) {

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "QRCode");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        System.out.println(fileName);
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(GoodsDetails.this, "保存成功", Toast.LENGTH_SHORT).show();
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 判断SDK版本是不是4.4或者高于4.4
            String[] paths = new String[]{file.getAbsolutePath()};
            MediaScannerConnection.scanFile(context, paths, null, null);
        } else {
            final Intent intent;
            if (file.isDirectory()) {
                intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            } else {
                intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
            }
            context.sendBroadcast(intent);
        }
    }

    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private void getpermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        PERMISSIONS_STORAGE,
                        1);
            } else {
                saveImageToGallery(GoodsDetails.this, bt);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImageToGallery(GoodsDetails.this, bt);
            } else {
                Toast.makeText(GoodsDetails.this, "请设置app允许读写权限，然后重试", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
