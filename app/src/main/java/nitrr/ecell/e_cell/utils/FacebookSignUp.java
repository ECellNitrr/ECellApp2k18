package nitrr.ecell.e_cell.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.auth.AuthenticationResponse;
import nitrr.ecell.e_cell.model.auth.FacebookSignInUserDetails;
import nitrr.ecell.e_cell.activities.otp_activity;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacebookSignUp {

    private Activity activity;
    private View fbSignUp;
    private CallbackManager callbackManager;
    private PrefUtils prefUtils;
    private FacebookSignInUserDetails details = new FacebookSignInUserDetails();

    public FacebookSignUp(Activity activity, View fbSignUp) {
        this.activity = activity;
        this.fbSignUp = fbSignUp;

        FacebookSdk.setApplicationId(activity.getResources().getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(activity.getApplicationContext());

        prefUtils = new PrefUtils(activity);
    }

    public void initialize() {
        LoginButton button = new LoginButton(activity);
        button.setVisibility(View.INVISIBLE);
        ((LinearLayout)fbSignUp).addView(button);

        callbackManager = CallbackManager.Factory.create();

        fbSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList(AppConstants.PUBLIC_PROFILE, AppConstants.EMAIL));
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                prefUtils.isFacebookLogin(true);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Bundle fbData = getFbUserData(object);
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString(AppConstants.FIELDS, AppConstants.INPUT_FIELDS);

                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, activity.getResources().getString(R.string.error_fb), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
            }

        });

    }

    private Bundle getFbUserData(JSONObject jsonObject) {
        Bundle bundle = new Bundle();

        try {

            String id = jsonObject.getString(AppConstants.ID);

            URL avatar_url;
            avatar_url = new URL("https://graph.facebook.com/" + id + "/picture?type=large");

            bundle.putString(AppConstants.ID, id);

            if (jsonObject.has(AppConstants.FIRST_NAME))
                bundle.putString(AppConstants.FIRST_NAME, jsonObject.getString(AppConstants.FIRST_NAME));

            if (jsonObject.has(AppConstants.LAST_NAME))
                bundle.putString(AppConstants.LAST_NAME, jsonObject.getString(AppConstants.LAST_NAME));

            if (jsonObject.has(AppConstants.EMAIL))
                bundle.putString(AppConstants.EMAIL, jsonObject.getString(AppConstants.EMAIL));

            bundle.putString(AppConstants.AVATAR_URL, avatar_url.toString());

            prefUtils.saveFbUserInfo(jsonObject.getString(AppConstants.FIRST_NAME), jsonObject.getString(AppConstants.LAST_NAME), jsonObject.getString(AppConstants.EMAIL));

            details.setFacebook(prefUtils.getIfIsFacebookLogin());
            details.setName(jsonObject.getString(AppConstants.FIRST_NAME) + " " + jsonObject.getString(AppConstants.LAST_NAME));
            details.setEmail(jsonObject.getString(AppConstants.EMAIL));

            apiCall();

        } catch (Exception e) {
            Log.d("Facebook Sign Up Error.", e.getMessage());
        }

        return bundle;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    private void apiCall() {
        ApiServices services = AppClient.getInstance().createService(ApiServices.class);
        Call<AuthenticationResponse> call = services.sendFacebookRegistrationDetails(details);

        call.enqueue(new Callback<AuthenticationResponse>() {
            @Override
            public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        prefUtils.saveAccessToken(response.body().getToken());

                        Toast.makeText(activity, "Success.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(activity, otp_activity.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }
                } else
                    Toast.makeText(activity, "Not Successful.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
