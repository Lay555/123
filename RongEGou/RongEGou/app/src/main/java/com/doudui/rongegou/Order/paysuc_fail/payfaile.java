package com.doudui.rongegou.Order.paysuc_fail;

import android.view.View;
import android.widget.TextView;

import com.doudui.rongegou.R;

import org.greenrobot.eventbus.EventBus;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import baseTools.configParams;
import baseTools.paramsDataBean;
import butterknife.BindView;

public class payfaile extends BaseActivity_ {

    @BindView(R.id.payfail_dh)
    DaoHang_top payfailDh;
    @BindView(R.id.payf_tecz1)
    TextView payfTecz1;

    String type = "1";


    @Override
    protected void AddView() {
        payfailDh.settext_("支付失败");
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void SetViewListen() {
        payfTecz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_payfaile;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (type.equals("1")) {
            paramsDataBean databean = new paramsDataBean();
            databean.setMsg(configParams.orderlist);
            EventBus.getDefault().post(databean);
        }
    }
}
