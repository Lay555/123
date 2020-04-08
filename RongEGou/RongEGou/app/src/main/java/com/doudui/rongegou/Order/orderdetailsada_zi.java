package com.doudui.rongegou.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.R;

import java.util.List;

public class orderdetailsada_zi extends RecyclerView.Adapter<orderdetailsada_zi.viewholder> {
    Context context;
    List<recycada_datazi> list;


    public orderdetailsada_zi(Context context,
                              List<recycada_datazi> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycada_zi, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int position) {
        recycada_datazi data = list.get(position);
        viewholder.xdadaTename.setText(data.getName());
        viewholder.xdadaTegg.setText("规格:"+data.getGge());
        viewholder.xdadaTeprice.setText("￥"+data.getPrice());
        viewholder.xdadaTenum.setText("X"+data.getNum());
        Glide.with(context).load(data.getSpurl()).into(viewholder.xdziImsp);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        ImageView xdziImsp;
        TextView xdadaTename;
        TextView xdadaTegg;
        TextView xdadaTeprice;
        TextView xdadaTenum;

        public viewholder(View itemView) {
            super(itemView);
            xdziImsp = itemView.findViewById(R.id.xdzi_imsp);
            xdadaTename = itemView.findViewById(R.id.xdada_tename);
            xdadaTegg = itemView.findViewById(R.id.xdada_tegg);
            xdadaTeprice = itemView.findViewById(R.id.xdada_teprice);
            xdadaTenum = itemView.findViewById(R.id.xdada_tenum);
        }
    }

}
