package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.AboutUsFragment;

public class HomeViewPagerAdapter extends PagerAdapter {

    private Activity activity;
    private LayoutInflater inflater;

    public HomeViewPagerAdapter(Activity activity) {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return AppConstants.HOME_TITLES.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        Typeface bebasNeue = Typeface.createFromAsset(activity.getAssets(), "fonts/BebasNeue.ttf");

        View itemView = inflater.inflate(R.layout.home_custom_view, container, false);
        TextView textView = itemView.findViewById(R.id.home_custom_view_text);
        textView.setText(AppConstants.HOME_TITLES[position]);
        textView.setTypeface(bebasNeue);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 4){

                    FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.parentLayout, new AboutUsFragment());
                    transaction.commit();
                }
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
