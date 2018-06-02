package nitrr.ecell.e_cell;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RegisterMainActivity extends AppCompatActivity {
    CardView registerManual, registerFacebook, topPanel;
    Button visitSite;
    ImageView fbImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        registerManual = findViewById(R.id.register_manual);
        registerFacebook = findViewById(R.id.register_facebook);
        topPanel = findViewById(R.id.register_top);
        visitSite = findViewById(R.id.register_visit_site);

        fbImage = findViewById(R.id.register_facebook_image);

        registerManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMainActivity.this, ManualSignUpActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(topPanel, "cardViewTransition");
                    pairs[1] = new Pair<View, String>(visitSite, "buttonTransition");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterMainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        registerFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterMainActivity.this, FacebookActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(fbImage, "facebookImageTransition");
                    pairs[1] = new Pair<View, String>(visitSite, "buttonTransition");


                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterMainActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });
    }
}
