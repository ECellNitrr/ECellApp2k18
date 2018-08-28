package nitrr.ecell.e_cell.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.adapters.sponsor.SponsorSectionAdapter;
import nitrr.ecell.e_cell.model.sponsors.SponsorsResponse;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SponsorsFragment extends DialogFragment {
    private RecyclerView sectionedRecyclerView;
    private TextView tvComingSoon;
    private SwipeRefreshLayout swipeRefreshLayoutSponsors;
    private ProgressBar progressBarSponsors;
    private Toolbar toolbar;
    private SponsorSectionAdapter sectionedSponsorAdapter;
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
        View view = inflater.inflate(R.layout.fragment_sponsors, container, false);
        toolbar = view.findViewById(R.id.sponsors_toolbar);
        toolbar.setTitle("Sponsors");
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_expand_more));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SponsorsFragment.this.dismiss();
            }
        });
        swipeRefreshLayoutSponsors = view.findViewById(R.id.swipeRefreshLayoutSponsors);
        sectionedRecyclerView = view.findViewById(R.id.recyclerview_sponsor1);
        progressBarSponsors = view.findViewById(R.id.progress_bar_sponsors);
        tvComingSoon = view.findViewById(R.id.tvComingSoon);
        tvComingSoon.setVisibility(View.GONE);

        sectionedSponsorAdapter = new SponsorSectionAdapter(getContext());
        sectionedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sectionedRecyclerView.setAdapter(sectionedSponsorAdapter);
        sectionedRecyclerView.setNestedScrollingEnabled(false);
        APICall();
        swipeRefreshLayoutSponsors.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall();
                swipeRefreshLayoutSponsors.setRefreshing(false);
            }
        });
        return view;
    }

    private void APICall() {
        progressBarSponsors.setVisibility(View.VISIBLE);
        ApiServices services = AppClient.getInstance().createService(ApiServices.class);

        Call<SponsorsResponse> call = services.getSponsorsResponce();
        call.enqueue(new Callback<SponsorsResponse>() {
            @Override
            public void onResponse(Call<SponsorsResponse> call, @NonNull Response<SponsorsResponse> response) {
                progressBarSponsors.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    SponsorsResponse sponsorsResponse = response.body();
                    if (null != sponsorsResponse && sponsorsResponse.getSuccess()) {
                        tvComingSoon.setVisibility(View.GONE);
                        sectionedSponsorAdapter.setSectionedSponsorTypeList(sponsorsResponse.getSponsors());
                        sectionedSponsorAdapter.notifyDataSetChanged();
                    }
                    else{
                        tvComingSoon.setVisibility(View.VISIBLE);
                        if (sponsorsResponse != null) {
                            tvComingSoon.setText(sponsorsResponse.getMessage());
                        }
                    }
                } else if (getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SponsorsResponse> call, Throwable t) {
                progressBarSponsors.setVisibility(View.GONE);
                if (getContext() != null) {
                    if (!NetworkUtils.isNetworkAvailable(getContext())) {
                        DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, getContext(), clickListenerPositive, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.something_went_wrong_msg), Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }
}
