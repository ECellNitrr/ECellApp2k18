package nitrr.ecell.e_cell.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.AboutUsResponse;
import nitrr.ecell.e_cell.model.AuthenticationResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.HomeViewPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        APICall();
    }

    private void initialize() {
        parentLayout = findViewById(R.id.parentLayout);
        homeCenterImageView = findViewById(R.id.homeCenterImage);
        viewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);

        viewPager.setAdapter(new HomeViewPagerAdapter(this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                parentLayout.setBackground(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[position]));
                setStatusBarColor(getResources().getDrawable(AppConstants.GRADIENT_LOCATIONS[position]));
                homeCenterImageView.setImageDrawable(getResources().getDrawable(AppConstants.IMAGE_LOCATIONS[position]));
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

    private void APICall(){
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<AboutUsResponse> call = services.getAboutUsDetails();

        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                if(response.isSuccessful()){
                    AboutUsResponse jsonResponse = response.body();

                    if(jsonResponse != null){

                    }

                } else {
                    Toast.makeText(HomeActivity.this, "Not successful.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
