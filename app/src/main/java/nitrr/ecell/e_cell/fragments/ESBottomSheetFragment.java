package nitrr.ecell.e_cell.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.SpeakerList;
import nitrr.ecell.e_cell.model.SpeakerResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.EsRecyclerViewAdapter;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class ESBottomSheetFragment extends DialogFragment {
    private ImageView esImage;
    private TextView es, esDesc, sponLabel;
    private SwipeRefreshLayout swipeRefreshLayoutESummit;
    private EsRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private List<SpeakerList> details = new ArrayList<>();
    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            callAPI();
        }
    };


    public ESBottomSheetFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        initialize();
        callAPI();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.es_bottom_sheet, container, false);
    }

    private void initialize() {
        Typeface bebasNeue = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        swipeRefreshLayoutESummit = getView().findViewById(R.id.swipeRefreshLayoutESummit);
        RecyclerView recyclerView = getView().findViewById(R.id.esRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new EsRecyclerViewAdapter(getContext(), details);

        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        esImage = getView().findViewById(R.id.es_image);
        es = getView().findViewById(R.id.es_text);
        esDesc = getView().findViewById(R.id.es_content);
        sponLabel = getView().findViewById(R.id.es_label);
        progressBar = getView().findViewById(R.id.progressSpeaker);

        es.setTypeface(bebasNeue);
        sponLabel.setTypeface(bebasNeue);

        Glide.with(getActivity())
                .load(R.drawable.esummit)
                .apply(RequestOptions.circleCropTransform())
                .into(esImage);

        swipeRefreshLayoutESummit.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
                swipeRefreshLayoutESummit.setRefreshing(false);
            }
        });

    }

    private void callAPI() {
        progressBar.setVisibility(View.VISIBLE);
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<SpeakerResponse> response = services.getSpeakerDetails();
        response.enqueue(new Callback<SpeakerResponse>() {
            @Override
            public void onResponse(Call<SpeakerResponse> call, Response<SpeakerResponse> response) {
                progressBar.setVisibility(GONE);
                if (response.isSuccessful()) {
                    SpeakerResponse speakerResponse = response.body();

                    if (speakerResponse != null) {
                        details.addAll(speakerResponse.getList());
                        adapter.notifyDataSetChanged();
                    }
                } else if (getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SpeakerResponse> call, Throwable t) {
                progressBar.setVisibility(GONE);
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
