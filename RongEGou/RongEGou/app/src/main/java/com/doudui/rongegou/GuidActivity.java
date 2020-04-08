package com.doudui.rongegou;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.doudui.rongegou.LoginAct.Loginact;
import com.gyf.barlibrary.ImmersionBar;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.analytics.MobclickAgent;

import baseTools.Density;

public class GuidActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Density.setDensity(getApplication(), 360);
//        setCustomDensity(this, getApplication());
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//
        preferences = PreferenceManager.getDefaultSharedPreferences(GuidActivity.this);
        setContentView(R.layout.activity_guid);
        if (preferences.getString("user_id", "0").equals("0"))
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(GuidActivity.this, Loginact.class);
                    i.putExtra("type", "0");
                    startActivity(i);
                    overridePendingTransition(R.anim.anim_show,
                            R.anim.anim_dismiss);
                    finish();
                }
            }, 800);
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(GuidActivity.this, MainActivity.class));
                    finish();
                }
            }, 800);

        }

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

    private static float sNoncompatDensity;
    private static float sNoncompatScaleDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        sNoncompatDensity = appDisplayMetrics.density;
        sNoncompatScaleDensity = appDisplayMetrics.scaledDensity;
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    sNoncompatScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                }
            }

            @Override
            public void onLowMemory() {
            }
        });

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaleDensity = targetDensity * (sNoncompatScaleDensity / sNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;

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
