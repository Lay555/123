package com.doudui.rongegou.Order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class recycada_zi extends BaseAdapter {
    Context context;
    List<recycada_datazi> list;


    public recycada_zi(Context context,
                       List<recycada_datazi> list) {
        this.context = context;
        this.list = list;
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
        viewholder viewholder = null;
        if (view == null) {
            viewholder = new viewholder();
            view = LayoutInflater.from(context).inflate(R.layout.recycada_zi, viewGroup,false);
            viewholder.xdziImsp = view.findViewById(R.id.xdzi_imsp);
            viewholder.xdadaTename = view.findViewById(R.id.xdada_tename);
            viewholder.xdadaTegg = view.findViewById(R.id.xdada_tegg);
            viewholder.xdadaTeprice = view.findViewById(R.id.xdada_teprice);
            viewholder.xdadaTenum = view.findViewById(R.id.xdada_tenum);
            view.setTag(viewholder);
        } else viewholder = (recycada_zi.viewholder) view.getTag();
        recycada_datazi data = list.get(i);
        viewholder.xdadaTename.setText(data.getName());
        viewholder.xdadaTegg.setText(data.getGge());
        viewholder.xdadaTeprice.setText("￥"+data.getPrice());
        viewholder.xdadaTenum.setText("X"+data.getNum());
        Glide.with(context).load(data.getSpurl()).into(viewholder.xdziImsp);

        return view;
    }

    public class viewholder {
        ImageView xdziImsp;
        TextView xdadaTename;
        TextView xdadaTegg;
        TextView xdadaTeprice;
        TextView xdadaTenum;

    }

}
