package baseTools;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;

import org.greenrobot.eventbus.EventBus;
import org.xutils.BuildConfig;
import org.xutils.x;

public class App extends MultiDexApplication {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String qd = "";

    @Override
    public void onCreate() {
        super.onCreate();
        //        xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); //是否输出debug日志，开启debug会影响性能。
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "");
        MultiDex.install(this);
        initX5WebView();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        String phone_chan = Build.BRAND.toLowerCase();
        XGPushConfig.enableOtherPush(this, true);
//        if (phone_chan.contains("meizu")) {

        XGPushConfig.setMzPushAppId(this, "117932");
        XGPushConfig.setMzPushAppKey(this, "0cab40fed973464696c1a1a9672d3a9c");
//        } else if (phone_chan.contains("xiaomi")) {
//            qd = "xiaomi";
//            XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761517924655");
//            XGPushConfig.setMiPushAppKey(getApplicationContext(), "5301792418655");
//        } else if (phone_chan.contains("huawei")) {
//            qd = "huawei";
//            XGPushConfig.setHuaweiDebug(true);
//        }

        XGPushConfig.enableDebug(this, false);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                System.out.println("注册成功，设备token为：" + data);
                editor = preferences.edit();
                editor.putString("device_token", data.toString());
                editor.apply();


                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.gtoken);
                EventBus.getDefault().post(databean);

            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
            }
        });

    }


    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    private void initX5WebView() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


}
