package com.doudui.rongegou.Goods;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.Goods.guige.MyGridView;
import com.doudui.rongegou.Goods.guige.sp_ggada1;
import com.doudui.rongegou.Goods.guige.sp_ggadadadata;
import com.doudui.rongegou.Goods.guige.sp_ggadadadata1;
import com.doudui.rongegou.R;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class maijiaxiuada extends RecyclerView.Adapter<maijiaxiuada.viewholder> {
    List<majiaxiudata> list;
    Context context;


    public maijiaxiuada(List<majiaxiudata> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spggada, parent, false);


        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {

        majiaxiudata data = list.get(position);

        Glide.with(context).load(data.getUrl()).into(holder.ima);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
      ImageView ima;

        public viewholder(View itemView) {
            super(itemView);

            ima = itemView.findViewById(R.id.ima_);
        }
    }




}
