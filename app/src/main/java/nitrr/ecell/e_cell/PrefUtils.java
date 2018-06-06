package nitrr.ecell.e_cell;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

public class PrefUtils {
    private Activity activity;

    PrefUtils(Activity activity) {
        this.activity = activity;
    }

    public void saveFbAccessToken(String token) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fb_access_token", token);
        editor.apply();
    }

    public String getFbAccessToken() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        return prefs.getString("fb_access_token", null);
    }

    public void clearToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.clear();
        editor.apply();
    }

    public void saveFbUserInfo(String first_name, String last_name, String email, String avatar_url) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("first_name", first_name);
        editor.putString("last_name", last_name);
        editor.putString("email", email);
        editor.putString("avatar_url", avatar_url);
        editor.apply();


        // Delete this dialog after final build.
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Retrieved Data");
        dialog.setMessage("Access Token: " + getFbAccessToken() + "\nName : " + first_name + " " + last_name + "\nEmail : " + email + "\nAvatar URL : " + avatar_url);
        dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public Map<String, String> getFbUserInfo() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        Map<String, String> userInfo = new HashMap<>();

        userInfo.put("uid", prefs.getString("uid", null));
        userInfo.put("first_name", prefs.getString("first_name", null));
        userInfo.put("last_name", prefs.getString("last_name", null));
        userInfo.put("email", prefs.getString("email", null));
        userInfo.put("avatar_url", prefs.getString("avatar_url", null));

        return userInfo;
    }
}
