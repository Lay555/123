package com.doudui.rongegou.HomePage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class adapter extends BaseAdapter {
    List<String> list;
    Context context;

    public adapter(List<String> list,
                   Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        addview addview;
        if (view == null) {
            addview = new addview();
            ButterKnife.bind(this, view);
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            view.setTag(addview);
        } else
            addview = (addview) view.getTag();
        return view;
    }

    public class addview {
        @BindView(R.id.hpit_lin)
        LinearLayout lin_;
        @BindView(R.id.hpit_ima)
        ImageView hpitIma;
        @BindView(R.id.hpit_tetxt)
        TextView hpitTetxt;
        @BindView(R.id.hpit_teprice)
        TextView hpitTeprice;
    }
}
