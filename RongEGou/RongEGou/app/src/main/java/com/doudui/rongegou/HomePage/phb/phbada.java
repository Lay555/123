package com.doudui.rongegou.HomePage.phb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class phbada extends RecyclerView.Adapter<phbada.viewholder> {
    Context context;
    List<phbdata> list;


    public phbada(Context context,
                  List<phbdata> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phbada, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        phbdata data = list.get(position);
        holder.phbitTepos.setText((position + 1) + "");
        Glide.with(context).load(data.getUrl()).into(holder.phbitImpic);
        holder.phbitTename.setText(data.getName());
        holder.phbitTegge.setText(data.getGge());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.phbit_tepos)
        TextView phbitTepos;
        @BindView(R.id.phbit_impic)
        ImageView phbitImpic;
        @BindView(R.id.phbit_tename)
        TextView phbitTename;
        @BindView(R.id.phbit_tegge)
        TextView phbitTegge;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
