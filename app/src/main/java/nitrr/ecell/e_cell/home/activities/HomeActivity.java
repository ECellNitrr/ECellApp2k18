package nitrr.ecell.e_cell.home.activities;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.HomeViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout parentLayout;
    private ImageView homeCenterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[0]));
        initialize();
    }

    private void initialize() {
        parentLayout = findViewById(R.id.parentLayout);
        homeCenterImageView = findViewById(R.id.homeCenterImage);
        viewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);

        viewPager.setAdapter(new HomeViewPagerAdapter(this));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                parentLayout.setBackground(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[position]));
                setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[position]));
                homeCenterImageView.setImageDrawable(getResources().getDrawable(AppConstants.IMAGE_LOCATIONS[position]));
            }
        });
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(Drawable gradientColor) {
        Window window = getWindow();
        Drawable background = gradientColor;
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
    }
}
