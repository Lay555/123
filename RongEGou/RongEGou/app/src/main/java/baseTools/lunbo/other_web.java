package baseTools.lunbo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class other_web extends Activity {
    @BindView(R.id.ddanxq_retop)
    RelativeLayout re_top;
    @BindView(R.id.ddanxq_imreturn)
    ImageView im_return;
    @BindView(R.id.ddanxq_web)
    WebView wb;

    @BindView(R.id.ddanxq_tetit)
    TextView te_tit;

    @BindView(R.id.progressBar1)
    ProgressBar pbar;

    String url = "", titl = "";

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.bai).statusBarDarkFont(true, 0.0f).init();
        setContentView(R.layout.activity_other_web);
        ButterKnife.bind(this);
        setviewdata();
        setviewweb();
        setviewhw();
        setviewlisten();
    }

    private void setviewdata() {
        Intent i = getIntent();
        titl = i.getStringExtra("titl");
        te_tit.setText(titl);
        url = i.getStringExtra("url");
    }

    private void setviewlisten() {
        im_return.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (wb.canGoBack()) {
                    wb.goBack();
                } else {
                    finish();
                    overridePendingTransition(R.anim.push_right_out,
                            R.anim.push_right_in);
                }

            }
        });
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                wb.setEnabled(false);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    wb.loadUrl(url);
                    return false;
            }
        });
        wb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根

                if (newProgress == 100) {
                    pbar.setVisibility(View.GONE);// 加载完网页进度条消失
                } else {
                    pbar.setVisibility(View.VISIBLE);// 开始加载网页时显示进度条
                    pbar.setProgress(newProgress);// 设置进度值
                }

            }
        });

    }

    private void setviewhw() {

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        setviewhw_re(re_top, w, (int) (w * 55 / 450.0), 0, 0, 0, 0);
        setviewhw_re(im_return, (int) (w * 49 / 450.0), (int) (w * 25 / 450.0),
                0, (int) (w * 15 / 450.0), 0, 0);
        im_return.setPadding((int) (w * 12 / 450.0), 0, (int) (w * 12 / 450.0),
                0);
    }

    private void setviewhw_re(View v, int width, int height, int left, int top,
                              int right, int bottom) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width,
                height);
        lp.setMargins(left, top, right, bottom);
        v.setLayoutParams(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && wb.canGoBack()) {
            wb.goBack();
            return true;
        } else {
            finish();
            overridePendingTransition(R.anim.push_right_out,
                    R.anim.push_right_in);
            return false;
        }
    }

    private void setviewweb() {
        wb.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        WebSettings settings = wb.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wb.getSettings().setDomStorageEnabled(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setBlockNetworkImage(false);
        wb.setVerticalScrollBarEnabled(false);
        wb.setHorizontalScrollBarEnabled(false);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.loadUrl(url);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        wb.removeAllViews();
        wb.destroy();
    }
}
