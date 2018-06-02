package nitrr.ecell.e_cell;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ManualSignUpActivity extends AppCompatActivity {

    EditText inputName, inputEmail, inputPhone, inputPassword;
    TextInputLayout inputNameLayout, inputEmailLayout, inputPhoneLayout, inputPasswordLayout;
    Button proceedButton;
    CardView botPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_login_screen);

        botPanel = findViewById(R.id.register_bot_card_view);

        inputNameLayout = findViewById(R.id.register_input_name_layout);
        inputEmailLayout = findViewById(R.id.register_input_email_layout);
        inputPhoneLayout = findViewById(R.id.register_input_phone_layout);
        inputPasswordLayout = findViewById(R.id.register_input_password_layout);

        inputName = findViewById(R.id.register_input_name);
        inputEmail = findViewById(R.id.register_input_email);
        inputPhone = findViewById(R.id.register_input_phone);
        inputPassword = findViewById(R.id.register_input_password);

        proceedButton = findViewById(R.id.register_manual_proceed);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: verify details first.

                Intent intent = new Intent(ManualSignUpActivity.this, OTPActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(proceedButton, "buttonTransition");
                    pairs[1] = new Pair<View, String>(inputPhone, "editTextTransition");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ManualSignUpActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

    }
}
