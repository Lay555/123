package com.doudui.rongegou.ApplayOrder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

public class spnameadapter extends RecyclerView.Adapter<spnameadapter.viewholder> {
    Context context;
    List<spnameData> list;
    iterm_ iterm_;

    public spnameadapter(Context context,
                         List<spnameData> list) {
        this.context = context;
        this.list = list;
    }

    public void setiterm(iterm_ item) {
        this.iterm_ = item;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.spnameadapter, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        final spnameData data = list.get(position);
        holder.te_name.setText(data.getName());
        holder.te_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iterm_.iterm_(data.getId(),data.getName(),data.getGoodsid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView te_name;

        public viewholder(View itemView) {
            super(itemView);
            te_name = itemView.findViewById(R.id.spname_teitname);
        }
    }

    public interface iterm_ {
        void iterm_(String skuid,String name,String goodsid);
    }
}
