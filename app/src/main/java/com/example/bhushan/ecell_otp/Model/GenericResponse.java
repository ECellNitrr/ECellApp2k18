package com.example.bhushan.ecell_otp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse implements Serializable{

    @Expose
    private Boolean success;

    @Expose
    private String message;


    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }



}