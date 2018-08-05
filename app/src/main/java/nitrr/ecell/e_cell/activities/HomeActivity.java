package nitrr.ecell.e_cell.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.HomeViewPagerAdapter;
import nitrr.ecell.e_cell.utils.PrefUtils;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private PrefUtils utils;
    private Spinner spinner;
    private RelativeLayout parentLay;
    private boolean show = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[0]));
        initialize();
    }

    private void initialize() {
        spinner = findViewById(R.id.spinner);
        ViewPager viewPager = findViewById(R.id.home_view_pager);
        TabLayout tabLayout = findViewById(R.id.home_tab_layout);
        parentLay = findViewById(R.id.parentLayout);
        String topText = "Hey User";

        utils = new PrefUtils(HomeActivity.this);

        if (utils.getUserName() != null)
            topText = "Hey " + utils.getUserName();


        final String finalTopText = topText;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this, R.layout.spinner_text);

        adapter.setDropDownViewResource(R.layout.spinner_menu);
        adapter.add(finalTopText);
        adapter.add("Log Out");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
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

                spinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner.setAdapter(adapter);

        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[position]));
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
    public void onClick(View view) {

    }
}
