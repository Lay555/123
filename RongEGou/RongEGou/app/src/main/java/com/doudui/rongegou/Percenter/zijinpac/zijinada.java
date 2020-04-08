package com.doudui.rongegou.Percenter.zijinpac;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class zijinada extends RecyclerView.Adapter<zijinada.viewholder> {
    Context context;
    List<zijindata> list;


    public zijinada(Context context,
                    List<zijindata> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zijinada, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        zijindata data = list.get(position);
        holder.zjTename.setText(data.getName());
        holder.zjTetime.setText(data.getTime());
        holder.zjTenum.setText(data.getNumstr());
        if (TextUtils.isEmpty(data.getStatus())) {
            holder.te_status.setVisibility(View.GONE);
        }else{
            holder.te_status.setVisibility(View.VISIBLE);
            holder.te_status.setText(data.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.zj_tename)
        TextView zjTename;
        @BindView(R.id.zj_tetime)
        TextView zjTetime;
        @BindView(R.id.zj_tenum)
        TextView zjTenum;
        @BindView(R.id.zj_status)
        TextView te_status;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
