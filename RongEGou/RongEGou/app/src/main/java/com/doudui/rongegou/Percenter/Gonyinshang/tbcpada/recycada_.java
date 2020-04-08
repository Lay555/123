package com.doudui.rongegou.Percenter.Gonyinshang.tbcpada;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class recycada_ extends RecyclerView.Adapter<recycada_.viewholder> {
    Context context;
    List<recycada_data> list;


    public recycada_(Context context,
                     List<recycada_data> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycada_tbcp, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        final recycada_data data = list.get(position);
        holder.recyctbTename.setText(data.getTxt());

        if (data.getIsselsec().equals("1")) {
            holder.recyctbIma.setVisibility(View.VISIBLE);
        } else holder.recyctbIma.setVisibility(View.GONE);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setIsselsec("1");
                    } else {
                        if (list.get(i).getIsselsec().equals("1")) {
                            list.get(i).setIsselsec("0");
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyctb_tename)
        TextView recyctbTename;
        @BindView(R.id.recyctb_ima)
        ImageView recyctbIma;

        @BindView(R.id.recyctb_lin)
        LinearLayout linearLayout;


        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
