package com.doudui.rongegou.HomePage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;


public class HPAdapter extends RecyclerView.Adapter<HPAdapter.viewHolder> {
    List<HPData> list;
    Context context;

    getonc getonc;

    public void setonc(getonc getonc) {
        this.getonc = getonc;
    }

    public HPAdapter(List<HPData> list,
                     Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homepageadapter, parent, false);
        viewHolder viewHolder = new viewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        final HPData data = list.get(position);
        holder.hpitteTit.setSelected(data.isIsselect());
        holder.hpitteTit.setText(data.getText());
        if (data.isIsselect())
            holder.view.setVisibility(View.VISIBLE);
        else holder.view.setVisibility(View.INVISIBLE);

        holder.hpitteTit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.hpitteTit.isSelected()) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == position) {
                            list.get(i).setIsselect(true);
                        } else {
                            list.get(i).setIsselect(false);
                        }
                    }
                    notifyDataSetChanged();
                    if (getonc != null)
                        getonc.getonc(data.getId(), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView hpitteTit;
        ImageView view;

        public viewHolder(View itemView) {
            super(itemView);
            hpitteTit = itemView.findViewById(R.id.hpitte_tit);
            view = itemView.findViewById(R.id.hpitte_hen);
        }
    }

    public interface getonc {
        void getonc(String id, int pos);
    }
}
