package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.GenericResponse;
import nitrr.ecell.e_cell.model.MessageDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends Fragment implements View.OnClickListener {
    TextView det, add, email, ph, touch;
    ImageView send;

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

        det.setTypeface(bebas);
        add.setTypeface(bebas);
        email.setTypeface(bebas);
        ph.setTypeface(bebas);
        touch.setTypeface(bebas);

        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);

        // TODO: send proper object
        Call<GenericResponse> call = services.sendMessage(new MessageDetails());
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful())
                    Toast.makeText(getContext(), "Thank you for giving us feedback!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
            }
        });
    }
}
