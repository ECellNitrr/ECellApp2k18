package nitrr.ecell.e_cell.sign_up.view;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.sign_up.Utils.AESCrypt;
import nitrr.ecell.e_cell.sign_up.presenter.SignUpData;
import nitrr.ecell.e_cell.sign_up.presenter.SignUpDataImp;

public class ManualSignUpActivity extends AppCompatActivity implements SignUpInterface {

    EditText inputName, inputPhone, inputPassword, inputConfirm;
    TextInputLayout inputNameLayout, inputPhoneLayout, inputPasswordLayout, inputConfirmLayout;
    TextView signUpOne, signUpTwo, signUpThree;
    Button signUp;
    ProgressBar signUpProgressBar;
    SignUpData signUpInstance;
    AESCrypt aesCrypt = new AESCrypt();

    @Override
    protected void onStart() {
        super.onStart();
        assert signUpInstance != null;
        signUpInstance.getUserData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_signup_layout);

        signUpInstance = new SignUpDataImp(this);

        Typeface ralewayBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ralewaybold.ttf");

        signUpOne = findViewById(R.id.sign_up_one);
        signUpTwo = findViewById(R.id.sign_up_two);
        signUpThree = findViewById(R.id.sign_up_three);

        inputNameLayout = findViewById(R.id.man_username_lay);
        inputPhoneLayout = findViewById(R.id.man_mob_lay);
        inputPasswordLayout = findViewById(R.id.man_pass_lay);
        inputConfirmLayout = findViewById(R.id.man_re_lay);

        inputName = findViewById(R.id.man_username);
        inputPhone = findViewById(R.id.man_mob);
        inputPassword = findViewById(R.id.man_pass);
        inputConfirm = findViewById(R.id.man_re);

        signUp = findViewById(R.id.sign_up_but);

        signUpProgressBar = findViewById(R.id.signUpProgressBar);

        inputNameLayout.setTypeface(ralewayBold);
        inputPhoneLayout.setTypeface(ralewayBold);
        inputPasswordLayout.setTypeface(ralewayBold);
        inputConfirmLayout.setTypeface(ralewayBold);
        signUpOne.setTypeface(ralewayBold);
        signUpTwo.setTypeface(ralewayBold);
        signUpThree.setTypeface(ralewayBold);
        signUp.setTypeface(ralewayBold);


        inputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = inputPhone.getText().toString().trim();
                if ((phone.length() != 10) || (Pattern.matches("[0-9]", phone))) {
                    inputPhoneLayout.setError("Valid Phone Number required.");
                    inputPhone.requestFocus();
                } else {
                    inputPhoneLayout.setErrorEnabled(false);
                }
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ((inputPassword.getText().toString().trim().isEmpty()) || (inputPassword.getText().length() < 8)) {
                    inputPasswordLayout.setError("Valid Password of at least 8 characters required.");
                    inputPassword.requestFocus();
                } else {
                    inputPasswordLayout.setErrorEnabled(false);
                }
            }
        });

        inputConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!inputConfirm.getText().toString().equals(inputPassword.getText().toString())) {
                    inputConfirmLayout.setError("Passwords didn't match.");
                    inputConfirm.requestFocus();
                } else {
                    inputConfirmLayout.setErrorEnabled(false);
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDetails()) {
                    String encPassword = null;

                    try {
                        encPassword = aesCrypt.encrypt(inputPassword.getText().toString().trim());
                    } catch (Exception e) {
                        Log.d("Encryption Error", e.getMessage());
                    }

                    signUpInstance.sendData(inputName.getText().toString().trim(), encPassword, inputPhone.getText().toString().trim());
                    Toast.makeText(getApplicationContext(), "Sign Up Success.", Toast.LENGTH_SHORT).show();

                    /*
                     * OTP Call after successful ..
                     * */

                }
            }
        });
    }


    @Override
    public void showProgressBar(boolean show) {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            if (inputMethodManager != null)
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }

        if (show) {

            signUp.setVisibility(View.GONE);
            signUpProgressBar.setVisibility(View.VISIBLE);
        } else {

            signUpProgressBar.setVisibility(View.GONE);
            signUp.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        AlertDialog.Builder builder;

        if (!activeNetworkInfo.isConnected()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                builder = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_Material_Dialog_Alert);
            else
                builder = new AlertDialog.Builder(getApplicationContext());

            builder.setTitle("Network Error!")
                    .setMessage("Do you want to retry?")
                    .setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isNetworkAvailable();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        return true;
    }

    @Override
    public void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }


    private boolean validateDetails() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputNameLayout.setError("Enter a User Name.");
            return false;
        }

        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputPasswordLayout.setError("Enter a password containing at least 8 characters.");
            return false;
        }

        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputPhoneLayout.setError("Enter your Phone Number.");
            return false;
        }

        return true;
    }
}

