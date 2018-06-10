package nitrr.ecell.e_cell.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.AuthenticationResponse;
import nitrr.ecell.e_cell.model.UserDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.CustomTextWatcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManualSignUpActivity extends AppCompatActivity {

    private EditText inputUserName, inputPhone, inputPassword, inputConfirm, inputFName, inputLName, inputEmail;
    private TextInputLayout inputUserNameLayout, inputPhoneLayout, inputPasswordLayout, inputConfirmLayout, inputFNameLayout, inputLNameLayout, inputEmailLayout;
    private Button signUpProceed, back;
    private ProgressBar signUpProgressBar;
    private LinearLayout layoutFirst, layoutSecond;
    private Boolean proceed = true, first = true;
    private String userName, mobile, password, firstName, lastName, email;

    private UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_signup_layout);
        userDetails = new UserDetails();

        initView();
    }

    private void initView() {

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
        back = findViewById(R.id.sign_up_back);

        signUpProgressBar = findViewById(R.id.signUpProgressBar);

        layoutFirst = findViewById(R.id.lay_first);
        layoutSecond = findViewById(R.id.lay_second);

        back.setEnabled(false);

        signUpProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first) {
                    signUpProceed.setText(getResources().getString(R.string.sign_up));
                    layoutSecond.animate().translationX(0).setDuration(500);
                    layoutFirst.animate().translationX(-1000).setDuration(500);
                    back.animate().alpha(1.0f).setDuration(300);

                    back.setEnabled(true);
                    first = false;
                    inputPassword.requestFocus();

                } else {
                    if (isEmpty()) {
                        if (validatePassword())
                            apiCall();
                    } else {
                        Toast.makeText(ManualSignUpActivity.this, "Some of the required fields are empty.", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFirst.animate().translationX(0).setDuration(500);
                layoutSecond.animate().translationX(1000).setDuration(500);
                back.animate().alpha(0.0f).setDuration(300);

                back.setEnabled(false);
                first = true;
                signUpProceed.setText(getResources().getString(R.string.proceed));
                inputFName.requestFocus();

            }
        });

        setTextWatcher();
    }

    private void apiCall() {

        setData();

        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationResponse> call = apiServices.sendRegisterDetails(userDetails);
        call.enqueue(new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {

                if (response.isSuccessful()) {
                    AuthenticationResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        // TODO : Call OTP Activity here
                    }
                } else {
                    Toast.makeText(ManualSignUpActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Toast.makeText(ManualSignUpActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        userName = inputUserName.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        firstName = inputFName.getText().toString().trim();
        lastName = inputLName.getText().toString().trim();
        mobile = inputPhone.getText().toString().trim();
        email = inputEmail.getText().toString().trim();

        userDetails.setUsername(userName);
        userDetails.setContactNumber(mobile);
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setEmail(email);
        userDetails.setPassword(password);
    }

    private void setTextWatcher() {

        inputFName.addTextChangedListener(new CustomTextWatcher(inputFName, inputFNameLayout, AppConstants.FIRST_NAME));
        inputLName.addTextChangedListener(new CustomTextWatcher(inputLName, inputLNameLayout, AppConstants.LAST_NAME));
        inputUserName.addTextChangedListener(new CustomTextWatcher(inputUserName, inputUserNameLayout, AppConstants.USER_NAME));
        inputEmail.addTextChangedListener(new CustomTextWatcher(inputEmail, inputEmailLayout, AppConstants.EMAIL));
        inputPassword.addTextChangedListener(new CustomTextWatcher(inputPassword, inputPasswordLayout, AppConstants.PASSWORD));
        inputPhone.addTextChangedListener(new CustomTextWatcher(inputPhone, inputPhoneLayout, AppConstants.MOBILE_NO));
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().equals(inputConfirm.getText().toString().trim())) {
            inputConfirmLayout.setError("Passwords didn't match.");
            inputConfirm.requestFocus();

            return false;
        } else {
            inputConfirmLayout.setErrorEnabled(false);

            return true;
        }
    }

    private boolean isEmpty() {
        String str[] = {inputFName.getText().toString().trim(), inputLName.getText().toString().trim(), inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim(), inputPhone.getText().toString().trim()};

        for (String s : str)
            if (s.equals(""))
                return false;

        return true;
    }
}

