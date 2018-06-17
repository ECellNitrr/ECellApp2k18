package com.example.bhushan.ecell_otp.restapi;

import com.example.bhushan.ecell_login.Model.Logindetails;
import com.example.bhushan.ecell_otp.Model.OtpDetails;

import nitrr.ecell.e_cell.model.AuthenticationResponse;
import nitrr.ecell.e_cell.model.UserDetails;
import nitrr.ecell.e_cell.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiServices {

    @POST(AppConstants.SIGN_IN_OTP)
    Call<AuthenticationResponse> sendRegisterDetails(@Body OtpDetails otpDetails);

}
