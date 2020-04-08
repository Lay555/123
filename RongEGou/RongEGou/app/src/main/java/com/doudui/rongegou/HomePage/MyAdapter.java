package com.doudui.rongegou.HomePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.doudui.rongegou.Goods.GoodsDetails;
import com.doudui.rongegou.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的


    //获取从Activity中传递过来每个item的数据集合
    private List<adapterdata> mDatas;
    Context context;
    private View mHeaderView;

    private int mHeaderCount = 1;//头部View个数

    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //构造函数
    public MyAdapter(List<adapterdata> list, Context context) {
        this.mDatas = list;
        this.context = context;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }


    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ListHolder(mHeaderView);
        }
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListHolder(layout);
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            if (holder instanceof ListHolder) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
//                ((ListHolder) holder).tv.setText(mDatas.get(position - 1));

                DisplayMetrics dm = context.getResources().getDisplayMetrics();
                int w = dm.widthPixels;
                if ((position - 1) % 2 == 0) {
//                    setviewhw_lin(((ListHolder) holder).hpitIma, getPixelsFromDp((int) (w * 160 / 720.0)), getPixelsFromDp((int) (w * 160 / 720.0)), getPixelsFromDp((int) (w * 14 / 720.0)),
//                            getPixelsFromDp((int) (w * 13 / 720.0)), getPixelsFromDp((int) (w * 6 / 720.0)), 0);
                    ((ListHolder) holder).view1.setVisibility(View.VISIBLE);
                    ((ListHolder) holder).view21.setVisibility(View.VISIBLE);
                    ((ListHolder) holder).view2.setVisibility(View.GONE);
                    ((ListHolder) holder).view11.setVisibility(View.GONE);
                } else {
//                    setviewhw_lin(((ListHolder) holder).hpitIma, getPixelsFromDp((int) (w * 160 / 720.0)), getPixelsFromDp((int) (w * 160 / 720.0)), getPixelsFromDp((int) (w * 6 / 720.0)), getPixelsFromDp((int) (w * 13 / 720.0)), getPixelsFromDp((int) (w * 14 / 720.0)), 0);

                    ((ListHolder) holder).view1.setVisibility(View.GONE);
                    ((ListHolder) holder).view21.setVisibility(View.GONE);
                    ((ListHolder) holder).view2.setVisibility(View.VISIBLE);
                    ((ListHolder) holder).view11.setVisibility(View.VISIBLE);
                }

                if (position >= 1) {
                    final adapterdata data = mDatas.get(position - 1);
                    RequestOptions myOptions = new RequestOptions()
                            .centerCrop().skipMemoryCache(false).dontAnimate().fitCenter()
                            .transform(new GlideRoundTransform(context, 5));


                    if (!data.getUrl().equals(((ListHolder) holder).hpitIma.getTag(R.id.hpit_ima))) {
                        // 加载图片
                        Glide.with(context).load(data.getUrl()).apply(myOptions).into(((ListHolder) holder).hpitIma);
                        ((ListHolder) holder).hpitIma.setTag(R.id.hpit_ima, data.getUrl());//bu3
                    }

                    ((ListHolder) holder).hpitTetxt.setText(data.getName());
                    Spannable span = new SpannableString("￥" + data.price + "福利价");
                    span.setSpan(new AbsoluteSizeSpan(11, true), 0, 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    span.setSpan(new AbsoluteSizeSpan(17, true), 1, data.price.length() + 1,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ((ListHolder) holder).hpitTeprice.setText(span);

                    ((ListHolder) holder).lin_.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(context, GoodsDetails.class);
                            i.putExtra("goodsid", data.getId());
                            context.startActivity(i);
                            ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                                    R.anim.push_left_out);
                        }
                    });
                }
                return;
            }
            return;
        } else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }//bu4

    //在这里面加载ListView中的每个item的布局
    class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hpit_lin)
        LinearLayout lin_;
        @BindView(R.id.hpit_ima)
        ImageView hpitIma;
        @BindView(R.id.hpit_tetxt)
        TextView hpitTetxt;
        @BindView(R.id.hpit_teprice)
        TextView hpitTeprice;
        @BindView(R.id.hp_view1)
        View view1;
        @BindView(R.id.hp_view2)
        View view2;

        @BindView(R.id.hp_view11)
        View view11;
        @BindView(R.id.hp_view21)
        View view21;

        public ListHolder(View itemView) {
            super(itemView);

            //如果是headerview或者是footerview,直接返回
            if (itemView == mHeaderView) {
                return;
            }
            ButterKnife.bind(this, itemView);
        }

    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return mDatas.size();
        } else
            return mDatas.size() + 1;
    }

    private void setviewhw_lin(View v, int width, int height, int left,
                               int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    private int getPixelsFromDp(int size) {

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return (size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }

}
