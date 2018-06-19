package com.example.bhushan.ecell_login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhushan.ecell_login.Model.AuthenticationResponse;
import com.example.bhushan.ecell_login.Model.Logindetails;
import com.example.bhushan.ecell_login.restapi.ApiServices;
import com.example.bhushan.ecell_login.restapi.AppClient;

import retrofit2.Response;

public class login_activity extends AppCompatActivity {

    private EditText EditText_UserName,EditText_Password;
    private Button Sign_in;
    private TextView ForgetPassword;
    private String Username,Password;
    private ProgressBar SignInProgressBar;
    private Logindetails logindetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_UserName = findViewById(R.id.inputusername);
        EditText_Password = findViewById(R.id.inputpassword);
        Sign_in = findViewById(R.id.signinbutton);
        ForgetPassword = findViewById(R.id.forgetpassword);
        SignInProgressBar = findViewById(R.id.signinprogressbar);

        //

        //

        Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkinput()){
                    apiCall();
                }
                else{
                    Toast.makeText(login_activity.this,"Required fields can't be empty.",Toast.LENGTH_LONG).show();
                }

            }
        });

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  // otp activity
            }
        });

    }

    //
    private void apiCall(){

        setData();

        ApiServices apiServices= AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationResponse> call=apiServices.sendRegisterDetails(logindetails);
        call.enqueue(new Call.Callback<AuthenticationResponse>(){

            @Override
            public void onResponce(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response ){

                if(response.isSuccessful()) {
                    AuthenticationResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        Toast.makeText(login_activity.this,"login successfull",Toast.LENGTH_LONG).show();
                        // login succcessfull  (home activity)
                    }
                } else{
                        Toast.makeText(login_activity.this,"Something went wrong. Please try again",Toast.LENGTH_LONG).show();

                    }


            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call,Throwable throwable){
                Toast.makeText(login_activity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
            }



        });

    }



    private void setData() {
        Username = EditText_UserName.getText().toString().trim();
        Password = EditText_Password.getText().toString().trim();

        logindetails.setUsername(Username);
        logindetails.setPassword(Password);

    }

    private boolean checkinput() {
        String str[] = { EditText_UserName.getText().toString().trim(), EditText_Password.getText().toString().trim()};

        for (String s : str)
            if (s.equals(""))
                return false;

        return true;
    }

}
