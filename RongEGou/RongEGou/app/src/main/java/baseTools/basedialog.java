package baseTools;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class basedialog extends DialogFragment {
    View view;
    Unbinder mubinder;
    long lastClick = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            setdialoglocation(getdialoglocation());
            view = inflater.inflate(getlayoid(), container, false);
            mubinder = ButterKnife.bind(this, view);
            addview();
            addviewlisten();
        }
        return view;
    }

    protected abstract int getlayoid();

    protected abstract void addview();

    protected abstract void addviewlisten();

    protected abstract int getdialoglocation();

    private void setdialoglocation(int gravity){
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(gravity);
    }

    /**
     * @return 判断是否快速点击
     * true不是快速点击
     */
    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 2000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
    protected void toaste_ut( String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(-1, -2);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff000000));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mubinder.unbind();
    }
}
