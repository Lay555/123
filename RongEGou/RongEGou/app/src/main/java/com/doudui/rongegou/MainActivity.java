package com.doudui.rongegou;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.HomePage.bandingwxh;
import com.doudui.rongegou.LoginAct.AesUtil;
import com.doudui.rongegou.LoginAct.Loginact;
import com.doudui.rongegou.Order.order;
import com.doudui.rongegou.Order.payinfoact;
import com.doudui.rongegou.Percenter.Address.quyussq;
import com.doudui.rongegou.Search.Search1;
import com.doudui.rongegou.fragment_.APOrder;
import com.doudui.rongegou.fragment_.HomePage;
import com.doudui.rongegou.fragment_.Personal;
import com.doudui.rongegou.fragment_.shenji;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import baseTools.Cannotscroll_viewpager;
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

public class MainActivity extends FragmentActivity implements View.OnClickListener, quyussq.setSSq {
//haha
    @BindView(R.id.mainact_vp)
    Cannotscroll_viewpager mainactVp;
    @BindView(R.id.mainact_ivsy)
    ImageView mainactIvsy;
    @BindView(R.id.mainact_tesy)
    TextView mainactTesy;
    @BindView(R.id.mainact_sy)
    LinearLayout mainactSy;
    @BindView(R.id.mainact_ivfl)
    ImageView mainactIvfl;
    @BindView(R.id.mainact_tefl)
    TextView mainactTefl;
    @BindView(R.id.mainact_fl)
    LinearLayout mainactFl;
    @BindView(R.id.mainact_ivxd)
    ImageView mainactIvxd;
    @BindView(R.id.mainact_texd)
    TextView mainactTexd;
    @BindView(R.id.mainact_xd)
    LinearLayout mainactXd;
    @BindView(R.id.mainact_ivwd)
    ImageView mainactIvwd;
    @BindView(R.id.mainact_tewd)
    TextView mainactTewd;
    @BindView(R.id.mainact_wd)
    LinearLayout mainactWd;

    Unbinder unbinder;
    public static File file;

    ImageView ivCurrent;
    TextView tvCurrent;
    private long exitTime = 0;
    private List<Fragment> fragment = new ArrayList<Fragment>();

    quyussq quyussq = new quyussq();

    SharedPreferences preferences;
    retro_intf serivce = retrofit_single.getInstence().getserivce(1);
    com.doudui.rongegou.fragment_.shenji shenji = new shenji();

    bandingwxh bdwx = new bandingwxh();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ImmersionBar.with(this).statusBarColor(R.color.bai).statusBarDarkFont(true, 0.0f).fitsSystemWindows(true).init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        setviewdata();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        if (click != null) {
            //从推送通知栏打开-Service打开Activity会重新执行Laucher流程
            //查看是不是全新打开的面板
            if (isTaskRoot()) {
                return;
            }
            finish();
        }
    }

    private void setviewdata() {
        getbb();

        fragment.add(new HomePage());
        fragment.add(new APOrder());
        fragment.add(new Personal());

        mainactSy.setOnClickListener(this);
        mainactFl.setOnClickListener(this);
        mainactXd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!preferences.getString("user_id", "0").equals("0")) {
                    Intent i = new Intent(MainActivity.this, Search1.class);
                    i.putExtra("value", "");
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                } else {
                    Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT);
                    Intent i = new Intent();
                    i.putExtra("type", "1");
                    i.setClass(MainActivity.this, Loginact.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);

                }
            }
        });
        mainactWd.setOnClickListener(this);

        mainactIvsy.setSelected(true);
        mainactTesy.setSelected(true);
        ivCurrent = mainactIvsy;
        tvCurrent = mainactTesy;

        mainactVp.setAdapter(new FragmentStatePagerAdapter(MainActivity.this
                .getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragment.get(arg0);
            }
        });
        mainactVp.setOffscreenPageLimit(fragment.size());
        mainactVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                changeTab(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次，退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {

            finish();
            System.exit(0);
        }
    }


    @Override
    public void onClick(View v) {
        if (!preferences.getString("user_id", "0").equals("0"))
            changeTab(v.getId());
        if (!preferences.getString("user_id", "0").equals("0")) {
            getdatatoken();
        } else {
            Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT);
            Intent i = new Intent();
            i.putExtra("type", "1");
            i.setClass(MainActivity.this, Loginact.class);
            startActivity(i);
            overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }
    }

    private void changeTab(int id) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {
            case R.id.mainact_sy:
                ImmersionBar.with(this).statusBarColor(R.color.bai).statusBarDarkFont(true, 0.0f).fitsSystemWindows(true).init();
                mainactVp.setCurrentItem(0, false);
            case 0:
                mainactIvsy.setSelected(true);
                ivCurrent = mainactIvsy;
                mainactTesy.setSelected(true);
                tvCurrent = mainactTesy;
                break;

            case R.id.mainact_fl:
                ImmersionBar.with(this).statusBarColor(R.color.bai).statusBarDarkFont(true, 0.0f).fitsSystemWindows(true).init();
                mainactVp.setCurrentItem(1, false);
            case 1:

                mainactIvfl.setSelected(true);
                ivCurrent = mainactIvfl;
                mainactTefl.setSelected(true);
                tvCurrent = mainactTefl;
                break;

            case R.id.mainact_wd:
                ImmersionBar.with(this).statusBarColor(R.color.hon).statusBarDarkFont(false, 0.0f).fitsSystemWindows(true).init();

                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.grzxzl);
                EventBus.getDefault().post(databean);

                mainactVp.setCurrentItem(2, false);
            case 2:

                mainactIvwd.setSelected(true);
                ivCurrent = mainactIvwd;
                mainactTewd.setSelected(true);
                tvCurrent = mainactTewd;
                break;
            default:
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getmess(paramsDataBean data) {
        if (data != null) {
            if (data.getMsg().equals(configParams.syxdssq)) {
                if (!quyussq.isAdded())
                    quyussq.show(getFragmentManager(), "qy");
                return;
            } else if (data.getMsg().equals(configParams.sypage1)) {
                mainactVp.setCurrentItem(0, false);
                return;
            } else if (data.getMsg().equals(configParams.gtoken)) {
                getdatatoken();
                return;
            } else if (data.getMsg().equals(configParams.duizhang)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(MainActivity.this, payinfoact.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.anim_show,
                                R.anim.anim_dismiss);
                    }
                }, 1000);

                return;
            } else if (data.getMsg().equals(configParams.duizhang1)) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(MainActivity.this, order.class);
                        i.putExtra("pos", 3);
                        startActivity(i);
                        overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        startActivity(i);
                        overridePendingTransition(R.anim.anim_show,
                                R.anim.anim_dismiss);
                    }
                }, 1000);

                return;
            } else if (data.getMsg().equals(configParams.banndingwxh)) {
                if (bdwx != null) {
                    if (!bdwx.isAdded()) {
                        bdwx.show(getFragmentManager(), "wxbd");
                    }
                }

                return;
            }
        }
    }

    @Override
    public void setSSq(String ids, String ssqstr) {
        String str = ssqstr + "," + ids;
        paramsDataBean databean = new paramsDataBean();
        databean.setMsg(configParams.syxdssq1);
        databean.setT(str);
        EventBus.getDefault().post(databean);
    }

    public void getbb() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "GetUpdateAndroid");
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
                        JSONArray jsa = jsonObject.getJSONArray("Value");
                        JSONObject jso = jsa.getJSONObject(0);
                        int version = jso.getInt("versioncode");
                        final String url = jso.getString("url");
