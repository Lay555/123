package com.doudui.rongegou.HomePage;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.Goods.GoodsDetails;
import com.doudui.rongegou.HomePage.phb.paihangbang;
import com.doudui.rongegou.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import baseTools.lunbo.ADInfo;
import baseTools.lunbo.CycleViewPager;
import baseTools.lunbo.ViewFactory;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HPHeader extends LinearLayout {

    @BindView(R.id.sye_refrag)
    RelativeLayout refragment;
    @BindView(R.id.hpheader_lin1)
    LinearLayout hpheaderLin1;
    @BindView(R.id.hpheader_lin2)
    LinearLayout hpheaderLin2;
    @BindView(R.id.hpheader_lin3)
    LinearLayout hpheaderLin3;
    @BindView(R.id.hpheader_lin4)
    LinearLayout hpheaderLin4;

    @BindView(R.id.hpheader_ima1)
    ImageView hpheaderIma1;
    @BindView(R.id.hpheader_ima2)
    ImageView hpheaderIma2;
    @BindView(R.id.hpheader_ima3)
    ImageView hpheaderIma3;
    @BindView(R.id.hpheader_ima4)
    ImageView hpheaderIma4;
    @BindView(R.id.hpheader_im5)
    ImageView hpheaderIm5;
    @BindView(R.id.hpheader_im6)
    ImageView hpheaderIm6;

    @BindView(R.id.hpheader_te1)
    TextView hpheaderte1;
    @BindView(R.id.hpheader_te2)
    TextView hpheaderte2;
    @BindView(R.id.hpheader_te3)
    TextView hpheaderte3;
    @BindView(R.id.hpheader_te4)
    TextView hpheaderte4;


    @BindView(R.id.hpheader_im7)
    ImageView hpheaderIm7;
    @BindView(R.id.hpheader_im8)
    ImageView hpheaderIm8;

    @BindView(R.id.lin_)
    LinearLayout lin_;


    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    CycleViewPager cycleViewPager;
    private String[] imageUrls = {"", "", ""};// 轮播图test

    Context context;

    JSONArray jsalb = null;

    public HPHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.hpheader, this);
        this.context = context;

        ButterKnife.bind(this, view);
        Glide.with(this).load(R.mipmap.wxtj_icon).into(hpheaderIma1);
        Glide.with(this).load(R.mipmap.jl_icon).into(hpheaderIma2);
        Glide.with(this).load(R.mipmap.xp_icon).into(hpheaderIma3);
        Glide.with(this).load(R.mipmap.bh_icon).into(hpheaderIma4);
//        setsetviewlisten(context);
    }

