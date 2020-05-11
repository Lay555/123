package com.doudui.rongegou.util;

import android.util.Log;

import com.doudui.rongegou.BuildConfig;


/**
 * 打印的工具类
 * Created by Administrator on 2020/5/9.
 */

public class LogHelper {
    public static boolean enableDefaultLog = BuildConfig.DEBUG_SETTING;
    private static final int RETURN_NOLOG = -1;

    public static int i(String tag, String msg) {
        if (msg == null)
            msg = "";
        return enableDefaultLog ? Log.i(tag, msg) : RETURN_NOLOG;
    }

    public static int d(String tag, String msg) {
        if (msg == null)
            msg = "";
        return enableDefaultLog ? Log.d(tag, msg) : RETURN_NOLOG;
    }

    public static int e(String tag, String msg) {
        if (msg == null)
            msg = "";
        return enableDefaultLog ? Log.e(tag, msg) : RETURN_NOLOG;
    }

    public static int w(String tag, String msg) {
        if (msg == null)
            msg = "";
        return enableDefaultLog ? Log.w(tag, msg) : RETURN_NOLOG;
    }
}