//                        System.out.println(jsonObject + "");
                        Log.v("",jsonObject+"");
                        if (version > getVersion1())
                            if (!shenji.isAdded()) {
//                            String url="",bbh="", txt = "",type=2;
                                Bundle bundle = new Bundle();
                                bundle.putString("url", jso.getString("url"));
                                bundle.putString("bbh", "V" + jso.getString("version"));
                                bundle.putString("txt", jso.getString("comment"));
                                bundle.putString("type", jso.getString("type"));
                                shenji.setArguments(bundle);
//                                shenji.show(getSupportFragmentManager(), "bbgx");
//                                升级
                                shenji.setShejiClick(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        quanxian(url);
                                    }
                                }).show(getSupportFragmentManager(), "");
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

    public void quanxian(final String url) {
        String qx = Manifest.permission.READ_EXTERNAL_STORAGE;
        boolean hanpermissions = PermissionsUtil.hasPermission(MainActivity.this, qx);
        if (hanpermissions) {
            setDownLoad(url);
        } else {
            PermissionsUtil.requestPermission(MainActivity.this, new PermissionListener() {
                //权限被授权
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    setDownLoad(url);
                }

                //权限被拒绝
                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    toaste_ut("你没有开启存储权限，无法更新下载");

                }
            }, Manifest.permission.READ_EXTERNAL_STORAGE);

        }

    }

    @SuppressLint("SdCardPath")
    public void setDownLoad(String url) {
        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);//断点下载
        params.setSaveFilePath("/mnt/sdcard/demo.apk");
        x.http().get(params, new org.xutils.common.Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
               /* if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }*/
                toaste_ut("下载完成开始安装");
                if (Build.VERSION.SDK_INT >= 24) {// 判读版本是否在7.0以上
                    file = new File(getSDPath(MainActivity.this), "demo.apk");
                    Uri apkUri = FileProvider.getUriForFile(MainActivity.this, "com.doudui.rongegou.fileprovider", file);// 在AndroidManifest中的android:authorities值
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setDataAndType(Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(), "demo.apk")),
                            "application/vnd.android.package-archive");
                    startActivity(intent);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                toaste_ut("提示更新失败");

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                /*System.out.println("开始下载");
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置为水平进行条
                progressDialog.setMessage("正在下载中...");
                progressDialog.setProgress(0);
                progressDialog.show();
*/
            }

            @Override
            public void onLoading(long arg0, long arg1, boolean isDownloading) {
              /*  progressDialog.setMax((int) arg0);
                progressDialog.setProgress((int) arg1);
                System.out.println( "1234");*/
                shenji.setPro((arg1 * 100 / arg0) + "%");
            }
        });
    }


    protected void toaste_ut(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }

    /**
     * 获取路径
     *
     * @param context
     * @return 路径
     */
    public static String getSDPath(Context context) {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();// 获取根目录
        } else {
            return context.getCacheDir().getAbsolutePath(); // 获取内置内存卡目录
        }
    }

    public int getVersion1() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    MainActivity.this.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }


    public void getdatatoken() {
        retro_intf serivce = retrofit_single.getInstence().getserivce(1);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        try {
            jsonObject1.put("user_id", preferences.getString("user_id", "0"));
            jsonObject1.put("device_token", preferences.getString("device_token", "0"));//需要存一下
            jsonObject.put("Data", jsonObject1);
            jsonObject.put("MethodName", "SetDeviceToken");
            jsonObject.put("ModuleName", "UserAccount");
            jsonObject.put("Token", AesUtil.aesEncrypt(time, "12345678876543211234567887654abc"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
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


                    } else if (jsonObject.getString("Status").equals("1101")) {
                        getdatatoken();
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

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
