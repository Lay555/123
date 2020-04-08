package com.doudui.rongegou.LoginAct;

import android.os.Bundle;
import android.widget.TextView;

import com.doudui.rongegou.R;

import baseTools.BaseActivity_;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterNoPass extends BaseActivity_ {

    @BindView(R.id.loginp_te)
    TextView loginpTe;


    @Override
    protected void AddView() {

    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register_no_pass;
    }

    @OnClick(R.id.loginp_te)
    public void onViewClicked() {
        startActivityByIntent(RegisterNoPass.this, Loginact.class);
        finish();
    }
}
