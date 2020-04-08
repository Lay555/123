package com.doudui.rongegou.Percenter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleVaAdapter extends RecyclerView.Adapter<SaleVaAdapter.viewholder> {
    Context context;
    List<SaleVaData> list;


    public SaleVaAdapter(Context context,
                         List<SaleVaData> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.salevaadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        if (position % 2 == 1)
            holder.salevalueadaLin.setBackgroundColor(Color.parseColor("#f9f9f9"));
        else holder.salevalueadaLin.setBackgroundColor(Color.parseColor("#ffffff"));

        SaleVaData data = list.get(position);
        holder.salevalueadaTename.setText(data.getName());
        holder.salevalueadaTesz.setText(data.getSznumber());
        holder.salevalueadaTeday.setText(data.getDaysale());
        holder.salevalueadaTemon.setText(data.getMonsale());

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.salevalueada_Tename)
        TextView salevalueadaTename;
        @BindView(R.id.salevalueada_Tesz)
        TextView salevalueadaTesz;
        @BindView(R.id.salevalueada_Teday)
        TextView salevalueadaTeday;
        @BindView(R.id.salevalueada_Temon)
        TextView salevalueadaTemon;
        @BindView(R.id.salevalueada_lin)
        LinearLayout salevalueadaLin;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
