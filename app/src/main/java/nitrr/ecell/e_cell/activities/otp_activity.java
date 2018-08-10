package nitrr.ecell.e_cell.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.model.aboutus.UserDetails;
import nitrr.ecell.e_cell.model.otp.SendOtpResponse;
import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.otp.VerifyOtp;
import nitrr.ecell.e_cell.model.otp.otpSendNumber;
import nitrr.ecell.e_cell.model.otp.sendOtp;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.PrefUtils;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText EditText_mobilenumber, EditText_otp;
    private TextView OTP_button, Proceed_afterotp_button;
    private String Mobile_no, OTP_entered;
    private LinearLayout FirstLayout, SecondLayout;
    private PrefUtils prefUtils;
    private UserDetails userDetails;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);

        initview();

    }

    private void initview() {
        prefUtils = new PrefUtils(otp_activity.this);
        userDetails = new UserDetails();

        EditText_mobilenumber = findViewById(R.id.input_mobilenumber);
        EditText_otp = findViewById(R.id.input_otp);
        OTP_button = findViewById(R.id.otp_button);
        Proceed_afterotp_button = findViewById(R.id.proceedafterotp_button);

        FirstLayout = findViewById(R.id.layout_first);
        SecondLayout = findViewById(R.id.layout_second);

        OTP_button.setOnClickListener(this);
        Proceed_afterotp_button.setOnClickListener(this);

        if (prefUtils.getIfIsFacebookLogin()) {
            FirstLayout.setVisibility(View.VISIBLE);
            SecondLayout.setVisibility(View.GONE);

        } else {
            SecondLayout.setVisibility(View.VISIBLE);
            FirstLayout.setVisibility(View.GONE);
        }
        progressDialog = new ProgressDialog();
    }

    private void apicallSendOtp() {
        progressDialog.showDialog("Sending you OTP.Please wait...", this);
        Mobile_no = EditText_mobilenumber.getText().toString().trim();

        otpSendNumber otpSendNumber = new otpSendNumber();
        otpSendNumber.setMobile_no(Mobile_no);
        otpSendNumber.setToken(prefUtils.getAccessToken());


        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<SendOtpResponse> call = apiServices.sendMobileNo(otpSendNumber);
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    SendOtpResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        Toast.makeText(otp_activity.this, jsonResponse.getMessage(), Toast.LENGTH_LONG).show();

                        SecondLayout.setVisibility(View.VISIBLE);
                        FirstLayout.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(otp_activity.this, response.message(), Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.hideDialog();
                Toast.makeText(otp_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void apicallVerifyOtp() {
        progressDialog.showDialog("Verifying OTP. Please wait...", this);
        sendOtp sendOtp = new sendOtp();
        PrefUtils utils = new PrefUtils(otp_activity.this);

        OTP_entered = EditText_otp.getText().toString().trim();

        sendOtp.setOtpEnterd(OTP_entered);
        sendOtp.setToken(utils.getAccessToken());

        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<VerifyOtp> call = apiServices.sendOtpEntered(sendOtp);
        call.enqueue(new Callback<VerifyOtp>() {
            @Override
            public void onResponse(Call<VerifyOtp> call, Response<VerifyOtp> response) {
                progressDialog.hideDialog();

                if (response.isSuccessful()) {
                    VerifyOtp jsonResponse = response.body();
                    if (null != jsonResponse) {
                        Toast.makeText(otp_activity.this, "Otp verified", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(otp_activity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(otp_activity.this, "Something went wrong. Please try again", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.hideDialog();
                Toast.makeText(otp_activity.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    private boolean checkmobileno() {
        String MobilePattern = "[0-9]{10}";
        if (!EditText_mobilenumber.getText().toString().matches(MobilePattern) || EditText_mobilenumber.length() != 10) {

            Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            return false;
        } else
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
            if (str.equals("")) {
                Toast.makeText(otp_activity.this, "Required fields can't be empty.", Toast.LENGTH_LONG).show();
            } else {
                apicallVerifyOtp();
            }
        }


    }
}
