package nitrr.ecell.e_cell.reg_main.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.sign_up.view.ManualSignUpActivity;

public class RegisterMainActivity extends AppCompatActivity {
    Button signIn, signUp;
    TextView facebookSignInText, orContinueWith;
    ImageView ecellImage;
    CallbackManager mCallbackManager;
    FirebaseAuth mAuth;
    LinearLayout facebookSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        mAuth = FirebaseAuth.getInstance();

        facebookSignUp = findViewById(R.id.register_facebook);

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

        mCallbackManager = CallbackManager.Factory.create();

        facebookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facebookSignUp.setEnabled(false);

                LoginManager.getInstance().logInWithReadPermissions(RegisterMainActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        facebookSignUp.setEnabled(true);
                        Log.d("Facebook SignUp", "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());

                        Toast.makeText(getApplicationContext(), "Facebook Authentication Successful.", Toast.LENGTH_SHORT).show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                /*
                                 * OTP Activity Call
                                 * */

                                //finish();
                            }
                        }, 150);
                    }

                    @Override
                    public void onCancel() {
                        facebookSignUp.setEnabled(true);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        facebookSignUp.setEnabled(true);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("Facebook SignUp", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            facebookSignUp.setEnabled(true);

                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Facebook SignUp", "signInWithCredential:success");

                        } else {
                            facebookSignUp.setEnabled(true);

                            // If sign in fails, display a message to the user.
                            Log.w("Facebook SignUp", "signInWithCredential:failure", task.getException());
                            Toast.makeText(RegisterMainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
