package com.doudui.rongegou.Percenter.zijinpac;

import android.app.DialogFragment;
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

public class tixiancsdialog extends DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.te_ok)
    TextView te_ok;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.txcsdialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        getDialog().setCanceledOnTouchOutside(false);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;


        setviewlisten();


        return view;
    }

    private void setviewlisten() {
        te_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        int w = dm.widthPixels;
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - 60, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
