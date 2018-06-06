package nitrr.ecell.e_cell.reg_main.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import nitrr.ecell.e_cell.FacebookSignUp;
import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.sign_up.view.ManualSignUpActivity;

public class RegisterMainActivity extends AppCompatActivity {
    Button signIn, signUp;
    TextView orContinueWith;
    LinearLayout facebookSignUp;
    FacebookSignUp fbSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        facebookSignUp = findViewById(R.id.register_facebook);

        signIn = findViewById(R.id.register_sign_in);
        signUp = findViewById(R.id.register_sign_up);
        orContinueWith = findViewById(R.id.register_orContinueWith);

        Typeface helvetica = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica.ttf");

        orContinueWith.setTypeface(helvetica);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        /*
                         * Sign In Activity Call
                         * */

                        //finish();
                    }
                }, 150);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        fbSignUp = new FacebookSignUp(RegisterMainActivity.this, facebookSignUp);
        fbSignUp.initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fbSignUp.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }
}
