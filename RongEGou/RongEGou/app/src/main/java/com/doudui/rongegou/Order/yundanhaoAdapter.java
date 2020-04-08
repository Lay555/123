package com.doudui.rongegou.Order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

import baseTools.lunbo.other_web;

public class yundanhaoAdapter extends RecyclerView.Adapter<yundanhaoAdapter.viewholder> {
    Context context;
    List<yudanhaoData> list;
    CopyButtonLibrary copyButtonLibrary;

    public yundanhaoAdapter(Context context,
                            List<yudanhaoData> list) {
        this.context = context;
        this.list = list;
        copyButtonLibrary = new CopyButtonLibrary(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.yundanhaoadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {
        final yudanhaoData data = list.get(position);
        holder.teydh.setText(data.getOrderStr());
        holder.tego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://m.kuaidi100.com/result.jsp?nu="
                        + data.getOrderStr();
                if (data.getTypestr().contains("顺丰")) {
                    url = "http://www.sf-express.com/mobile/cn/sc/dynamic_function/waybill/waybill_query_by_billno.html?billno=" + data.getOrderStr();
                }
                Intent i = new Intent();
                i.putExtra("titl", "物流详情");
                i.putExtra("url",
                        url);
                i.setClass(context, other_web.class);
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(
                        R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        holder.teydh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.teydh.getText().toString()))
                    copyButtonLibrary.init(holder.teydh.getText().toString());
            }
        });
        holder.tecopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(holder.teydh.getText().toString()))
                    copyButtonLibrary.init(holder.teydh.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView teydh, tego, tecopy;

        public viewholder(View itemView) {
            super(itemView);
            teydh = itemView.findViewById(R.id.ordde_tetit);
            tego = itemView.findViewById(R.id.ordde_tego);
            tecopy = itemView.findViewById(R.id.ordde_tecopy);

        }
    }
}
