package nitrr.ecell.e_cell.signin.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.telecom.Call;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.signin.model.AuthenticationLoginResponse;
import nitrr.ecell.e_cell.signin.model.Logindetails;
import com.example.bhushan.ecell_login.R;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

public class login_activity extends AppCompatActivity implements View.OnClickListener {

    private EditText EditText_Email,EditText_Password;
    private TextInputLayout Layout_Email,Layout_Password;
    private Button Sign_in;
    private TextView ForgetPassword;
    private String Email,Password;
    private ProgressBar SignInProgressBar;
    private Logindetails logindetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initview();

    }

    private void initview(){

        logindetails =new Logindetails();

        EditText_Email = findViewById(R.id.inputusername);
        Layout_Email=findViewById(R.id.layout_email);
        Layout_Password=findViewById(R.id.layout_password);
        EditText_Password = findViewById(R.id.inputpassword);
        Sign_in = findViewById(R.id.signinbutton);
        ForgetPassword = findViewById(R.id.forgetpassword);
        SignInProgressBar = findViewById(R.id.signinprogressbar);

        Sign_in.setOnClickListener(this);
        ForgetPassword.setOnClickListener(this);


    }

    //
    private void apiCall(){

        setData();

        ApiServices apiServices= AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationLoginResponse> call=apiServices.sendLoginDetails(logindetails);
        call.enqueue(new Callback<AuthenticationLoginResponse>(){

            @Override
            public void onResponse(Call<AuthenticationLoginResponse> call, Response<AuthenticationLoginResponse> response ){

                if(response.isSuccessful()) {
                    AuthenticationLoginResponse jsonResponse = response.body();
                    if (null != jsonResponse) {
                        Toast.makeText(login_activity.this,"login successfull",Toast.LENGTH_LONG).show();
                        // login succcessfull  (home activity)
                    }
                } else{
                        Toast.makeText(login_activity.this,"Something went wrong. Please try again",Toast.LENGTH_LONG).show();

                    }


            }

            @Override
            public void onFailure(Call<AuthenticationLoginResponse> call, Throwable throwable){
                Toast.makeText(login_activity.this,throwable.getMessage(),Toast.LENGTH_LONG).show();
            }



        });

    }



    private void setData() {
        Email = EditText_Email.getText().toString().trim();
        Password = EditText_Password.getText().toString().trim();

        logindetails.setEmail(Email);
        logindetails.setPassword(Password);

    }

    private boolean checkinput() {
        String str[] = { EditText_Email.getText().toString().trim(), EditText_Password.getText().toString().trim()};

        for (String s : str)
            if (s.equals(""))
                return false;

        return true;
    }

    @Override
    public void onClick(View v) {

        if (v==Sign_in){

            if(checkinput()){

                if (!Patterns.EMAIL_ADDRESS.matcher(EditText_Email.getText().toString()).matches()){
                    Toast.makeText(login_activity.this,"Invalid email-id",Toast.LENGTH_LONG).show();
                }else {

                    apiCall();
                }
            }
            else{
                Toast.makeText(login_activity.this,"Required fields can't be empty.",Toast.LENGTH_LONG).show();
            }


        }
        else if (v==ForgetPassword){

            // Reset password activity
            Toast.makeText(login_activity.this,"Reset password(on progress)",Toast.LENGTH_LONG).show();


        }

    }
}
