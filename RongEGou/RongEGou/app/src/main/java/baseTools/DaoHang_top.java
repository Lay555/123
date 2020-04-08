package baseTools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doudui.rongegou.R;


public class DaoHang_top extends RelativeLayout {
    RelativeLayout lin_top;
    public ImageView ima_fh, im_d;

    TextView te_tit;

    public DaoHang_top(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.daohang_top, this);
        lin_top = view.findViewById(R.id.dh_lintop);
        ima_fh = view.findViewById(R.id.dh_imreturn);
        te_tit = view.findViewById(R.id.dh_tetit);
        im_d = view.findViewById(R.id.dh_im);
        im_d.setVisibility(View.GONE);


        te_tit.setText("ccccccc");
        ima_fh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.push_right_out,
                        R.anim.push_right_in);
            }
        });
    }


    public void settext_(String str) {
        te_tit.setText(str);
    }

    public void setvis() {
        ima_fh.setVisibility(GONE);
    }

    public void setvis1() {
        im_d.setVisibility(VISIBLE);
    }

    /**
     * @param context 定制状态栏样色
     */
    public void DaoHangLan(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = ((Activity) context).getWindow();
            // 取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 设置状态栏颜色
            window.setStatusBarColor(Color.parseColor("#ffffff"));

        }
    }

}
