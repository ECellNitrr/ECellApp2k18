package nitrr.ecell.e_cell.fragments;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import nitrr.ecell.e_cell.model.aboutus.AboutUsResponse;
import nitrr.ecell.e_cell.model.aboutus.TeamDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.CustomScrollableView;
import nitrr.ecell.e_cell.utils.DialogFactory;
import nitrr.ecell.e_cell.utils.NetworkUtils;
import nitrr.ecell.e_cell.adapters.aboutus.TeamRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class TeamFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<TeamDetails> studentList = new ArrayList<>();
    private List<TeamDetails> facultyList = new ArrayList<>();
    private TeamRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private CustomScrollableView scrollableView;
    private SwipeRefreshLayout swipeRefreshLayoutTeam;
    private TextView director, hocd, faculty, team, hocdName, dirName, fac1;
    private ImageView dirImage, hocdImage, fac1Image;
    private DialogInterface.OnClickListener clickListenerPositive = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            callAPI();
        }
    };

    public TeamFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.team_fragment, container, false);
        initialize(view);
        callAPI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initialize(View view) {
        Typeface bebas = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        swipeRefreshLayoutTeam = view.findViewById(R.id.swipeRefreshLayoutTeam);

        scrollableView = view.findViewById(R.id.scrollView);
        scrollableView.setScrolling(false);

        recyclerView = view.findViewById(R.id.teamRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new TeamRecyclerViewAdapter(getContext(), studentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        director = view.findViewById(R.id.dir);
        hocd = view.findViewById(R.id.hocd);
        faculty = view.findViewById(R.id.faculty);
        team = view.findViewById(R.id.team);

        fac1 = view.findViewById(R.id.fac1Name);
        hocdName = view.findViewById(R.id.hocdName);
        dirName = view.findViewById(R.id.dirName);

        fac1Image = view.findViewById(R.id.fac1Image);
        dirImage = view.findViewById(R.id.dirImage);
        hocdImage = view.findViewById(R.id.hocdImage);

        progressBar = view.findViewById(R.id.team_progress);

        director.setTypeface(bebas);
        hocd.setTypeface(bebas);
        faculty.setTypeface(bebas);
        team.setTypeface(bebas);
        fac1.setTypeface(bebas);
        hocdName.setTypeface(bebas);
        dirName.setTypeface(bebas);

        swipeRefreshLayoutTeam.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAPI();
                swipeRefreshLayoutTeam.setRefreshing(false);
            }
        });
    }

    private void callAPI() {
        studentList.clear();
        facultyList.clear();
        progressBar.setVisibility(View.VISIBLE);
        ApiServices services = AppClient.getInstance().createService(ApiServices.class);
        Call<AboutUsResponse> call = services.getAboutUsDetails();
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                progressBar.setVisibility(GONE);
                if (response.isSuccessful()) {
                    scrollableView.setScrolling(true);
                    AboutUsResponse jsonResponse = response.body();
                    if (jsonResponse != null && jsonResponse.getSuccess()) {
                        studentList.addAll(jsonResponse.getStudent());
                        adapter.notifyDataSetChanged();
                        facultyList.addAll(jsonResponse.getFaculty());
                        setDetails();
                    }
                } else if (getContext() != null) {
                    Toast.makeText(getContext(), getString(R.string.something_went_wrong_msg), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                progressBar.setVisibility(GONE);
                if (getContext() != null) {
                    if (!NetworkUtils.isNetworkAvailable(getContext())) {
                        DialogFactory.showDialog(DialogFactory.CONNECTION_PROBLEM_DIALOG, getContext(), clickListenerPositive, null, false, getString(R.string.network_issue_title), getString(R.string.network_issue_details), getString(R.string.bquiz_dialog_retry_btn));
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.something_went_wrong_msg) + "  " + t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
    }

    private void setDetails() {
        for (TeamDetails detail : facultyList) {
            if (detail.getMemberType().equals("Dir")) {

                if (detail.getImage() != null && getActivity() != null)
                    Glide.with(getActivity())
                            .load(detail.getImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(dirImage);

                dirName.setText(detail.getName());

            } else if (detail.getMemberType().equals("HCD")) {
                if (detail.getImage() != null && getActivity() != null)
                    Glide.with(getActivity())
                            .load(detail.getImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(hocdImage);

                hocdName.setText(detail.getName());

            } else if (detail.getMemberType().equals("Fclty")) {

                if (detail.getImage() != null && getActivity() != null)
                    Glide.with(getActivity())
                            .load(detail.getImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(fac1Image);

                fac1.setText(detail.getName());

            }
        }
    }
}
