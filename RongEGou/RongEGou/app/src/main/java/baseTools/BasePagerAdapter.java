package baseTools;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.doudui.rongegou.R;

import java.util.ArrayList;
import java.util.List;

public class BasePagerAdapter extends PagerAdapter {
    private List<ImageView> views = new ArrayList<ImageView>();
    Context context;

    public BasePagerAdapter(List<ImageView> views, Context context) {
        this.views = views;
        this.context = context;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(views.get(position));

        View view = views.get(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(
                        R.anim.push_right_out, R.anim.push_right_in);
            }
        });

        return views.get(position);
    }

}
