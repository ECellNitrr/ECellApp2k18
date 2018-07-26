package nitrr.ecell.e_cell.signin.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static nitrr.ecell.e_cell.utils.AppConstants.ACCESS_TOKEN;

public class SharedPrefs {

    private Activity activity;
    private void SharedPreferences(Activity activity) {
        this.activity = activity;
    }




    public void saveAccessToken(String token){

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(ACCESS_TOKEN,token);
        editor.apply();

    }

    public String getAccessToken(){

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getString(ACCESS_TOKEN,null);

    }

}
