package com.example.bhushan.ecell_login.restapi;

import com.example.bhushan.ecell_login.Model.Logindetails;

import com.example.bhushan.ecell_login.Model.Logindetails;
import com.example.bhushan.ecell_login.utils.AppConstants;
import com.example.bhushan.ecell_login.Model.AuthenticationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

    @POST(AppConstants.SIGN_IN_URL)
    Call<AuthenticationResponse>sendLoginDetails(@Body Logindetails logindetails);

}
