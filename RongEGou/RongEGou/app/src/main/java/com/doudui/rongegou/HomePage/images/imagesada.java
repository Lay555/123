package com.doudui.rongegou.HomePage.images;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.doudui.rongegou.Goods.GoodsDetails;
import com.doudui.rongegou.HomePage.GlideRoundTransform;
import com.doudui.rongegou.R;

import java.util.List;

public class imagesada extends RecyclerView.Adapter<imagesada.viewholder> {
    Context context;
    List<imagesdata> list;
    float width = 0;
    float height = 0;
    float w_screen = 0;

    public imagesada(Context context,
                     List<imagesdata> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imagesada, parent, false);
        viewholder viewholder = new viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {
        final imagesdata data = list.get(position);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        w_screen = dm.widthPixels;

        if (!data.getImg().equals(holder.imageView.getTag(R.id.hpit_ima))) {
            // 加载图片
            Glide.with(context)
                    .load(data.getImg())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            width = resource.getIntrinsicWidth();
                            height = resource.getIntrinsicHeight();
                            RequestOptions myOptions = new RequestOptions()
                                    .centerCrop().skipMemoryCache(false).dontAnimate()
                                    .override((int) (w_screen / 2 - 40), (int) ((w_screen / 2 - 40) * height / width));
                            Glide.with(context).load(data.getImg()).apply(myOptions)
                                    .into(holder.imageView);
                        }
                    });

            holder.imageView.setTag(R.id.hpit_ima, data.getImg());//bu3
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GoodsDetails.class);
                i.putExtra("goodsid", data.getTxt());
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
        ImageView imageView;

        public viewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imait_ima);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }//bu4

    public int px2dip(int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
