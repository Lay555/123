package com.doudui.rongegou.Percenter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.R;

import baseTools.BaseActivity_;
import baseTools.DaoHang_top;
import butterknife.BindView;

public class JFRules extends BaseActivity_ {
    @BindView(R.id.jfru_dh)
    DaoHang_top daoHang_top;
    @BindView(R.id.jfru_img)
    ImageView img;

    @Override
    protected void AddView() {
        daoHang_top.settext_("积分规则");
//        Glide.with(this).load("http://rongegou.oss-cn-beijing.aliyuncs.com/Other/%E7%A7%AF%E5%88%86%E8%A7%84%E5%88%99.jpg").into(img);
    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_jfrules;
    }
}
