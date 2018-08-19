package nitrr.ecell.e_cell.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.adapters.HomeViewPagerAdapter;
import nitrr.ecell.e_cell.utils.PrefUtils;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private PrefUtils utils;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView user;
    private ImageView menu;
    private CardView logOut;
    private LinearLayout homeTopLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[0]));
        initialize();
    }

    private void initialize() {
        viewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);
        user = findViewById(R.id.user);
        menu = findViewById(R.id.menu);
        logOut = findViewById(R.id.log_out);
        homeTopLayout = findViewById(R.id.homeTopLayout);

        menu.setOnClickListener(this);
        logOut.setOnClickListener(this);
        homeTopLayout.setOnClickListener(this);

        String topText = "Hey User";

        utils = new PrefUtils(HomeActivity.this);

        if (utils.getUserName() != null)
            topText = "Hey " + utils.getUserName();

        user.setText(topText);

        viewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);

        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[position]));

                if (logOut.getVisibility() == View.VISIBLE)
                    logOut.setVisibility(View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout.setupWithViewPager(viewPager, true);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(Drawable gradientColor) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(gradientColor);
    }


    @Override
    public void onBackPressed() {
        if (logOut.getVisibility() == View.VISIBLE)
            logOut.setVisibility(View.GONE);

        else if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        if (view == homeTopLayout) {
            if (logOut.getVisibility() == View.VISIBLE)
                logOut.setVisibility(View.GONE);

        } else if (view == menu) {
            if (logOut.getVisibility() == View.GONE)
                logOut.setVisibility(View.VISIBLE);
            else
                logOut.setVisibility(View.GONE);

        } else if (view == logOut) {
            logOut.setVisibility(View.GONE);

            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setMessage("Are you surely want to Log Out?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    utils.clearPrefs();
                    Intent intent = new Intent(HomeActivity.this, RegisterMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}
