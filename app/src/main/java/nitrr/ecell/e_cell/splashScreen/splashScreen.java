package nitrr.ecell.e_cell.splashScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import nitrr.ecell.e_cell.BuildConfig;
import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.activities.RegisterMainActivity;
import nitrr.ecell.e_cell.model.SplashScreenResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import nitrr.ecell.e_cell.utils.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splashScreen extends AppCompatActivity {

    private PrefUtils prefUtils;
    private ProgressBar progressBarSplashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefUtils = new PrefUtils(this);
        progressBarSplashScreen = findViewById(R.id.progress_bar_splash_screen);
        apiCallForUpdateCheck();
    }

    private void apiCallForUpdateCheck() {
        progressBarSplashScreen.setVisibility(View.VISIBLE);
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<SplashScreenResponse> call = apiServices.getAppUpdate();
        call.enqueue(new Callback<SplashScreenResponse>() {
            @Override
            public void onResponse(Call<SplashScreenResponse> call, Response<SplashScreenResponse> response) {
                progressBarSplashScreen.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    SplashScreenResponse splashScreenResponse = response.body();
                    if (splashScreenResponse != null) {
                        String appVersion = splashScreenResponse.getVersion();
                        if (Float.valueOf(appVersion) > BuildConfig.VERSION_CODE) {
                            updateApp(splashScreenResponse.getUrl());
                        }
                        else if (splashScreenResponse.getSuccess()) {
                            notUpdateApp();
                        }
                    } else if (getApplicationContext() != null) {
                        Toast.makeText(getApplicationContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SplashScreenResponse> call, Throwable t) {
                progressBarSplashScreen.setVisibility(View.GONE);
                if (getApplicationContext() != null) {
                    if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                        DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, getApplicationContext(), clickListenerPositiveConnection, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private DialogInterface.OnClickListener clickListenerPositiveConnection = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
           apiCallForUpdateCheck();
        }
    };

    private void updateApp(final String url) {
        DialogFactory.showDialog(DialogFactory.UPDATE_APP, this, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                }
            }
        }, null, false, getString(R.string.app_update), getString(R.string.update_msg), getString(R.string.update_btn));
    }

    private void notUpdateApp() {
       if (prefUtils.getIsLoggedIn()) {
           Intent intent = new Intent(splashScreen.this, HomeActivity.class);
           startActivity(intent);
           finish();
       } else {
           Intent intent = new Intent(splashScreen.this, RegisterMainActivity.class);
           startActivity(intent);
           finish();
       }
    }
}
