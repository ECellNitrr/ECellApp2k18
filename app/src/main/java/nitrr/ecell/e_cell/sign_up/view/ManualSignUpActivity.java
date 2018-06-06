package nitrr.ecell.e_cell.sign_up.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import nitrr.ecell.e_cell.R;

public class ManualSignUpActivity extends AppCompatActivity {

    EditText inputUserName, inputPhone, inputPassword, inputConfirm, inputFName, inputLName, inputEmail;
    TextInputLayout inputUserNameLayout, inputPhoneLayout, inputPasswordLayout, inputConfirmLayout, inputFNameLayout, inputLNameLayout, inputEmailLayout;
    Button signUpProceed;
    ProgressBar signUpProgressBar;
    CardView cardAbove, cardBelow;
    Boolean proceed = true, first = true;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_signup_layout);

        inputUserNameLayout = findViewById(R.id.man_username_lay);
        inputPhoneLayout = findViewById(R.id.man_mob_lay);
        inputPasswordLayout = findViewById(R.id.man_pass_lay);
        inputConfirmLayout = findViewById(R.id.man_re_lay);
        inputEmailLayout = findViewById(R.id.man_email_lay);
        inputFNameLayout = findViewById(R.id.man_firstname_lay);
        inputLNameLayout = findViewById(R.id.man_lastname_lay);

        inputUserName = findViewById(R.id.man_username);
        inputPhone = findViewById(R.id.man_mob);
        inputPassword = findViewById(R.id.man_pass);
        inputConfirm = findViewById(R.id.man_re);
        inputEmail = findViewById(R.id.man_email);
        inputFName = findViewById(R.id.man_firstname);
        inputLName = findViewById(R.id.man_lastname);

        signUpProceed = findViewById(R.id.sign_up_proceed);

        signUpProgressBar = findViewById(R.id.signUpProgressBar);

        cardAbove = findViewById(R.id.card_above);
        cardBelow = findViewById(R.id.card_below);

        signUpProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first)
                    signUpProceed.setText(getResources().getString(R.string.sign_up));

                if (cardAbove.getAlpha() == 1.0f) {

                    cardAbove.animate().translationY(cardAbove.getHeight() + 350).alpha(0.0f).setDuration(300);
                    cardBelow.setAlpha(1.0f);
                    cardBelow.animate().translationY(0).setDuration(1);

                } else {

                    cardBelow.animate().translationY(cardBelow.getHeight() + 350).alpha(0.0f).setDuration(300);
                    cardAbove.setAlpha(1.0f);
                    cardAbove.animate().translationY(0).setDuration(1);
                }

            }
        });

    }


    private boolean validateDetails() {
        if (inputUserName.getText().toString().trim().isEmpty()) {
            inputUserNameLayout.setError("Enter a User Name.");
            proceed = false;
            return false;
        }

        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputPasswordLayout.setError("Enter a password containing at least 8 characters.");
            proceed = false;
            return false;
        }

        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputPhoneLayout.setError("Enter your Phone Number.");
            proceed = false;
            return false;
        }

        proceed = true;
        return true;
    }
}

