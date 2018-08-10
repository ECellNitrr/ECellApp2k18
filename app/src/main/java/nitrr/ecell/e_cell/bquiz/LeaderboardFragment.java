package nitrr.ecell.e_cell.bquiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.bquiz.model.BQuizLeaderboardResponse;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardFragment extends DialogFragment {

    private LeaderBoardAdapter leaderBoardAdapter;
    private RecyclerView recyclerViewLeaderboard;
    private ProgressBar progressBarLeaderboard;
    private TextView userRankLeaderboard, userNameLeadeboard;
    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            apiCallForBquizLeaderboard();
        }
    };

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    public static LeaderboardFragment newInstance() {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
            d.getWindow().setWindowAnimations(R.style.DialogAnimation);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.app.DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        initialize(view);
        setUpLeaderboardRecyclerView();
        apiCallForBquizLeaderboard();
        progressBarLeaderboard.setVisibility(View.VISIBLE);
        return view;
    }

    private void initialize(View view) {
        recyclerViewLeaderboard = (RecyclerView) view.findViewById(R.id.recycler_view_leaderboard);
        userNameLeadeboard = (TextView) view.findViewById(R.id.user_name_leaderboard);
        userRankLeaderboard = (TextView) view.findViewById(R.id.user_rank_leaderboard);
        progressBarLeaderboard = (ProgressBar) view.findViewById(R.id.progress_bar_leaderboard);
    }

    private void setUpLeaderboardRecyclerView() {
        leaderBoardAdapter = new LeaderBoardAdapter(getContext());
        recyclerViewLeaderboard.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLeaderboard.setAdapter(leaderBoardAdapter);
    }

    private void apiCallForBquizLeaderboard() {
        ApiServices apiServices = AppClient.getInstance().createService(ApiServices.class);
        Call<BQuizLeaderboardResponse> call = apiServices.getBquizLeaderboard();
        call.enqueue(new Callback<BQuizLeaderboardResponse>() {
            @Override
            public void onResponse(Call<BQuizLeaderboardResponse> call, Response<BQuizLeaderboardResponse> response) {
                progressBarLeaderboard.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    BQuizLeaderboardResponse jsonResponse = response.body();
                    if (jsonResponse != null) {
                        leaderBoardAdapter.setLeaderboardUserDetailsList(jsonResponse.getLeaderboard());
                        //userNameLeadeboard.setText();
                        userRankLeaderboard.setText(String.valueOf(jsonResponse.getUserRank()));
                        leaderBoardAdapter.notifyDataSetChanged();
                    }
                } else if (getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BQuizLeaderboardResponse> call, Throwable t) {
                progressBarLeaderboard.setVisibility(View.GONE);
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
