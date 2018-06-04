package nitrr.ecell.e_cell.reg_main.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.sign_up.view.ManualSignUpActivity;

public class RegisterMainActivity extends AppCompatActivity {
    Button signIn, signUp;
    TextView facebookSignInText, orContinueWith;
    ImageView ecellImage;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        loginButton = findViewById(R.id.login_button);

        signIn = findViewById(R.id.register_sign_in);
        signUp = findViewById(R.id.register_sign_up);
        facebookSignInText = findViewById(R.id.register_facebook_text);
        orContinueWith = findViewById(R.id.register_orContinueWith);

        ecellImage = findViewById(R.id.ecell_image);

        Typeface ralewayBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ralewaybold.ttf");
        Typeface helvetica = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica.ttf");

        signIn.setTypeface(ralewayBold);
        signUp.setTypeface(ralewayBold);
        facebookSignInText.setTypeface(ralewayBold);
        orContinueWith.setTypeface(helvetica);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 *  Sign In Activity Call..
                 * */
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMainActivity.this, ManualSignUpActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(signUp, "signUpButtonTrans");
                    pairs[1] = new Pair<View, String>(ecellImage, "imageTrans");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterMainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

    }

    public void facebookSignUp(View view) {
        loginButton.performClick();
    }
}
