package com.doudui.rongegou.Percenter.Gonyinshang.tbcpada;

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

import org.greenrobot.eventbus.EventBus;

import baseTools.configParams;
import baseTools.paramsDataBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class tbcg extends android.support.v4.app.DialogFragment {
    View view;
    int wxx = 0;
    @BindView(R.id.xiadan_teok)
    TextView xiadanTeok;
    @BindView(R.id.xiadan_tets)
    TextView te_ts;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);


        view = inflater.inflate(R.layout.querenorder, container, false);
        unbinder = ButterKnife.bind(this, view);
        te_ts.setText("提报产品提交成功！");
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        setdata();
        setviewlisten();


        return view;
    }

    private void setviewlisten() {
        xiadanTeok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mappos pos = (mappos) getActivity();
                pos.mappos();
                dismiss();
            }
        });
    }

    private void setdata() {

    }

    public interface mappos {
        void mappos();
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
        getDialog().getWindow().setLayout(dm.widthPixels - 100, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
