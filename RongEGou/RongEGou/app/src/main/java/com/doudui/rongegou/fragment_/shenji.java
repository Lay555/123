package com.doudui.rongegou.fragment_;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.doudui.rongegou.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class shenji extends DialogFragment {
    View view;
    View.OnClickListener onClickListener;
    int wxx = 0;
    @BindView(R.id.bbgx_tebb)
    TextView bbgxTebb;
    @BindView(R.id.bbgx_tetxt)
    TextView bbgxTetxt;
    @BindView(R.id.bbgx_tegx)
    TextView bbgxTegx;
    @BindView(R.id.bbgx_teflue)
    TextView bbgxTeflue;
    Unbinder unbinder;

    String url = "", bbh = "", txt = "", type = "";

    public shenji setShejiClick(View.OnClickListener onClickListener){
        this.onClickListener=onClickListener;
        return this;
    }
    public void setPro(String pro){
        if (bbgxTegx!=null){
            bbgxTegx.setText(pro);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(false);
        view = inflater.inflate(R.layout.shenji, container, false);

        Bundle bundle = getArguments();
        url = bundle.getString("url");
        unbinder = ButterKnife.bind(this, view);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        wxx = dm.widthPixels;
        Bundle b = getArguments();
        setdata();
        setviewlisten();


        return view;
    }

    private void setviewlisten() {
        bbgxTeflue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        更新
        bbgxTegx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener!=null){
                    onClickListener.onClick(view);
                }
               /* Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
            }
        });
    }

    private void setdata() {
        Bundle bundle = getArguments();
        //bbh版本完全号码 txt更新说明
        bbgxTebb.setText(bundle.getString("bbh"));
        bbgxTetxt.setText(bundle.getString("txt"));
        type = bundle.getString("type");
        if (bundle.getString("type").equals("2")) {
            bbgxTeflue.setVisibility(View.GONE);
        }

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (type.equals("2")) {
                        Intent home = new Intent(Intent.ACTION_MAIN);
                        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        home.addCategory(Intent.CATEGORY_HOME);
                        startActivity(home);

                    } else dismiss();

                    return true;
                }
                return false;
            }
        });
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
        getDialog().getWindow().setLayout(dm.widthPixels, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
