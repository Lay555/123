package com.doudui.rongegou.Order.paysuc_fail;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.doudui.rongegou.MainActivity;
import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import butterknife.BindView;
import butterknife.ButterKnife;

public class paysucc extends BaseActivity_ {
    String type = "1";
    @BindView(R.id.paysuc_dh)
    DaoHang_top paysucDh;
    @BindView(R.id.paysuc_tecz1)
    TextView paysucTecz1;


    @Override
    protected void AddView() {
        paysucDh.settext_("支付成功");
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void SetViewListen() {
        paysucTecz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paramsDataBean databean = new paramsDataBean();
                databean.setMsg(configParams.sypage1);
                databean.setT("");
                EventBus.getDefault().post(databean);
                startActivityByIntent(paysucc.this, MainActivity.class);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_paysucc;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (type.equals("1")) {
            paramsDataBean databean = new paramsDataBean();
            databean.setMsg(configParams.orderlist);
            EventBus.getDefault().post(databean);
            return false;
        }
        return false;

    }
}
