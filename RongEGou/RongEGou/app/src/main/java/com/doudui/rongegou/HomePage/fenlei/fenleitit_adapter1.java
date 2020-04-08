package com.doudui.rongegou.HomePage.fenlei;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

public class fenleitit_adapter1 extends RecyclerView.Adapter<fenleitit_adapter1.viewholde> {
    Context context;
    List<fenleitit_data1> list;
    gethenpos gethenpos;

    public fenleitit_adapter1(Context context,
                              List<fenleitit_data1> list) {
        this.context = context;
        this.list = list;
    }

    public void setheninter(gethenpos hen) {
        this.gethenpos = hen;
    }

    @NonNull
    @Override
    public fenleitit_adapter1.viewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fenlei_te1, parent, false);
        fenleitit_adapter1.viewholde viewholde = new fenleitit_adapter1.viewholde(view);
        return viewholde;
    }

    @Override
    public void onBindViewHolder(@NonNull final fenleitit_adapter1.viewholde holder, final int position) {
        fenleitit_data1 data1 = list.get(position);
        holder.textView.setText(data1.getTxt());
        if (data1.getIsselect().equals("1")) {
            holder.textView.setSelected(true);
        } else holder.textView.setSelected(false);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.textView.isSelected()) {
                    gethenpos.gethenpos(position);
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
            textView = itemView.findViewById(R.id.fenleite_titf);
        }
    }

    public interface gethenpos {
        void gethenpos(int pos);
    }

}
