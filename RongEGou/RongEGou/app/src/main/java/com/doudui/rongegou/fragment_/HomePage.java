package com.doudui.rongegou.fragment_;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.doudui.rongegou.HomePage.HPAdapter;
import com.doudui.rongegou.HomePage.HPData;
import com.doudui.rongegou.HomePage.fristFrag;
import com.doudui.rongegou.HomePage.otherFrag;
import com.doudui.rongegou.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import baseTools.BaseFragment_1;
import baseTools.Cannotscroll_viewpager;
import baseTools.retrofit2base.retro_intf;
import butterknife.BindView;
import butterknife.Unbinder;

public class HomePage extends BaseFragment_1 implements HPAdapter.getonc {
    retro_intf service1, service2;
    Unbinder unbinder1;
    @BindView(R.id.sy_vp)
    Cannotscroll_viewpager syVp;

    @BindView(R.id.magic_indicator4)
    MagicIndicator magicIndicator;

    List<HPData> list = new ArrayList<>();

    private List<Fragment> fragment = new ArrayList<Fragment>();

    @Override

    protected void AddView() {
        fragment.add(new fristFrag());

        list.add(new HPData("", "首页", true));

        list.add(new HPData("26", "家居用品", false));
        fragment.add(getfr("26", "家居用品"));

        list.add(new HPData("27", "厨房用品", false));
        fragment.add(getfr("27", "厨房用品"));

        list.add(new HPData("28", " 家纺", false));
        fragment.add(getfr("28", " 家纺"));

        list.add(new HPData("29", "服装", false));
        fragment.add(getfr("29", "服装"));

        list.add(new HPData("30", "小家电", false));
        fragment.add(getfr("30", "小家电"));

        list.add(new HPData("31", "食品", false));
        fragment.add(getfr("31", "食品"));

        list.add(new HPData("32", "保健品", false));
        fragment.add(getfr("32", "保健品"));

        list.add(new HPData("33", "美妆护肤", false));
        fragment.add(getfr("33", "美妆护肤"));

        list.add(new HPData("34", "收藏装饰", false));
        fragment.add(getfr("34", "收藏装饰"));


        syVp.setDisableScroll(false);
        syVp.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragment.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragment.get(arg0);
            }
        });
        syVp.setOffscreenPageLimit(fragment.size());
        syVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == arg0) {
                        list.get(i).setIsselect(true);
                    } else
                        list.get(i).setIsselect(false);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        initMagicIndicator4();

    }

    @Override
    protected void SetViewListen() {

    }

    @Override
    protected int getLayout() {
        return R.layout.homepage;
    }


    public static Fragment getfr(String typeid, String name) {
        otherFrag otherFrag;
        otherFrag classify = new otherFrag();
        Bundle bundle = new Bundle();
        bundle.putString("typeid", typeid);
        bundle.putString("name", name);
        classify.setArguments(bundle);
        return classify;
    }

    @Override
    public void getonc(String id, int pos) {
        syVp.setCurrentItem(pos, false);
    }

    private void initMagicIndicator4() {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);//设置为可渐变的View
                simplePagerTitleView.setNormalColor(Color.BLACK);//字渐变前
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.hon));//字渐变后
                simplePagerTitleView.setText(list.get(index).getText());
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);//设置字体大小
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        syVp.setCurrentItem(index, false);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(getResources().getColor(R.color.hon));//线的颜色
                linePagerIndicator.setMode(2);
                linePagerIndicator.setLineHeight(UIUtil.dip2px(getActivity(), 2));
                linePagerIndicator.setLineWidth(UIUtil.dip2px(getActivity(), 60));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE); //设置tab的显示模式，SHOW_DIVIDER_MIDDLE为居中
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getActivity(), 11);//tab的宽度
            }
        });
        ViewPagerHelper.bind(magicIndicator, syVp);
    }


}
