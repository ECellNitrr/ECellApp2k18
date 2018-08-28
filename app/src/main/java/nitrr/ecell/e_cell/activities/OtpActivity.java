package nitrr.ecell.e_cell.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import nitrr.ecell.e_cell.model.aboutus.UserDetails;
import nitrr.ecell.e_cell.model.auth.AuthenticationResponse;
import nitrr.ecell.e_cell.model.otp.SendOtpResponse;
import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.otp.VerifyOtp;
import nitrr.ecell.e_cell.model.otp.otpSendNumber;
import nitrr.ecell.e_cell.model.otp.sendOtp;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import nitrr.ecell.e_cell.utils.PrefUtils;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText EditText_mobilenumber, EditText_otp;
    private TextView OTP_button, Proceed_afterotp_button, tvTimer;
    private LinearLayout FirstLayout, SecondLayout;
    private RelativeLayout rlResendOtp;
    public static final int MILLISECONDS_RESEND_TIMER = 30000;
    public static final int MILLISECONDS_RESEND_TIMER_INTERVAL = 1000;
    private String Mobile_no, OTP_entered;
    private PrefUtils prefUtils;
    private UserDetails userDetails;
    private ProgressDialog progressDialog;
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);
        initview();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userDetails = gson.fromJson(bundle.getString("userDetails"), UserDetails.class);
        }
        resendOtpCountDownTimer();

    }

    private void initview() {
        prefUtils = new PrefUtils(OtpActivity.this);

        EditText_mobilenumber = findViewById(R.id.input_mobilenumber);
        EditText_otp = findViewById(R.id.input_otp);
        OTP_button = findViewById(R.id.otp_button);
        Proceed_afterotp_button = findViewById(R.id.proceedafterotp_button);
        tvTimer = findViewById(R.id.tvTimer);
        rlResendOtp = findViewById(R.id.rlResendOtp);
        FirstLayout = findViewById(R.id.layout_first);
        SecondLayout = findViewById(R.id.layout_second);
        rlResendOtp = findViewById(R.id.rlResendOtp);

        OTP_button.setOnClickListener(this);
        Proceed_afterotp_button.setOnClickListener(this);
        rlResendOtp.setOnClickListener(this);

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


        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<SendOtpResponse> call = apiServices.sendMobileNo(otpSendNumber);
        call.enqueue(new Callback<SendOtpResponse>() {
            @Override
            public void onResponse(Call<SendOtpResponse> call, Response<SendOtpResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    SendOtpResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        Toast.makeText(OtpActivity.this, jsonResponse.getMessage(), Toast.LENGTH_LONG).show();
                        SecondLayout.setVisibility(View.VISIBLE);
                        FirstLayout.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(OtpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.hideDialog();
                if (!NetworkUtils.isNetworkAvailable(OtpActivity.this)) {
                    DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, OtpActivity.this, clickListenerPositiveSendOtp, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                } else {
                    Toast.makeText(OtpActivity.this, getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void apicallVerifyOtp() {
        progressDialog.showDialog("Verifying OTP. Please wait...", this);
        sendOtp sendOtp = new sendOtp();

        OTP_entered = EditText_otp.getText().toString().trim();
        sendOtp.setOtpEnterd(OTP_entered);
        sendOtp.setToken(prefUtils.getAccessToken());

        ApiServices apiServices = AppClient.getInstance().createServiceWithAuth(ApiServices.class, this);
        Call<VerifyOtp> call = apiServices.sendOtpEntered(sendOtp);
        call.enqueue(new Callback<VerifyOtp>() {
            @Override
            public void onResponse(Call<VerifyOtp> call, Response<VerifyOtp> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    VerifyOtp jsonResponse = response.body();
                    if (null != jsonResponse) {
                        if (jsonResponse.getSuccess()) {
                            Toast.makeText(OtpActivity.this, "Otp verified", Toast.LENGTH_LONG).show();
                            prefUtils.setIsLoggedIn(true);
                            Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(OtpActivity.this, jsonResponse.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(OtpActivity.this, getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.hideDialog();
                if (!NetworkUtils.isNetworkAvailable(OtpActivity.this)) {
                    DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, OtpActivity.this, clickListenerPositiveVerifyOtp, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                } else {
                    Toast.makeText(OtpActivity.this, getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    private DialogInterface.OnClickListener clickListenerPositiveVerifyOtp = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            apicallVerifyOtp();
        }
    };

    private DialogInterface.OnClickListener clickListenerPositiveSendOtp = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
           apicallSendOtp();
        }
    };

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
                Toast.makeText(OtpActivity.this, "Invalid phone no.", Toast.LENGTH_LONG).show();
            }


        } else if (v == Proceed_afterotp_button) {

            String str = EditText_otp.getText().toString().trim();
            if (str.equals("")) {
                Toast.makeText(OtpActivity.this, "Required fields can't be empty.", Toast.LENGTH_LONG).show();
            } else {
                apicallVerifyOtp();
            }
        }

        else if (v == rlResendOtp) {
            resendOtpApiCall();
            resendOtpCountDownTimer();
        }
    }

    private void resendOtpApiCall() {
        progressDialog.showDialog("Resending the otp. Please wait...", this);
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationResponse> call = apiServices.getAnotherOtp(userDetails);
        call.enqueue(new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    AuthenticationResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        String token = jsonResponse.getToken();
                        PrefUtils utils = new PrefUtils(OtpActivity.this);
                        utils.saveAccessToken(token);
                        utils.saveUserName(userDetails.getFirstName());
                        if(!jsonResponse.getSuccess()) {
                            Toast.makeText(OtpActivity.this, jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(OtpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                progressDialog.hideDialog();
                Toast.makeText(OtpActivity.this, t.getMessage()+"Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resendOtpCountDownTimer() {
        rlResendOtp.setVisibility(View.GONE);
        tvTimer.setVisibility(View.VISIBLE);
        new CountDownTimer(MILLISECONDS_RESEND_TIMER, MILLISECONDS_RESEND_TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(Long.toString(millisUntilFinished/1000).length()>1){
                    tvTimer.setText(String.valueOf("00:" + millisUntilFinished/1000));
                }
                else{
                    tvTimer.setText(String.valueOf("00:0" + millisUntilFinished/1000));
                }
            }

            @Override
            public void onFinish() {
                rlResendOtp.setVisibility(View.VISIBLE);
                tvTimer.setVisibility(View.GONE);
            }
        }.start();
    }


}
