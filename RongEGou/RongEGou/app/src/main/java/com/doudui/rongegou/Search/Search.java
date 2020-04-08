package com.doudui.rongegou.Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;

import baseTools.BaseActivity_;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Search extends BaseActivity_ {


    @BindView(R.id.flss_imreturn)
    ImageView flssImreturn;
    @BindView(R.id.flss_edit)
    EditText flssEdit;
    @BindView(R.id.flss_imss)
    ImageView flssImss;
    @BindView(R.id.flss_tess)
    TextView flssTess;
    @BindView(R.id.flss_retop)
    RelativeLayout flssRetop;

    @Override
    protected void AddView() {

    }

    @Override
    protected void SetViewListen() {
        flssImreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
        flssTess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flssEdit.getText().toString().length() <= 0) {
                    toaste_ut(Search.this, "请输入搜索内容");
                } else {
                    closeKeybord(flssEdit, Search.this);
                    Intent i = new Intent(Search.this, Search1.class);
                    i.putExtra("value", flssEdit.getText().toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
    }

    public void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

}
