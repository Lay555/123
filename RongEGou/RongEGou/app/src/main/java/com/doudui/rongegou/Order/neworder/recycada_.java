package com.doudui.rongegou.Order.neworder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.Goods.guige.MyGridView;
import com.doudui.rongegou.Order.orderdetails;
import com.doudui.rongegou.Order.recycada_zi;
import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class recycada_ extends RecyclerView.Adapter<recycada_.viewholder> {
    Context context;
    List<recycada_data> list;
    cancelord getit_hen;

    public recycada_(Context context,
                     List<recycada_data> list) {
        this.context = context;
        this.list = list;
    }

    public void setinter_hen(cancelord getit_hen) {
        this.getit_hen = getit_hen;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycada_yushou, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {

        final recycada_data data = list.get(position);

        recycada_zi recycada_zi = new recycada_zi(context, data.getList());
        holder.xdadaGridv.setAdapter(recycada_zi);

        holder.xdadaTebh.setText("订单编号：" + data.getBhao());
        holder.xdadaTespnum.setText("共" + data.getAllnum() + "件商品");
        holder.xdadaTeprice.setText("实付款:￥" + data.getAllprice());
        holder.xdadaTeshr.setText("收 货 人：" + data.getShren());
        holder.xdadaTetime.setText("下单时间:  " + data.getTime());

        holder.shouhuodz.setText(" " + data.getShouhuodz());

        holder.xdadaTezt.setVisibility(View.GONE);

        holder.lin_bot.setVisibility(View.VISIBLE);
        if (data.getZtai().equals("10")) {

            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            setviewhw_lin(holder.lin_bot, w_screen, (int) (w_screen * 90 /750.0), 0, 0,
                    0, 0);

            holder.lin_bot.setVisibility(View.VISIBLE);
            holder.xdadaTecz1.setText("转正式订单");
            holder.xdadaTecz2.setVisibility(View.VISIBLE);
            holder.xdadaTecz2.setText("取消订单");
            holder.te_zhxx.setVisibility(View.GONE);
            holder.quxiaotime.setVisibility(View.GONE);

            holder.xdadaTecz2.setBackgroundResource(R.drawable.yuanjiaobk);
            holder.xdadaTecz2.setTextColor(context.getResources().getColor(R.color.h68));

            holder.xdadaTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getit_hen != null)
                        getit_hen.cancel_order(position, data.getId());
                }
            });
            holder.xdadaTecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //转正式订单
                    if (getit_hen != null)
                        getit_hen.zhuanhua(position, data.getId());
                }
            });

        } else if (data.getZtai().equals("20")) {//订单状态共两种 10 （未转化），20 （已转化）
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int w_screen = dm.widthPixels;
            setviewhw_lin(holder.lin_bot, w_screen, (int) (w_screen * 130 /750.0), 0, 0,
                    0, 0);
            holder.quxiaotime.setVisibility(View.GONE);
            holder.lin_bot.setVisibility(View.VISIBLE);
            holder.xdadaTecz1.setText("待发货");
            holder.xdadaTecz1.setVisibility(View.GONE);
            holder.te_zhxx.setVisibility(View.VISIBLE);
            holder.xdadaTecz2.setVisibility(View.VISIBLE);
            holder.xdadaTecz2.setText("正式详情 >");
            holder.xdadaTecz2.setBackgroundResource(R.drawable.yuanjiaobk1);
            holder.xdadaTecz2.setTextColor(context.getResources().getColor(R.color.bai));

            holder.te_zhxx.setText("正式订单号： " + data.getZhuanhuabhao() + "\n" + "转化时间:  " + data.getZhuanhuashijian());

            holder.xdadaTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, orderdetails.class);
                    i.putExtra("id", data.getBhao());
                    i.putExtra("orderid", data.getId());
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            });
        } else if (data.getZtai().equals("-10")) {//订单状态共3种 10 （未转化），20 （已转化）,-10取消
            holder.lin_bot.setVisibility(View.GONE);
            holder.quxiaotime.setVisibility(View.VISIBLE);
            holder.xdadaTecz1.setVisibility(View.GONE);
            holder.te_zhxx.setVisibility(View.GONE);

            holder.quxiaotime.setText("取消时间：" + data.getQuxiaoshijian());
        }
        holder.lin_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getZtai().equals("20")) {
                    Intent i = new Intent(context, orderdetails.class);
                    i.putExtra("id", data.getBhao());
                    i.putExtra("orderid", data.getId());
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });
        holder.xdadaGridv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int ix, long l) {
                if (data.getZtai().equals("20")) {
                    Intent i = new Intent(context, orderdetails.class);
                    i.putExtra("id", data.getBhao());
                    i.putExtra("orderid", data.getId());
                    i.putExtra("wuliuhao", data.getWuliuhao());
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.xdada_tebh)
        TextView xdadaTebh;
        @BindView(R.id.xdada_tezt)
        TextView xdadaTezt;
        @BindView(R.id.xdada_tespnum)
        TextView xdadaTespnum;
        @BindView(R.id.xdada_teprice)
        TextView xdadaTeprice;
        @BindView(R.id.xdada_teshr)
        TextView xdadaTeshr;
        @BindView(R.id.xdada_tetime)
        TextView xdadaTetime;
        @BindView(R.id.xdada_gridv)
        MyGridView xdadaGridv;

        @BindView(R.id.linall)
        LinearLayout lin_all;

        @BindView(R.id.orderddatails_linbot)
        LinearLayout lin_bot;
        @BindView(R.id.xdada_lintop)
        LinearLayout lin_top;
        @BindView(R.id.xdada_tecz1)
        TextView xdadaTecz1;
        @BindView(R.id.xdada_tecz2)
        TextView xdadaTecz2;

        @BindView(R.id.xdada_tezhxx)
        TextView te_zhxx;

        @BindView(R.id.xdada_teqxtime)
        TextView quxiaotime;
        @BindView(R.id.xdada_teshdz)
        TextView shouhuodz;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface cancelord {

        void cancel_order(int pos, String id);

        void zhuanhua(int pos, String id);
    }

    private void setviewhw_lin(View v, int width, int height, int left,
                               int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }
}
