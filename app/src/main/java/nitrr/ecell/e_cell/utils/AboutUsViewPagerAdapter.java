package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

import java.util.ArrayList;

import nitrr.ecell.e_cell.R;

public class AboutUsViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list = new ArrayList<>();
    private Context context;

    public AboutUsViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = null;

        switch (position) {
            case 0:
                drawable = context.getResources().getDrawable(R.drawable.goal);
                break;

            case 1:
                drawable = context.getResources().getDrawable(R.drawable.team);
                break;

            case 2:
                drawable = context.getResources().getDrawable(R.drawable.contact);
                break;
        }

        SpannableStringBuilder sb = new SpannableStringBuilder("  ");
        drawable.setBounds(0, 0, 83, 83);
        ImageSpan span = new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BASELINE);
        sb.setSpan(span, 0 ,1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return sb;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void addFragment(Fragment fragment) {
        list.add(fragment);
    }
}
