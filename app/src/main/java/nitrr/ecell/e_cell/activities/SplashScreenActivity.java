package nitrr.ecell.e_cell.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import nitrr.ecell.e_cell.BuildConfig;
import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.SplashScreenResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import nitrr.ecell.e_cell.utils.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private PrefUtils prefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        prefUtils = new PrefUtils(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                apiCallForUpdateCheck();
            }
        }, 1000);
    }

    private void apiCallForUpdateCheck() {
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<SplashScreenResponse> call = apiServices.getAppUpdate();
        call.enqueue(new Callback<SplashScreenResponse>() {
            @Override
            public void onResponse(Call<SplashScreenResponse> call, Response<SplashScreenResponse> response) {
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
                    } else {
                        Toast.makeText(SplashScreenActivity.this, getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SplashScreenResponse> call, Throwable t) {
                    if (!NetworkUtils.isNetworkAvailable(getApplicationContext())) {
                        DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, SplashScreenActivity.this, clickListenerPositiveConnection, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                    } else {
                        Toast.makeText(SplashScreenActivity.this, getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();
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
        DialogFactory.showDialog(DialogFactory.UPDATE_APP, SplashScreenActivity.this, new DialogInterface.OnClickListener() {
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
           Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
           startActivity(intent);
           finish();
       } else {
           Intent intent = new Intent(SplashScreenActivity.this, RegisterMainActivity.class);
           startActivity(intent);
           finish();
       }
    }
}
