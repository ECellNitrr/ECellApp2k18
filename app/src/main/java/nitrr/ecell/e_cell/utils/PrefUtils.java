package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
        editor.putBoolean("isFB", bool);
        editor.apply();
    }


    public boolean getIfIsFacebookLogin() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);

        return prefs.getBoolean("isFB", false);
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

    public void saveFcmToken(String fcm) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fcm_token", fcm);
        editor.apply();
    }

    public String getFcmToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getString("fcm_token", null);
    }


    public void clearPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.apply();
    }

    public void setQuestionSetId(int id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("QuestionSetId", id);
        editor.apply();
    }

    public int getQuestionSetId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getInt("QuestionSetId", -1);
    }

    public boolean getIsLoggedIn() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getBoolean("isLoggedIn", false);
    }

    public void setIsLoggedIn(boolean loggedIn) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("isLoggedIn", loggedIn);
        editor.apply();
    }

    public boolean getIsFirstTimeLaunch() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getBoolean("isLoggedIn", true);
    }

    public void setIsFirstTimeLaunch(boolean loggedIn) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean("isLoggedIn", loggedIn);
        editor.apply();
    }
}
