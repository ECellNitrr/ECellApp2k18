package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

import nitrr.ecell.e_cell.utils.PrefUtils;

public class FacebookSignUp {

    private Activity activity;
    private View fbSignUp;
    private CallbackManager callbackManager;
    private PrefUtils prefUtils;

    public FacebookSignUp(Activity activity, View fbSignUp) {
        this.activity = activity;
        this.fbSignUp = fbSignUp;

        prefUtils = new PrefUtils(activity);
    }

    public void initialize() {
        callbackManager = CallbackManager.Factory.create();

        fbSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Bundle fbData = getFbUserData(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email");

                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "Facebook SignUp Cancelled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
            }

        });

    }

    private Bundle getFbUserData(JSONObject jsonObject) {
        Bundle bundle = new Bundle();

        try {

            String id = jsonObject.getString("id");

            URL avatar_url;
            avatar_url = new URL("https://graph.facebook.com/" + id + "/picture?type=large");

            bundle.putString("id", id);

            if (jsonObject.has("first_name"))
                bundle.putString("first_name", jsonObject.getString("first_name"));

            if (jsonObject.has("last_name"))
                bundle.putString("last_name", jsonObject.getString("last_name"));

            if (jsonObject.has("email"))
                bundle.putString("email", jsonObject.getString("email"));

            bundle.putString("avatar_url", avatar_url.toString());

            prefUtils.saveFbUserInfo(jsonObject.getString("first_name"), jsonObject.getString("last_name"), jsonObject.getString("email"), avatar_url.toString());

        } catch (Exception e) {
            Log.d("Facebook Sign Up Error.", e.getMessage());
        }

        return bundle;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
