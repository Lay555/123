package com.doudui.rongegou.HomePage.header;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.HomePage.fenlei.fenlei_list;
import com.doudui.rongegou.R;

import java.util.List;

public class otherheadadapter extends BaseAdapter {
    List<otherheadadapterdata> list;
    Context context;

    public otherheadadapter(List<otherheadadapterdata> list,
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
            view = LayoutInflater.from(context).inflate(R.layout.otherheadadapter, viewGroup, false);
            addview.hpitIma = view.findViewById(R.id.hpit_ima);
            addview.hpitTetxt = view.findViewById(R.id.hpit_tetxt);
            addview.hpitLin = view.findViewById(R.id.hpit_lin);
            view.setTag(addview);
        } else
            addview = (otherheadadapter.addview) view.getTag();

        final otherheadadapterdata data = list.get(i);
        addview.hpitTetxt.setText(data.getTxt());
        Glide.with(context).load(data.getUrl()).into(addview.hpitIma);
        if (addview.hpitTetxt.getText().toString().equals("全部"))
            Glide.with(context).load(R.mipmap.morexx).into(addview.hpitIma);

        addview.hpitLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, fenlei_list.class);
                i.putExtra("typeid", data.getZhuid());
                i.putExtra("catid", data.getId());
                i.putExtra("name", data.getTxt());

                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });
        return view;
    }

    public class addview {
        ImageView hpitIma;
        TextView hpitTetxt;
        LinearLayout hpitLin;
    }
}
