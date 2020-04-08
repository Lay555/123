package com.doudui.rongegou.tuisong.meizu;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.huawei.hms.support.api.push.PushReceiver;

import java.io.FileWriter;
import java.io.IOException;

public class huaweiReceiver extends PushReceiver {

    @Override
    public void onEvent(Context context, Event arg1, Bundle arg2) {
        super.onEvent(context, arg1, arg2);

    }

    @Override
    public boolean onPushMsg(Context context, byte[] arg1, Bundle arg2) {

        return super.onPushMsg(context, arg1, arg2);
    }

    @Override
    public void onPushMsg(Context context, byte[] arg1, String arg2) {

        super.onPushMsg(context, arg1, arg2);
    }

    @Override
    public void onPushState(Context context, boolean arg1) {

        super.onPushState(context, arg1);
    }

    @Override
    public void onToken(Context context, String arg1, Bundle arg2) {
        super.onToken(context, arg1, arg2);

    }

    @Override
    public void onToken(Context context, String arg1) {
        super.onToken(context, arg1);
    }

    public void showToast(final String toast, final Context context) {

    }

    private void writeToFile(String conrent) {
        String SDPATH = Environment.getExternalStorageDirectory() + "/huawei.txt";
        try {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(SDPATH, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileWriter.write(conrent + "\r\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}