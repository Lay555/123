package com.doudui.rongegou.Percenter.Gonyinshang;

import android.os.Bundle;
import android.widget.TextView;

import com.doudui.rongegou.LoginAct.Loginact;
import com.doudui.rongegou.R;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class gongyinshang extends BaseActivity_ {

    @BindView(R.id.gys_dh)
    DaoHang_top gysDh;
    @BindView(R.id.gys_tebot)
    TextView gysTebot;


    @Override
    protected void AddView() {
        gysDh.settext_("我想成为供应商");
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_gongyinshang;
    }

    @OnClick(R.id.gys_tebot)
    public void onViewClicked() {
    startActivityByIntent(this,tibaocpin.class);
    }
}
