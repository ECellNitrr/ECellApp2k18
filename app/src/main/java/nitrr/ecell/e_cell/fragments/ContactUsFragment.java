package nitrr.ecell.e_cell.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.auth.GenericResponse;
import nitrr.ecell.e_cell.model.aboutus.MessageDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import nitrr.ecell.e_cell.utils.PrefUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private TextView det, add, email, ph, touch;
    private ImageView send, facebook, youtube, linkedin, twitter, instagram;
    private EditText nameEditText, emailEditText, messageEditText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_us_fragement, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        Typeface bebas = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        det = getView().findViewById(R.id.contact_us_top);
        add = getView().findViewById(R.id.add);
        email = getView().findViewById(R.id.email);
        ph = getView().findViewById(R.id.phone);
        touch = getView().findViewById(R.id.contact_us_mid);
        send = getView().findViewById(R.id.send);
        nameEditText = getView().findViewById(R.id.contactNameEdit);
        emailEditText = getView().findViewById(R.id.contactEmailEdit);
        messageEditText = getView().findViewById(R.id.contactMessageEdit);
        facebook = getView().findViewById(R.id.fb);
        twitter = getView().findViewById(R.id.twitter);
        youtube = getView().findViewById(R.id.youtube);
        linkedin = getView().findViewById(R.id.linkedin);
        instagram = getView().findViewById(R.id.instagram);

        det.setTypeface(bebas);
        add.setTypeface(bebas);
        email.setTypeface(bebas);
        ph.setTypeface(bebas);
        touch.setTypeface(bebas);

        send.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
        youtube.setOnClickListener(this);
        instagram.setOnClickListener(this);
        linkedin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == send) {
            if (!NetworkUtils.isNetworkAvailable(getContext())) {
                Snackbar.make(view, getResources().getString(R.string.no_internet_connection_msg), Snackbar.LENGTH_LONG).show();
            }
            if (checkNull()) {

                ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);

                PrefUtils utils = new PrefUtils(getActivity());
                MessageDetails details = new MessageDetails();

                details.setToken(utils.getAccessToken());
                details.setEmail(emailEditText.getText().toString().trim());
                details.setName(nameEditText.getText().toString().trim());
                details.setMessage(messageEditText.getText().toString().trim());

                Call<GenericResponse> call = services.sendMessage(details);
                call.enqueue(new Callback<GenericResponse>() {
                    @Override
                    public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                        if (response.isSuccessful())
                            Toast.makeText(getContext(), R.string.feedback_success, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {
                        Toast.makeText(getContext(), R.string.feedback_json_failure, Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(getContext(), R.string.feedback_failure, Toast.LENGTH_LONG).show();
            }
        } else if (view == facebook)
            openURL(AppConstants.FACEBOOK);

        else if (view == twitter)
            openURL(AppConstants.TWITTER);

        else if (view == youtube)
            openURL(AppConstants.YOUTUBE);

        else if (view == linkedin)
            openURL(AppConstants.LINKEDIN);

        else if (view == instagram)
            openURL(AppConstants.INSTAGRAM);
    }

    private void openURL(String URL) {
        Uri uri = Uri.parse(URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private boolean checkNull() {
        if (nameEditText.getText().toString().trim().equals(""))
            return false;

        if (messageEditText.getText().toString().trim().equals(""))
            return false;

        if (emailEditText.getText().toString().trim().equals(""))
            return false;

        return true;
    }
}
