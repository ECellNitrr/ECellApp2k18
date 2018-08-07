package nitrr.ecell.e_cell.OTP.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.signin.activities.login_activity;
import nitrr.ecell.e_cell.signin.model.AuthenticationLoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp_activity extends AppCompatActivity implements View.OnClickListener{

    private EditText EditText_mobilenumber,EditText_otp;
    private Button OTP_button,Proceed_afterotp_button;
    private String Mobile_no,OTP_entered;
    private LinearLayout FirstLayout,SecondLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);

        initview();


    }

    private void initview() {


        EditText_mobilenumber = findViewById(R.id.input_mobilenumber);
        EditText_otp = findViewById(R.id.input_otp);
        OTP_button=findViewById(R.id.otp_button);
        Proceed_afterotp_button=findViewById(R.id.proceedafterotp_button);

        FirstLayout = findViewById(R.id.layout_first);
        SecondLayout = findViewById(R.id.layout_second);

        OTP_button.setOnClickListener(this);
        Proceed_afterotp_button.setOnClickListener(this);

        SecondLayout.setVisibility(View.INVISIBLE);
        FirstLayout.setVisibility(View.VISIBLE);

    }

    private void apicallSendOtp(){

           Mobile_no=EditText_mobilenumber.getText().toString().trim();

        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<> call = apiServices.sendMobileNo(Mobile_no);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(otp_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void apicallVerifyOtp(){

        OTP_entered=EditText_otp.getText().toString().trim();

        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<> call = apiServices.sendMobileNo(OTP_entered);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(otp_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }



    }

    private boolean checkmobileno(){
        String MobilePattern = "[0-9]{10}";
        if( !EditText_mobilenumber.getText().toString().matches(MobilePattern) || EditText_mobilenumber.length()!=10) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;



    }

    @Override
    public void onClick(View v) {

        if (v == OTP_button) {

            if (checkmobileno()) {

                apicallSendOtp();

            } else {
                Toast.makeText(otp_activity.this, "Invalid phone no.", Toast.LENGTH_LONG).show();
            }


        } else if (v == Proceed_afterotp_button) {

            String str = EditText_otp.getText().toString().trim();
            if (str.equals(""))
            {
                Toast.makeText(otp_activity.this, "Required fields can't be empty.", Toast.LENGTH_LONG).show();
            }
            else
            {
               apicallVerifyOtp();
            }
        }


    }
}
