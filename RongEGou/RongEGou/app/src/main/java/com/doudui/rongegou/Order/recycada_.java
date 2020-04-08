package com.doudui.rongegou.Order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.doudui.rongegou.Goods.guige.MyGridView;
import com.doudui.rongegou.R;

import java.util.List;

import baseTools.lunbo.other_web;
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
        View view = LayoutInflater.from(context).inflate(R.layout.recycada_, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, final int position) {

        final recycada_data data = list.get(position);

        recycada_zi recycada_zi = new recycada_zi(context, data.getList());
        holder.xdadaGridv.setAdapter(recycada_zi);

        holder.xdadaTebh.setText("订单编号：" + data.getBhao());
        holder.xdadaTespnum.setText("共" + data.getAllnum() + "件商品");
        holder.xdadaTeprice.setText("实付款:￥" + data.getAllprice());
        holder.xdadaTeshr.setText("收货人：" + data.getShren());
        holder.xdadaTetime.setText("下单时间:" + data.getTime());

        if (data.getIskehu().equals("0")) {
            holder.ima_khxd.setVisibility(View.VISIBLE);
        } else holder.ima_khxd.setVisibility(View.GONE);

        holder.lin_bot.setVisibility(View.VISIBLE);
        if (data.getZtai().equals("10")) {
            holder.xdadaTezt.setText("待提交");
            holder.xdadaTecz1.setVisibility(View.GONE);
            holder.xdadaTecz2.setVisibility(View.VISIBLE);
            holder.xdadaTecz2.setText("取消订单");
            holder.xdadaTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //quxiaodd
                    getit_hen.cancel_order(position, data.getId());
                }
            });
        } else if (data.getZtai().equals("20")) {
            holder.xdadaTezt.setText("待发货");
            holder.xdadaTecz1.setVisibility(View.GONE);
            holder.xdadaTecz2.setVisibility(View.VISIBLE);
            if (data.getShouhou().equals("0"))
                holder.xdadaTecz2.setText("售后处理中");
            else if (data.getShouhou().equals("-1"))
                holder.xdadaTecz2.setText("申请售后");
            else if (data.getShouhou().equals("1"))
                holder.xdadaTecz2.setText("完成售后");
            //afterstate:售后状态（0售后处理中，1售后完成，1申请售后/未提交售后）,
            holder.xdadaTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, order_shouhou.class);
                    i.putExtra("id", data.getId());
                    i.putExtra("type", "1");
                    i.putExtra("type_shouhou", data.getShouhou());
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    //售后
                }
            });
        } else if (data.getZtai().equals("30")) {
            holder.xdadaTezt.setText("待收货");
            holder.xdadaTecz1.setVisibility(View.VISIBLE);
            holder.xdadaTecz2.setVisibility(View.VISIBLE);
            holder.xdadaTecz1.setText("查看物流");
            if (data.getShouhou().equals("0"))
                holder.xdadaTecz2.setText("售后处理中");
            else if (data.getShouhou().equals("-1"))
                holder.xdadaTecz2.setText("申请售后");
            else if (data.getShouhou().equals("1"))
                holder.xdadaTecz2.setText("完成售后");
            //afterstate:售后状态（0售后处理中，1售后完成，1申请售后/未提交售后）,
            holder.xdadaTecz2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, order_shouhou.class);
                    i.putExtra("id", data.getId());
                    i.putExtra("type", "1");//判断是订单进入售后还是订单详情进入售后1

                    i.putExtra("type_shouhou", data.getShouhou());
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                }
            });
            holder.xdadaTecz1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getWuliuhao().contains(",")) {
                        Toast.makeText(context, "该订单有多个快递，请到订单详情查看。", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String url = "https://m.kuaidi100.com/result.jsp?nu="
                            + data.getWuliuhao();
                    if (data.getWuliutype().equals("29")) {
                        url = "http://www.sf-express.com/mobile/cn/sc/dynamic_function/waybill/waybill_query_by_billno.html?billno="
                                + data.getWuliuhao();
                    }
                    Intent i = new Intent();
                    i.putExtra("titl", "物流详情");
                    i.putExtra("url",
                            url);
                    i.setClass(context, other_web.class);
                    context.startActivity(i);
                    ((Activity) context).overridePendingTransition(
                            R.anim.push_left_in, R.anim.push_left_out);
                }
            });

        } else if (data.getZtai().equals("40")) {
            holder.lin_bot.setVisibility(View.GONE);
            holder.xdadaTezt.setText("待支付");
            holder.xdadaTecz1.setVisibility(View.GONE);
            holder.xdadaTecz2.setVisibility(View.GONE);

        }
        holder.lin_all.setOnClickListener(new View.OnClickListener() {
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
        holder.xdadaGridv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int ix, long l) {
                Intent i = new Intent(context, orderdetails.class);
                i.putExtra("id", data.getBhao());
                i.putExtra("orderid", data.getId());
                i.putExtra("wuliuhao", data.getWuliuhao());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
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

        @BindView(R.id.ima_khxg)
        ImageView ima_khxd;

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

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface cancelord {

        void cancel_order(int pos, String id);
    }
}
