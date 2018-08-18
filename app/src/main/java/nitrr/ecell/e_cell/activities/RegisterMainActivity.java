package nitrr.ecell.e_cell.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.utils.FacebookSignUp;
import nitrr.ecell.e_cell.utils.ProgressDialog;

public class RegisterMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button signIn, signUp;
    private TextView orContinueWith;
    private LinearLayout facebookSignUp;
    private FacebookSignUp fbSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbSignUp.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    private void init() {
        facebookSignUp = findViewById(R.id.register_facebook);

        signIn = findViewById(R.id.register_sign_in);
        signUp = findViewById(R.id.register_sign_up);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        orContinueWith = findViewById(R.id.register_orContinueWith);

        Typeface helvetica = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica.ttf");

        orContinueWith.setTypeface(helvetica);
        fbSignUp = new FacebookSignUp(RegisterMainActivity.this, facebookSignUp);
        fbSignUp.initialize();
    }

    @Override
    public void onClick(View v) {
        if (v == signIn) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(RegisterMainActivity.this, login_activity.class);
                    startActivity(intent);
                    finish();
                }
            }, 150);

        } else if (v == signUp) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(RegisterMainActivity.this, ManualSignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 150);
        }
    }
}
