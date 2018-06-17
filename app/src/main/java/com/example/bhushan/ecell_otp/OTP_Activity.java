package com.example.bhushan.ecell_otp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bhushan.ecell_otp.Model.OtpDetails;

public class OTP_Activity extends AppCompatActivity {

    private EditText EditText_mobilenumber,EditText_otp;
    private Button OTP_button,Proceed_afterotp_button;
    private String Mobile_no,OTP_entered;
    private ProgressBar otp_progressbar;
    private LinearLayout FirstLayout,SecondLayout;
    private OtpDetails otpDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);


        EditText_mobilenumber = findViewById(R.id.input_mobilenumber);
        EditText_otp = findViewById(R.id.input_otp);
        OTP_button=findViewById(R.id.otp_button);
        Proceed_afterotp_button=findViewById(R.id.proceedafterotp_button);
        otp_progressbar=findViewById(R.id.otpprogressbar);

        FirstLayout = findViewById(R.id.layout_first);
        SecondLayout = findViewById(R.id.layout_second);

        OTP_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkmobileno()){
                    // OTP_call
                }
                else{
                    Toast.makeText(OTP_Activity.this,"Invalid Mobile Number",Toast.LENGTH_LONG).show();
                }
            }
        });


        Proceed_afterotp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // otp verification
            }
        });

    }

    // otp call (Remaining)

    private void setData() {
        Mobile_no = EditText_mobilenumber.getText().toString().trim();
        OTP_entered = EditText_otp.getText().toString().trim();

        otpDetails.setMobile_no(Mobile_no);
        otpDetails.setOtp(OTP_entered);

    }

    private boolean checkmobileno(){
       // String str= EditText_mobilenumber.getText().toString().trim();
        String MobilePattern = "[0-9]{10}";
        if( !EditText_mobilenumber.getText().toString().matches(MobilePattern) || EditText_mobilenumber.length()!=10) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;



    }

}
