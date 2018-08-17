package nitrr.ecell.e_cell.activities;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samveg on 17/8/18.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context mContext;

    public ViewPagerAdapter(Context mcontext) {
        this.mContext = mcontext;

        }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(WelcomeActivity.layouts[position], container, false);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return WelcomeActivity.layouts.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
