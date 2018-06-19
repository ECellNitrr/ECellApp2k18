package com.example.bhushan.ecell_login.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefs {

    private Activity activity;
    private void SharedPreferences(Activity activity) {
        this.activity = activity;
    }

 //   private SharedPreferences sharedPreferences;
   // private SharedPreferences.Editor editor;



    public void saveAccessToken(String token){

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("access_token",token);
        editor.apply();

    }

    public String getAccessToken(){

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getString("acess_token",null);

    }

}
