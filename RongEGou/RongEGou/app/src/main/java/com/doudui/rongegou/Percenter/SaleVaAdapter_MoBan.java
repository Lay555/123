package com.doudui.rongegou.Percenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class SaleVaAdapter_MoBan extends RecyclerView.Adapter<SaleVaAdapter_MoBan.viewholder> {
    Context  context;
    List<SaleVaData> list;

    public SaleVaAdapter_MoBan(Context context,
                         List<SaleVaData> list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{

        public viewholder(View itemView) {
            super(itemView);
        }
    }
}
