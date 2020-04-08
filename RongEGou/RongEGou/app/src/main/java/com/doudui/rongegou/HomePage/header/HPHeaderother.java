package com.doudui.rongegou.HomePage.header;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.transition.Transition;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.doudui.rongegou.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HPHeaderother extends LinearLayout {

    @BindView(R.id.otherhead1)
    MyGridView otherhead1;
    otherheadadapter otherheadadapter;
    List<otherheadadapterdata> list = new ArrayList<>();

    public HPHeaderother(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.hpheaderother, this);
        ButterKnife.bind(this, view);
        otherheadadapter = new otherheadadapter(list, context);
        otherhead1.setAdapter(otherheadadapter);
        otherhead1.setBackground(new ColorDrawable(Color.TRANSPARENT));

    }

    public void addlist(List<otherheadadapterdata> listx) {
        list.removeAll(list);
        list.addAll(listx);
        otherheadadapter.notifyDataSetChanged();

    }


}
