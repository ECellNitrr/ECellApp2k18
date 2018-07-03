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

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.HomeViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        setStatusBarColor();
        initialize();
    }

    private void initialize() {
        viewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);

        viewPager.setAdapter(new HomeViewPagerAdapter(this));
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.home_background);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);
    }
}
