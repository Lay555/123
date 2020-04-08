package com.doudui.rongegou.Percenter;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doudui.rongegou.R;

import baseTools.BaseActivity_;
import butterknife.BindView;

public class testWeb extends BaseActivity_ {


    @BindView(R.id.test_edit)
    EditText testEdit;
    @BindView(R.id.test_teok)
    TextView testTeok;
    @BindView(R.id.test_web)
    X5WebView testWeb;

    @Override
    protected void AddView() {
        initHardwareAccelerate();
        testWeb.loadUrl("https://api.rongegou.net/LiveStream.aspx");
    }

    @Override
    protected void SetViewListen() {
        testTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(testEdit.getText().toString())) {
                    toaste_ut(testWeb.this, "请输入网址");
                } else
                    testWeb.loadUrl(testEdit.getText().toString());
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test_web;
    }

    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (testWeb != null && testWeb.canGoBack()) {
                testWeb.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        //释放资源
        if (testWeb != null)
            testWeb.destroy();
        super.onDestroy();
    }


}
