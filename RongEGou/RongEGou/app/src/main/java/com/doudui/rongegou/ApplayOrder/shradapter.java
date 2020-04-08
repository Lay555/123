package com.doudui.rongegou.ApplayOrder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.HomePage.fenlei.fenleitit_data;
import com.doudui.rongegou.Percenter.Address.AddressData;
import com.doudui.rongegou.R;

import java.util.List;

public class shradapter extends RecyclerView.Adapter<shradapter.viewholde> {
    Context context;
    List<AddressData> list;
    chooseshr gethenpos;

    public shradapter(Context context,
                      List<AddressData> list) {
        this.context = context;
        this.list = list;
    }

    public void setshronc(chooseshr hen) {
        this.gethenpos = hen;
    }

    @NonNull
    @Override
    public shradapter.viewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shradapter, parent, false);
        viewholde viewholde = new viewholde(view);
        return viewholde;
    }

    @Override
    public void onBindViewHolder(@NonNull final shradapter.viewholde holder, final int position) {
        AddressData data = list.get(position);
        holder.textView.setText(data.getName()+"  "+data.getPho());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.textView.isSelected()) {
                    gethenpos.chooseshr(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholde extends RecyclerView.ViewHolder {
        TextView textView;

        public viewholde(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.shrada_tename);
        }
    }

    public interface chooseshr {
        void chooseshr(int pos);
    }
}
