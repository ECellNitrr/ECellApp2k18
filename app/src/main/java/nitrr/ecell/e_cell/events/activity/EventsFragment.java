package nitrr.ecell.e_cell.events.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.events.Model.EventsData;
import nitrr.ecell.e_cell.events.Model.EventsResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EventsFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBarEvents;
    private EventsFragmentAdapter adapterr;
    private SwipeRefreshLayout swipeRefreshLayoutEvents;
    private ArrayList<EventsData> data_events = new ArrayList<>();
    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            APICall();
        }
    };

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        swipeRefreshLayoutEvents = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutEvents);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_id);
        progressBarEvents = (ProgressBar) view.findViewById(R.id.progress_bar_events);
        adapterr = new EventsFragmentAdapter(data_events, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterr);
        if (!(data_events.size() > 0)) {
            APICall();
        }
        swipeRefreshLayoutEvents.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                APICall();
                swipeRefreshLayoutEvents.setRefreshing(false);
            }
        });
        return view;
    }

    private void APICall() {
        progressBarEvents.setVisibility(View.VISIBLE);
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<EventsResponse> call = services.getEventsResponse();
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {
                progressBarEvents.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    EventsResponse eventsResponse = response.body();
                    if (null != eventsResponse) {
                        data_events.addAll(eventsResponse.getEvents());
                        adapterr.notifyDataSetChanged();
                    }
                } else if (getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                progressBarEvents.setVisibility(View.GONE);
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