//    public void setsetviewlisten(final Context context) {
//        hpheaderLin1.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, fristFragTop4.class);
//                i.putExtra("tit", "每日产品");
//                i.putExtra("txt", "goods_rexiao");
//                context.startActivity(i);
//
//            }
//        });
//        hpheaderLin4.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, fristFragTop4.class);
//                i.putExtra("tit", "每日补货");
//                i.putExtra("txt", "goods_absvip_recommend");
//                context.startActivity(i);
//
//            }
//        });
//        hpheaderLin3.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, fristFragTop4.class);
//                i.putExtra("tit", "每日新品");
//                i.putExtra("txt", "goods_recommend");
//                context.startActivity(i);
//
//            }
//        });
//        hpheaderLin2.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, fristFragTop4.class);
//                i.putExtra("tit", "捡漏");
//                i.putExtra("txt", "goods_jianlou");
//                context.startActivity(i);
//            }
//        });
//
//    }

    public void getjson_totop(JSONArray jsa) throws JSONException {
        JSONObject jso1 = jsa.getJSONObject(0);
        final String t1 = jso1.getString("areaname");
        final String id1 = jso1.getString("id");
        hpheaderte1.setText(t1);
        Glide.with(this).load(jso1.getString("areaimage")).into(hpheaderIma1);
        hpheaderLin1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, fristFragTop4.class);
                i.putExtra("tit", t1);
                i.putExtra("txt", id1);
                i.putExtra("isneedshare", "1");
                context.startActivity(i);

            }
        });

        JSONObject jso2 = jsa.getJSONObject(1);
        final String t2 = jso2.getString("areaname");
        final String id2 = jso2.getString("id");
        hpheaderte2.setText(t2);
        Glide.with(this).load(jso2.getString("areaimage")).into(hpheaderIma2);
        hpheaderLin2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, fristFragTop4.class);
                i.putExtra("tit", t2);
                i.putExtra("txt", id2);
                i.putExtra("isneedshare", "1");
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

        JSONObject jso3 = jsa.getJSONObject(2);
        final String t3 = jso3.getString("areaname");
        final String id3 = jso3.getString("id");
        hpheaderte3.setText(t3);
        Glide.with(this).load(jso3.getString("areaimage")).into(hpheaderIma3);
        hpheaderLin3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, fristFragTop4.class);
                i.putExtra("tit", t3);
                i.putExtra("txt", id3);
                i.putExtra("isneedshare", "1");
                i.putExtra("isneedshare", "1");
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

        JSONObject jso4 = jsa.getJSONObject(3);
        final String t4 = jso4.getString("areaname");
        final String id4 = jso4.getString("id");
        hpheaderte4.setText(t4);
        Glide.with(this).load(jso4.getString("areaimage")).into(hpheaderIma4);
        hpheaderLin4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, fristFragTop4.class);
                i.putExtra("tit", t4);
                i.putExtra("txt", id4);
                i.putExtra("isneedshare", "1");
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });


        JSONObject jso5 = jsa.getJSONObject(4);
        final String t5 = jso5.getString("areaname");
        final String id5 = jso5.getString("id");
        Glide.with(this).load(jso5.getString("areaimage")).into(hpheaderIm6);
        hpheaderIm6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, fristFragTop4.class);
                i.putExtra("tit", t5);
                i.putExtra("isneedshare", "1");
                i.putExtra("txt", id5);
                context.startActivity(i);

            }
        });
        hpheaderIm5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, paihangbang.class);
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
            }
        });

        if (jsa.length() >= 7) {
            JSONObject jso7 = jsa.getJSONObject(5);
            final String t7 = jso7.getString("areaname");
            final String id7 = jso7.getString("id");
            Glide.with(this).load(jso7.getString("areaimage")).into(hpheaderIm7);
            hpheaderIm7.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, fristFragTop4.class);
                    i.putExtra("tit", t7);
                    i.putExtra("isneedshare", "1");
                    i.putExtra("txt", id7);
                    context.startActivity(i);
                }
            });


            JSONObject jso8 = jsa.getJSONObject(6);
            final String t8 = jso8.getString("areaname");
            final String id8 = jso8.getString("id");
            Glide.with(this).load(jso8.getString("areaimage")).into(hpheaderIm8);
            hpheaderIm8.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(context, fristFragTop4.class);
                    i.putExtra("tit", t8);
                    i.putExtra("isneedshare", "1");
                    i.putExtra("txt", id8);
                    context.startActivity(i);
                }
            });
        }else lin_.setVisibility(GONE);

    }

    public void getjsonobj(JSONObject object, FragmentManager fragment, Context context) throws JSONException {
        JSONArray jsa = object.getJSONArray("dt_ads");

        System.out.println("lubbo" + jsa);

        if (jsa.length() <= 0) {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            return;
        }

        jsalb = jsa;
        imageUrls = new String[jsa.length()];
        for (int i = 0; i < jsa.length(); i++) {
            JSONObject jso = jsa.getJSONObject(i);
            imageUrls[i] = jso.getString("ads_pic");
        }
        initialize(fragment, context);
    }

    /**
     * 轮播图片
     */
    public void initialize(FragmentManager fragment, Context context) {
        cycleViewPager = (CycleViewPager) fragment
                .findFragmentById(R.id.sye_viewpagerlunbos);

        views.removeAll(views);
        infos.removeAll(infos);

        for (int i = 0; i < imageUrls.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(imageUrls[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }
        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(context,
                infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(context, infos.get(i)
                    .getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory
                .getImageView(context, infos.get(0).getUrl()));
        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, mAdCycleViewListener);
        // 设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认4500ms
        cycleViewPager.setTime(4000);
        // 设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();

    }

    /**
     * 轮播点击监听
     */
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {
            if (cycleViewPager.isCycle()) {
                int pos = position - 1;
                try {
                    JSONObject jso = jsalb.getJSONObject(pos);
                    String type = jso.getString("ap_id");
                    if (type.equals("1")) {
                        Intent i = new Intent(context, com.doudui.rongegou.HomePage.images.images.class);
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    } else if (type.equals("4")) {
                        Intent i = new Intent(context, GoodsDetails.class);
                        i.putExtra("goodsid", jso.getString("goods_id"));
                        context.startActivity(i);
                        ((Activity) context).overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    };
}
