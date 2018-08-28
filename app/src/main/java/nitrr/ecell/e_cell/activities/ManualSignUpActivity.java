package nitrr.ecell.e_cell.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.auth.AuthenticationResponse;
import nitrr.ecell.e_cell.model.aboutus.UserDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.CustomTextWatcher;
import nitrr.ecell.e_cell.utils.PrefUtils;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManualSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputPhone, inputPassword, inputConfirm, inputFName, inputLName, inputEmail;
    private TextInputLayout inputPhoneLayout, inputPasswordLayout, inputConfirmLayout, inputFNameLayout, inputLNameLayout, inputEmailLayout;
    private Button signUpProceed, back;
    private LinearLayout layoutFirst, layoutSecond;
    private Boolean proceed, first;
    private String mobile, password, firstName, lastName, email;
    private RelativeLayout layout;
    private TextView tvPrivacyPolicy, tvTermsAndConditions;
    private LinearLayout llTC;

    private UserDetails userDetails;
    private ProgressDialog progressDialog;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_signup_layout);
        userDetails = new UserDetails();

        initView();
    }

    private void initView() {

        PrefUtils utils = new PrefUtils(ManualSignUpActivity.this);
        utils.isFacebookLogin(false);

        first = true;
        proceed = true;

        layout = findViewById(R.id.main);
        layout.setOnClickListener(this);

        Typeface raleway = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/raleway.ttf");

        inputPhoneLayout = findViewById(R.id.man_mob_lay);
        inputPasswordLayout = findViewById(R.id.man_pass_lay);
        inputConfirmLayout = findViewById(R.id.man_re_lay);
        inputEmailLayout = findViewById(R.id.man_email_lay);
        inputFNameLayout = findViewById(R.id.man_firstname_lay);
        inputLNameLayout = findViewById(R.id.man_lastname_lay);

        inputPhone = findViewById(R.id.man_mob);
        inputPassword = findViewById(R.id.man_pass);
        inputConfirm = findViewById(R.id.man_re);
        inputEmail = findViewById(R.id.man_email);
        inputFName = findViewById(R.id.man_firstname);
        inputLName = findViewById(R.id.man_lastname);

        signUpProceed = findViewById(R.id.sign_up_proceed);
        back = findViewById(R.id.sign_up_back);

        signUpProceed.setOnClickListener(this);
        back.setOnClickListener(this);

        layoutFirst = findViewById(R.id.lay_first);
        layoutSecond = findViewById(R.id.lay_second);
        llTC = findViewById(R.id.llTC);
        llTC.setVisibility(View.GONE);
        tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicy);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        tvPrivacyPolicy.setOnClickListener(this);
        tvTermsAndConditions.setOnClickListener(this);

        inputFName.requestFocus();

        inputPasswordLayout.setTypeface(raleway);
        inputConfirmLayout.setTypeface(raleway);

        back.setEnabled(false);

        setTextWatcher();
        progressDialog = new ProgressDialog();
    }

    private void apiCall() {
        progressDialog.showDialog("Registering you.Please wait...", this);
        setData();
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationResponse> call = apiServices.sendRegisterDetails(userDetails);
        call.enqueue(new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    AuthenticationResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        String token = jsonResponse.getToken();
                        PrefUtils utils = new PrefUtils(ManualSignUpActivity.this);
                        utils.saveAccessToken(token);
                        utils.saveUserName(firstName);
                        if(jsonResponse.getSuccess()) {
                            Intent intent = new Intent(ManualSignUpActivity.this, OtpActivity.class);
                            intent.putExtra("userDetails", gson.toJson(userDetails));
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(ManualSignUpActivity.this, jsonResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(ManualSignUpActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                progressDialog.hideDialog();
                Toast.makeText(ManualSignUpActivity.this, t.getMessage()+"Failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        password = inputPassword.getText().toString().trim();
        firstName = inputFName.getText().toString().trim();
        lastName = inputLName.getText().toString().trim();
        mobile = inputPhone.getText().toString().trim();
        email = inputEmail.getText().toString().trim();

        userDetails.setContactNumber(mobile);
        userDetails.setFirstName(firstName);
        userDetails.setLastName(lastName);
        userDetails.setEmail(email);
        userDetails.setPassword(password);
    }

    private void setTextWatcher() {

        inputFName.addTextChangedListener(new CustomTextWatcher(ManualSignUpActivity.this, inputFName, inputFNameLayout, AppConstants.FIRST_NAME));
        inputLName.addTextChangedListener(new CustomTextWatcher(ManualSignUpActivity.this, inputLName, inputLNameLayout, AppConstants.LAST_NAME));
        inputEmail.addTextChangedListener(new CustomTextWatcher(ManualSignUpActivity.this, inputEmail, inputEmailLayout, AppConstants.EMAIL));
        inputPassword.addTextChangedListener(new CustomTextWatcher(ManualSignUpActivity.this, inputPassword, inputPasswordLayout, AppConstants.PASSWORD));
        inputPhone.addTextChangedListener(new CustomTextWatcher(ManualSignUpActivity.this, inputPhone, inputPhoneLayout, AppConstants.MOBILE_NO));

    }

    private boolean validatePassword() {
        if (!inputPassword.getText().toString().trim().equals(inputConfirm.getText().toString().trim())) {
            inputConfirmLayout.setError(getResources().getString(R.string.error_con_pass));
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

    @Override
    public void onClick(View v) {

        if (v == signUpProceed) {
            if (first) {
                signUpProceed.setText(getResources().getString(R.string.sign_up));
                layoutSecond.animate().translationX(0).setDuration(500);
                layoutFirst.animate().translationX(-1000).setDuration(500);
                back.animate().alpha(1.0f).setDuration(300);
                back.setEnabled(true);
                first = false;
                inputPassword.requestFocus();
                showKeyboard();
                llTC.setVisibility(View.VISIBLE);

            } else {
                if (isEmpty()) {
                    if (validatePassword())
                        apiCall();
                } else {
                    Toast.makeText(ManualSignUpActivity.this, getResources().getString(R.string.required_fields), Toast.LENGTH_LONG).show();
                }
            }

        } else if (v == back) {

            layoutFirst.animate().translationX(0).setDuration(500);
            layoutSecond.animate().translationX(1000).setDuration(500);
            back.animate().alpha(0.0f).setDuration(300);

            llTC.setVisibility(View.GONE);
            back.setEnabled(false);
            first = true;
            signUpProceed.setText(getResources().getString(R.string.proceed));

            inputFName.requestFocus();
            showKeyboard();

        } else if (v == layout) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
        else if(v == tvPrivacyPolicy){
            openWebsiteUrl("https://ecell.nitrr.ac.in/privacy_policy/");
        }
        else if(v == tvTermsAndConditions){
            openWebsiteUrl("https://ecell.nitrr.ac.in/terms/");
        }
    }

    private void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void openWebsiteUrl(String url) {
            Uri uri = Uri.parse(url);
            CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
            intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
            intentBuilder.setStartAnimations(this, R.anim.slide_up, R.anim.slide_down);
            intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right);
            CustomTabsIntent customTabsIntent = intentBuilder.build();
            customTabsIntent.launchUrl(this, uri);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, RegisterMainActivity.class);
        startActivity(intent);
        finish();
    }

}

