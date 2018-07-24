package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.AboutUsResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        callAPI();
    }

    private void init() {
        final Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");
        LinearLayout aboutUsLay = getView().findViewById(R.id.about_us_lay);

        TextView textView = getView().findViewById(R.id.about_custom_view_text);

        textView.setText(AppConstants.HOME_TITLES[4]);
        textView.setTypeface(bebasNeue);

        textView.setOnClickListener(this);
        aboutUsLay.setOnClickListener(this);
    }

    private void callAPI() {
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<AboutUsResponse> call = services.getAboutUsDetails();

        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                if (response.isSuccessful()) {
                    AboutUsResponse jsonResponse = response.body();

                    if (jsonResponse != null) {
                    }

                } else {
                    Toast.makeText(getActivity(), "Unable to fetch data.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        AboutUsBottomSheetFragment fragment = new AboutUsBottomSheetFragment();
        fragment.show(getActivity().getSupportFragmentManager(), "about_us");
    }
}