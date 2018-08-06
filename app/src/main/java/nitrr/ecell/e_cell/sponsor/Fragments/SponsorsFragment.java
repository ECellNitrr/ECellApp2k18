package nitrr.ecell.e_cell.sponsor.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.sponsor.model.SponsorType;
import nitrr.ecell.e_cell.sponsor.model.SponsorsResponce;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SponsorsFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBarSponsors;
    private SponsorAdapterFirst adapterr;
    private List<SponsorType> sponsdatafirst = new ArrayList<>();
    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            APICall();
        }
    };

    public SponsorsFragment() {
        // Required empty public constructor
    }

    public static SponsorsFragment newInstance() {
        SponsorsFragment fragment = new SponsorsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null && d.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sponsors, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_sponsor1);
        progressBarSponsors = (ProgressBar) view.findViewById(R.id.progress_bar_sponsors);
        adapterr = new SponsorAdapterFirst(sponsdatafirst, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterr);

        if (!(sponsdatafirst.size() > 0)) {
            APICall();
        }
        return view;
    }

    private void APICall() {
        Log.e("api callfunction ====", "called");
        progressBarSponsors.setVisibility(View.VISIBLE);
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<SponsorsResponce> call = services.getSponsorsResponce();
        call.enqueue(new Callback<SponsorsResponce>() {
            @Override
            public void onResponse(Call<SponsorsResponce> call, @NonNull Response<SponsorsResponce> response) {
                progressBarSponsors.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    SponsorsResponce sponsorsResponce = response.body();
                    if (null != sponsorsResponce) {
                        Log.e("response====", "Succesfull");
                        sponsdatafirst.addAll(sponsorsResponce.getSponsors());
                        adapterr.notifyDataSetChanged();
                        //Toast.makeText(getContext(), "api call for sponsors", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SponsorsResponce> call, Throwable t) {
                progressBarSponsors.setVisibility(View.GONE);
                if (!NetworkUtils.isNetworkAvailable(getContext())) {
                    DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, getContext(), clickListenerPositive, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
