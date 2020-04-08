package com.doudui.rongegou.Order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

public class recycada_hen extends RecyclerView.Adapter<recycada_hen.viewholder> {
    Context context;
    List<recycada_hendata> list;
    getit_hen getit_hen;

    public recycada_hen(Context context,
                        List<recycada_hendata> list) {
        this.context = context;
        this.list = list;
    }

    public void setinter_hen(getit_hen getit_hen) {
        this.getit_hen = getit_hen;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycada_hen, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        recycada_hendata data = list.get(position);
        holder.textView.setText(data.getTxt());
        if (data.getIssect().equals("1")) {
            holder.view.setVisibility(View.VISIBLE);
            holder.textView.setSelected(true);
        } else {
            holder.view.setVisibility(View.GONE);
            holder.textView.setSelected(false);
        }
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getit_hen.getit_hen(position, "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView textView;
        View view;

        public viewholder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.orderhen_te);
            view = itemView.findViewById(R.id.orderhen_v);
        }
    }

    public interface getit_hen {
        void getit_hen(int pos, String str);
    }
}
