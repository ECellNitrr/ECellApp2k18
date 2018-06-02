package nitrr.ecell.e_cell;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class OTPActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification_layout);

        getMessageReadPermission();
    }

    private void getMessageReadPermission() {
        int GET_MY_PERMISSION = 1;
        if (ContextCompat.checkSelfPermission(OTPActivity.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(OTPActivity.this,
                    Manifest.permission.READ_SMS)) {

                Toast.makeText(getApplicationContext(), "Can't proceed without SMS permission.", Toast.LENGTH_LONG).show();

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED)
                    getMessageReadPermission();

            } else {
                ActivityCompat.requestPermissions(OTPActivity.this,
                        new String[]{Manifest.permission.READ_SMS}, GET_MY_PERMISSION);
            }
        }
    }
}
