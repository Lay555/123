package com.doudui.rongegou.Goods.guige;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doudui.rongegou.R;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class sp_ggada extends RecyclerView.Adapter<sp_ggada.viewholder> implements sp_ggada1.shuaxin {
    List<sp_ggadadadata> list;
    Context context;
    xxshua xxs;
    int width = 0;


    public sp_ggada(List<sp_ggadadadata> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setshaxin(xxshua xx) {
        this.xxs = xx;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.spggada, parent, false);


        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {
        final sp_ggadadadata data = list.get(position);
        holder.te_gg.setText(data.getText());
        final List<sp_ggadadadata1> listzi = list.get(position).getList();
        sp_ggada1 ada = new sp_ggada1(listzi, context);
        ada.setinterfa(this);
        holder.gr.setAdapter(ada);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;

        holder.flowLayout.removeAllViews();
        final List<TextView> list_textv = new ArrayList<>();

        for (int i = 0; i < listzi.size(); i++) {
            sp_ggadadadata1 spGgadadadata1 = listzi.get(i);
            final TextView view = new TextView(context);
            view.setText(spGgadadadata1.getText());
            view.setSelected(spGgadadadata1.isT_sele());
            view.setBackgroundResource(R.drawable.shaixuan_selecter);
            if (view.isSelected())
                view.setTextColor(Color.parseColor("#ffffff"));
            else
                view.setTextColor(Color.parseColor("#666666"));
            view.setPadding(40, 10, 40, 10);

            list_textv.add(view);
        }

        for (int i = 0; i < listzi.size(); i++) {
            final sp_ggadadadata1 spGgadadadata1 = listzi.get(i);
            final int pos = i;

            holder.flowLayout.addView(list_textv.get(i));

            list_textv.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spGgadadadata1.getIscanoncl().equals("1")) {
                        if (!list_textv.get(pos).isSelected()) {
                            for (int j = 0; j < listzi.size(); j++) {
                                if (j == pos) {
                                    data.getList().get(pos).setT_sele(true);
                                    list_textv.get(pos).setSelected(true);
                                } else {
                                    data.getList().get(j).setT_sele(false);
                                    list_textv.get(j).setSelected(false);
                                }
                            }
                            notifyDataSetChanged();
                            xxs.shuaxin();
                        }
                    }
                }
            });
        }


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView te_gg;
        MyGridView gr;
        FlowLayout flowLayout;

        public viewholder(View itemView) {
            super(itemView);
            te_gg = (TextView) itemView.findViewById(R.id.gg_te);
            gr = (MyGridView) itemView.findViewById(R.id.gg_listvx);
            flowLayout = itemView.findViewById(R.id.gg_flowlayout);
        }
    }

    @Override
    public void shuaxin() {
        xxs.shuaxin();
    }

    public interface xxshua {
        void shuaxin();
    }


}
