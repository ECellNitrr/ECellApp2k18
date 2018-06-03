package nitrr.ecell.e_cell;

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

public class SignInActivity extends AppCompatActivity {
    Button signInButton;
    EditText inputUser, inputPassword;
    TextInputLayout inputUserLayout, inputPassLayout;
    TextView signInOne, signInTwo, signInThree, forgotPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_main_layout);

        Typeface ralewayBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/ralewaybold.ttf");
        Typeface helvetica = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/helvetica.ttf");

        inputUserLayout = findViewById(R.id.sign_in_user_lay);
        inputPassLayout = findViewById(R.id.sign_in_pass_lay);

        inputUser = findViewById(R.id.sign_in_user);
        inputPassword = findViewById(R.id.sign_in_pass);

        signInOne = findViewById(R.id.sign_in_one);
        signInTwo = findViewById(R.id.sign_in_two);
        signInThree = findViewById(R.id.sign_in_three);
        forgotPassword = findViewById(R.id.sign_in_for_pass);

        signInButton = findViewById(R.id.sign_in_but);

        inputUserLayout.setTypeface(ralewayBold);
        inputPassLayout.setTypeface(ralewayBold);
        signInOne.setTypeface(ralewayBold);
        signInTwo.setTypeface(ralewayBold);
        signInThree.setTypeface(ralewayBold);
        signInButton.setTypeface(ralewayBold);
        forgotPassword.setTypeface(helvetica);

        inputUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inputUser.getText().toString().trim().isEmpty()) {
                    inputUserLayout.setError("Enter your Name.");
                    inputUser.requestFocus();
                } else {
                    if (!Pattern.matches("[a-z A-Z]+", inputUser.getText()))
                        inputUserLayout.setError("User Name should not contain any numbers.");
                    else
                        inputUserLayout.setErrorEnabled(false);
                }
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
