package com.doudui.rongegou.Percenter.Gonyinshang;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;
import com.gyf.barlibrary.ImmersionBar;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import baseTools.BaseActivity_;
import baseTools.Cannotscroll_viewpager;
import baseTools.DaoHang_top;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class xiaoshoue extends FragmentActivity {

    @BindView(R.id.xsefr_xsezs)
    DaoHang_top xsefrXsezs;
    @BindView(R.id.xsefr_vpger)
    Cannotscroll_viewpager xsefrVpger;

    @BindView(R.id.xsefr_indicator4)
    MagicIndicator magicIndicator;

    List<Fragment> list = new ArrayList<>();

    List<String> liststr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.bai).statusBarDarkFont(true, 0.0f).init();
        setContentView(R.layout.activity_xiaoshoue);
        ButterKnife.bind(this);
        AddView();
        SetViewListen();
    }

    protected void AddView() {
        xsefrXsezs.settext_("销售额展示");
        list.add(new perxse());
        list.add(new SaleValues());

        xsefrVpger.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        xsefrVpger.setOffscreenPageLimit(2);

        liststr.add("个人统计");
        liststr.add("团队统计");
        xsefrVpger.setDisableScroll(false);
        initMagicIndicator4();
    }

    protected void SetViewListen() {
    }

    private void initMagicIndicator4() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);//设置为可渐变的View
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.bai));//字渐变前
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.bai));//字渐变后
                simplePagerTitleView.setText(liststr.get(index));
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);//设置字体大小

                DisplayMetrics dm = getResources().getDisplayMetrics();
                int w = dm.widthPixels;
                simplePagerTitleView.setWidth(w / 2);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        xsefrVpger.setCurrentItem(index, false);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);

                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                TriangularPagerIndicator indicator = new TriangularPagerIndicator(context);
                indicator.setLineColor(Color.parseColor("#ffffff"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
//        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
//        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE); //设置tab的显示模式，SHOW_DIVIDER_MIDDLE为居中
//        titleContainer.setDividerDrawable(new ColorDrawable() {
//            @Override
//            public int getIntrinsicWidth() {
//                return UIUtil.dip2px(xiaoshoue.this, 20);//tab的宽度
//            }
//        });
        ViewPagerHelper.bind(magicIndicator, xsefrVpger);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
