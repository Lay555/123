package com.doudui.rongegou.Percenter.Perziliao;

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
import android.widget.TextView;


import com.doudui.rongegou.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class getpicdialogfragment extends DialogFragment {
    @BindView(R.id.xzfs_teth)
    TextView xzfsTeth;
    @BindView(R.id.xzfs_tesc)
    TextView xzfsTesc;
    @BindView(R.id.xzfs_teqx)
    TextView xzfsTeqx;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        View view = inflater.inflate(R.layout.getpicdialogfragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        setviewlisten();
        return view;
    }

    private void setviewlisten() {
        xzfsTeth.setOnClickListener(new View.OnClickListener() {//拍照
            @Override
            public void onClick(View v) {
                paizao paizao = (paizao) getActivity();
                paizao.getPaiZ(1);
                dismiss();
            }
        });
        xzfsTesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paizao paizao = (paizao) getActivity();
                paizao.getPaiZ(2);
                dismiss();
            }
        });
        xzfsTeqx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface paizao {
        void getPaiZ(int pz);
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
