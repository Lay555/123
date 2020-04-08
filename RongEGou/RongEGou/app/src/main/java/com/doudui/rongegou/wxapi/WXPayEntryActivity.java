package com.doudui.rongegou.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.doudui.rongegou.MainActivity;
import com.doudui.rongegou.Order.paysuc_fail.payfaile;
import com.doudui.rongegou.Order.paysuc_fail.paysucc;
import com.doudui.rongegou.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_sb);
        api = WXAPIFactory.createWXAPI(this, "wx8179ac04473e44c0", false);
        api.handleIntent(getIntent(), this);
        // if (Build.VERSION.SDK_INT >= 21) {
        // Window window = getWindow();
        // // 取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
        // window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        // window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // // 设置状态栏颜色
        // window.setStatusBarColor(Color.parseColor("#f2f3f5"));
        // }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        // 0 支付成功
        // -1 发生错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        // -2 用户取消 发生场景：用户不支付了，点击取消，返回APP。
        if (resp.errCode == 0) {
            preferences = PreferenceManager
                    .getDefaultSharedPreferences(WXPayEntryActivity.this);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT)
                            .show();

                    Intent i = new Intent();
                    i.putExtra("type", "1");
                    i.setClass(WXPayEntryActivity.this, paysucc.class);
                    startActivity(i);
                    finish();
                }
            }, 10);
        } else {

            if (resp.errCode == -2) {
                preferences = PreferenceManager
                        .getDefaultSharedPreferences(WXPayEntryActivity.this);

                Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT)
                        .show();

                Intent i = new Intent();
                i.putExtra("type", "1");
                i.setClass(WXPayEntryActivity.this, payfaile.class);
                startActivity(i);
                finish();
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            startActivity(new Intent(WXPayEntryActivity.this,
                    MainActivity.class));
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);

            return false;
        }
        return false;
    }

    String sign = "";

}