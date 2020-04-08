package com.doudui.rongegou.HomePage.fenlei;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;

import java.util.List;

public class fenleitit_adapter extends RecyclerView.Adapter<fenleitit_adapter.viewholde> {
    Context context;
    List<fenleitit_data> list;
    gethenpos gethenpos;

    public fenleitit_adapter(Context context,
                             List<fenleitit_data> list) {
        this.context = context;
        this.list = list;
    }

    public void setheninter(gethenpos hen) {
        this.gethenpos = hen;
    }

    @NonNull
    @Override
    public fenleitit_adapter.viewholde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fenlei_te, parent, false);
        viewholde viewholde = new viewholde(view);
        return viewholde;
    }

    @Override
    public void onBindViewHolder(@NonNull final fenleitit_adapter.viewholde holder, final int position) {
        fenleitit_data data = list.get(position);
        if (data.isselect.equals("1")) {
            holder.textView.setSelected(true);
        } else holder.textView.setSelected(false);
        holder.textView.setText(data.getTxt());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.textView.isSelected()) {
                    if (fastClick())
                        gethenpos.gethenpos1(position);
                }
            }
        });
    }

    long lastClick = 0;

    /**
     * @return 判断是否快速点击
     * true不是快速点击
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
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
        void gethenpos1(int pos);
    }
}
