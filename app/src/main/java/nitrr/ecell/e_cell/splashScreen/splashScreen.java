package nitrr.ecell.e_cell.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.RegisterMainActivity;
import nitrr.ecell.e_cell.otp.activity.otp_activity;

public class splashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeIntent=new Intent(splashScreen.this,RegisterMainActivity.class);
                startActivity(homeIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
