package nitrr.ecell.e_cell;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FacebookActivity extends AppCompatActivity {
    Button facebookLoginProceedButton;
    CardView parentCardView;
    EditText inputPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_login_screen);

        facebookLoginProceedButton = findViewById(R.id.register_facebook_login_button);
        parentCardView = findViewById(R.id.register_facebook_parent_card);
        inputPhone = findViewById(R.id.register_facebook_phone);

        facebookLoginProceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Verify if given phone number is correct.

                Intent intent = new Intent(FacebookActivity.this, OTPActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(inputPhone, "editTextTransition");
                    pairs[1] = new Pair<View, String>(facebookLoginProceedButton, "buttonTransition");
                    // pairs[2] = new Pair<View, String>(parentCardView, "cardViewTransition");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FacebookActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }
}
