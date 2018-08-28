package nitrr.ecell.e_cell.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.ESummitRegistrationResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.adapters.HomeViewPagerAdapter;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import nitrr.ecell.e_cell.utils.PrefUtils;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private PrefUtils utils;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView user;
    private ImageView menu;
    private CardView logOut;
    private LinearLayout homeTopLayout;
    private Button btnRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        progressDialog = new ProgressDialog();
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
        btnRegister = findViewById(R.id.btn_register_esummit);
        btnRegister.setOnClickListener(this);
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

        }else if (view == btnRegister) {
            progressDialog.showDialog("Redirecting to registration form...",HomeActivity.this);
            apiCallForRegistrationLink();
        }
    }

    private void apiCallForRegistrationLink() {
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<ESummitRegistrationResponse> call =  apiServices.getRegistrationLink();
        call.enqueue(new Callback<ESummitRegistrationResponse>() {
            @Override
            public void onResponse(Call<ESummitRegistrationResponse> call, Response<ESummitRegistrationResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()){
                    ESummitRegistrationResponse eSummitRegistrationResponse = response.body();
                    if (eSummitRegistrationResponse != null){
                        if (eSummitRegistrationResponse.getSuccess()) {
                            openRegistrationFormLink(eSummitRegistrationResponse.getLink());
                        }else {
                            Toast.makeText(HomeActivity.this, eSummitRegistrationResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(HomeActivity.this, getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ESummitRegistrationResponse> call, Throwable t) {
                progressDialog.hideDialog();
                    if (!NetworkUtils.isNetworkAvailable(HomeActivity.this)) {
                        DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, HomeActivity.this, clickListenerPositive, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                    } else {
                        Toast.makeText(HomeActivity.this, getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();

                    }
            }
        });
    }

    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            apiCallForRegistrationLink();
        }
    };

    private void openRegistrationFormLink(String url) {
            Uri uri = Uri.parse(url);
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            intentBuilder.setStartAnimations(this, R.anim.slide_up, R.anim.slide_down);
            intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            customTabsIntent.launchUrl(this, uri);
    }
}
