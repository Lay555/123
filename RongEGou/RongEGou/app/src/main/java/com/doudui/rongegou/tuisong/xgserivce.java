package com.doudui.rongegou.tuisong;

import android.content.Context;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import baseTools.configParams;
import baseTools.paramsDataBean;

public class xgserivce extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {

    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        try {
            JSONObject jsonObject = new JSONObject(xgPushClickedResult.getCustomContent());
            if (jsonObject.getString("type").equals("1")) {
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.duizhang);
                EventBus.getDefault().post(databean);
            }else   if (jsonObject.getString("type").equals("2")) {
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.duizhang1);
                EventBus.getDefault().post(databean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
//        System.out.println(xgPushShowedResult.getTitle() + "  111 ");
    }
}
