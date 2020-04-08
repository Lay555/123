package baseTools.lunbo;

import com.bumptech.glide.Glide;
import com.doudui.rongegou.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		imageView.setScaleType(ScaleType.FIT_XY);
		Glide.with(context.getApplicationContext()).load(url).into(imageView);
		return imageView;
	}
}
