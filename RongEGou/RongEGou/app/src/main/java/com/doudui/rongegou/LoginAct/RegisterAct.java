package com.doudui.rongegou.LoginAct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.Calendar;

import baseTools.BaseActivity_;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterAct extends BaseActivity_ {

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pho)
    EditText loginPho;
    @BindView(R.id.login_yzm)
    EditText loginYzm;
    @BindView(R.id.loginp_tegetyzm)
    TimerTextView loginpTegetyzm;
    @BindView(R.id.login_sr)
    EditText loginSr;
    @BindView(R.id.login_sr1)
    EditText loginSr1;
    @BindView(R.id.login_sr2)
    EditText loginSr2;
    @BindView(R.id.login_tjr)
    EditText loginTjr;
    @BindView(R.id.loginp_tezc)
    TextView loginpTezc;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (getIntent().getStringExtra("ss") != null) {
        }
    }

    @Override
    protected void AddView() {

    }

    @Override
    protected void SetViewListen() {
        loginSr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String year = loginSr.getText().toString();
                if (year.length() == 4)
                    openEditText(loginSr1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginSr1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String month = s.toString();
                if (month.length() == 1) {//只输入了一位数，如果==1就继续输入，如果大于1前面直接补0
                    if (Integer.valueOf(month) > 1) {
                        month = "0" + month;
                        loginSr1.setText(month);
                        openEditText(loginSr2);
                    }
                } else if (month.length() == 2) {
                    if (Float.valueOf(month) > 12) {
                        month = "12";
                        loginSr1.setText(month);
                    }
                    openEditText(loginSr2);
                } else if (month.length() == 0) {
//                    Utils.deleteEditText(holder.et_birthYear);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        loginSr2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String day = s.toString();
                if (day.length() == 2) {
                    if (TextUtils.isEmpty(loginSr.getText().toString())) {
                        openEditText(loginSr);
                        return;
                    }
                    if (TextUtils.isEmpty(loginSr1.getText().toString())) {
                        openEditText(loginSr1);
                        return;
                    }
                    Calendar c = Calendar.getInstance();
                    c.set(Integer.valueOf(loginSr.getText().toString()), Integer.valueOf(loginSr1.getText().toString()), 1);
                    c.add(Calendar.DATE, -1);
                    int d = c.get(Calendar.DATE);
                    if (Integer.valueOf(day) > d) {
                        day = d + "";
                        loginSr2.setText(day);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @OnClick({R.id.loginp_tegetyzm, R.id.loginp_tezc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginp_tegetyzm:
                break;
            case R.id.loginp_tezc:

                startActivityByIntent(this, RegisterActWait.class);
                break;
        }
    }

    public void openEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setSelection(0);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }
}
