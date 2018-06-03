package nitrr.ecell.e_cell;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class ManualSignUpActivity extends AppCompatActivity {

    EditText inputName, inputPhone, inputPassword, inputConfirm;
    TextInputLayout inputNameLayout, inputPhoneLayout, inputPasswordLayout, inputConfirmLayout;
    TextView signUpOne, signUpTwo, signUpThree;
    Button signUp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_signup_layout);

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

        inputNameLayout.setTypeface(ralewayBold);
        inputPhoneLayout.setTypeface(ralewayBold);
        inputPasswordLayout.setTypeface(ralewayBold);
        inputConfirmLayout.setTypeface(ralewayBold);
        signUpOne.setTypeface(ralewayBold);
        signUpTwo.setTypeface(ralewayBold);
        signUpThree.setTypeface(ralewayBold);
        signUp.setTypeface(ralewayBold);


        inputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputName.getText().toString().trim().isEmpty()) {
                    inputNameLayout.setError("Enter your Name.");
                    inputName.requestFocus();
                } else {
                    if (!Pattern.matches("[a-z A-Z]+", inputName.getText()))
                        inputNameLayout.setError("User Name should not contain any numbers.");
                    else
                        inputNameLayout.setErrorEnabled(false);
                }
            }
        });


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
                    Intent intent = new Intent(ManualSignUpActivity.this, OTPActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateDetails() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputNameLayout.setError("Enter your Name.");
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

