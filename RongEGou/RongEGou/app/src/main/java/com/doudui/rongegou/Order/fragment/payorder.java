package com.doudui.rongegou.Order.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class payorder extends DialogFragment {

    @BindView(R.id.fk_linzfb)
    LinearLayout fkLinzfb;
    @BindView(R.id.fk_linwx)
    LinearLayout fkLinwx;
    @BindView(R.id.xzfs_teqx)
    TextView xzfsTeqx;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.payorder, container, false);
        unbinder = ButterKnife.bind(this, view);
        setviewlisten();

        return view;
    }

    private void setviewlisten() {
        xzfsTeqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });
        fkLinwx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    zffs zffs = (payorder.zffs) getActivity();
                    zffs.getzffs(1);
                    dismiss();
                }
            }
        });
        fkLinzfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fastClick()) {
                    zffs zffs = (payorder.zffs) getActivity();
                    zffs.getzffs(0);
                    dismiss();
                }
            }
        });
    }

    long lastClick = 0;

    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    public interface zffs {
        void getzffs(int pz);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        getDialog().getWindow().setLayout(-1, -2);
    }
}
