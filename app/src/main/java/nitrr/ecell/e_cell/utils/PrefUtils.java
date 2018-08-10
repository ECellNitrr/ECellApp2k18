package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class PrefUtils {
    private Activity activity;

    public PrefUtils(Activity activity) {
        this.activity = activity;
    }

    public void saveUserName(String name) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("user", name);
        editor.apply();
    }

    public void isFacebookLogin(boolean bool) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("isFB", Boolean.toString(bool));
        editor.apply();
    }


    public boolean getIfIsFacebookLogin() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        String str = prefs.getString("isFB", null);

        if (str.equals("true"))
            return true;
        else return false;
    }

    public String getUserName() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getString("user", null);
    }

    public void saveAccessToken(String token) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("access_token", token);
        editor.apply();
    }

    public String getAccessToken() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getString("access_token", null);
    }

    public void saveFbUserInfo(String first_name, String last_name, String email) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("first_name", first_name);
        editor.putString("last_name", last_name);
        editor.putString("email", email);
        editor.apply();

        saveUserName(first_name);
    }

    public void clearPrefs(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.apply();
    }
}
