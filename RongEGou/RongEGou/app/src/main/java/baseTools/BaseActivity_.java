package baseTools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.R;
import com.gyf.barlibrary.ImmersionBar;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity_ extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    protected int w_, h_;

    Unbinder mubinder;
    public progressbar_ jdt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jdt = new progressbar_();
//        将显示改成竖屏显示
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayout());
//        DaoHangLan(this);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.bai).statusBarDarkFont(true, 0.0f).init();
        mubinder = ButterKnife.bind(this);
        AddView();
        SetViewListen();
    }


    /**
     * @param context
     * @param cls
     * @param isFinish 跳转方法
     *                 isfinish是否结束act
     */
    protected void startActivityByIntent(Context context, Class<?> cls, Boolean isFinish, Bundle data) {
        Intent i = new Intent();
        i.setClass(context, cls);
        i.putExtras(data);
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
        if (isFinish) {
            finish();
        }
    }

    protected void startActivityByIntent(Context context, Class<?> cls) {
        Intent i = new Intent();
        i.setClass(context, cls);
        startActivity(i);
        overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
    }

    long lastClick = 0;

    /**
     * @return 判断是否快速点击
     * true不是快速点击
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * @param context
     * @param str     提示语显示
     */
    protected void toaste_ut(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    /**
     * @param key
     * @param value
     * @param context 保存键值对
     */
    protected void sharePre(String key, String value, Context context) {

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        if (!TextUtils.isEmpty(value)) {
            editor.putString(key, value);
        }
        editor.apply();

    }

    /**
     * @param key
     * @param context
     * @return 根据key获取值
     */
    protected String getSharePre(String key, Context context) {
        if (preferences==null)
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString(key, "0");
    }

    protected void setTextSize(TextView textView, int size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }


    boolean isShow = true;

    /**
     * @param TAG
     * @param msg 打印日志 true显示日志
     */
    protected void LogT_(String TAG, String msg) {
        if (isShow) {
            Log.i(TAG, msg);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.view_null);
        ImmersionBar.with(this).destroy();
        mubinder.unbind();
    }

    /**
     * 抽象加载view
     */
    protected abstract void AddView();

    /**
     * 抽象设置view点击事件
     */
    protected abstract void SetViewListen();

    /**
     * @return 获取layoutview
     */
    protected abstract int getLayout();

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
