package nitrr.ecell.e_cell.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.auth.AuthenticationResponse;
import nitrr.ecell.e_cell.model.auth.LoginDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.PrefUtils;
import nitrr.ecell.e_cell.utils.ProgressDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText EditText_Email, EditText_Password;
    private TextInputLayout Layout_Email, Layout_Password;
    private Button Sign_in;
    private String Email, Password;
    private LoginDetails loginDetails;
    private PrefUtils prefUtils;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
    }

    private void initview() {
        loginDetails = new LoginDetails();
        prefUtils = new PrefUtils(this);
        Typeface raleway = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/raleway.ttf");
        EditText_Email = findViewById(R.id.inputusername);
        Layout_Email = findViewById(R.id.layout_email);
        Layout_Password = findViewById(R.id.layout_password);
        EditText_Password = findViewById(R.id.inputpassword);
        Sign_in = findViewById(R.id.signinbutton);

        Sign_in.setOnClickListener(this);

        Layout_Password.setTypeface(raleway);
        progressDialog = new ProgressDialog();
    }

    private void apiCall() {
        setData();
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationResponse> call = apiServices.sendLoginDetails(loginDetails);
        call.enqueue(new Callback<AuthenticationResponse>() {

            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()) {
                    AuthenticationResponse jsonResponse = response.body();
                    if (null != jsonResponse && jsonResponse.getSuccess()) {
                        Toast.makeText(LoginActivity.this, jsonResponse.getMessage(), Toast.LENGTH_LONG).show();
                        prefUtils.saveAccessToken(jsonResponse.getToken());
                        prefUtils.setIsLoggedIn(true);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable throwable) {
                progressDialog.hideDialog();
                Toast.makeText(LoginActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
        Email = EditText_Email.getText().toString().trim();
        Password = EditText_Password.getText().toString().trim();
        loginDetails.setEmail(Email);
        loginDetails.setPassword(Password);

    }

    private boolean checkinput() {
        String str[] = {EditText_Email.getText().toString().trim(), EditText_Password.getText().toString().trim()};

        for (String s : str)
            if (s.equals(""))
                return false;

        return true;
    }

    @Override
    public void onClick(View v) {

        if (v == Sign_in) {
            if (checkinput()) {
                if (!Patterns.EMAIL_ADDRESS.matcher(EditText_Email.getText().toString()).matches()) {
                    Toast.makeText(LoginActivity.this, "Invalid email-id", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.showDialog("Logging in. Please wait...", LoginActivity.this);
                    apiCall();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Required fields can't be empty.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
