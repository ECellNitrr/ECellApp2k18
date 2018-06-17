package com.example.bhushan.ecell_otp.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpDetails implements Serializable{

    @Expose
    private String mobile_no;

    @Expose
    private String otp;


    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) { this.otp = otp; }


}
