package baseTools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.R;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment_ extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public progressbar_ jdt;

    Unbinder mubinder;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayout(), container, false);
            jdt = new progressbar_();
            mubinder = ButterKnife.bind(this, view);
            AddView();
            SetViewListen();
        }
        return view;
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
        getActivity().overridePendingTransition(R.anim.push_right_out,
                R.anim.push_right_in);
        if (isFinish) {
            getActivity().finish();
        }
    }

    protected void startActivityByIntent(Context context, Class<?> cls, Boolean isFinish) {
        Intent i = new Intent();
        i.setClass(context, cls);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.push_left_in,
                R.anim.push_left_out);
        if (isFinish) {
            getActivity().finish();
        }
    }

    long lastClick = 0;

    /**
     * @return 判断是否快速点击
     * true不是快速点击
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 2000) {
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
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param key
     * @param value
     * @param context 保存键值对
     */
    protected void sharePre(String key, String value, Context context) {

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
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
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return preferences.getString(key, "0");
    }

    protected void sharePreclear() {

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.apply();
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


    protected void setViewHw_Lin(View v, int width, int height, int left,
                                 int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    protected void setViewHw_Re(View v, int width, int height, int left,
                                int top, int right, int bottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }


    /**
     * 抽象加载view
     */
    protected abstract void AddView();

    /**
     * 抽象设置view点击事件,需要手动添加到对应页面
     */
    protected abstract void SetViewListen();

    /**
     * @return 获取layoutview
     */
    protected abstract int getLayout();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mubinder.unbind();
    }



}
