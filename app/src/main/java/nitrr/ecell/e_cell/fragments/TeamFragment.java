package nitrr.ecell.e_cell.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.AboutUsResponse;
import nitrr.ecell.e_cell.model.TeamDetails;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.utils.TeamRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<TeamDetails> studentList = new ArrayList<>();
    private List<TeamDetails> facultyList = new ArrayList<>();
    private TeamRecyclerViewAdapter adapter;

    private TextView director, hocd, faculty, team, hocdName, dirName, fac1;
    private ImageView dirImage, hocdImage, fac1Image;

    public TeamFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
        callAPI();
    }

    private void initialize() {
        Typeface bebas = Typeface.createFromAsset(getActivity().getAssets(), "fonts/BebasNeue.ttf");

        recyclerView = getView().findViewById(R.id.teamRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new TeamRecyclerViewAdapter(getContext(), studentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);

        director = getView().findViewById(R.id.dir);
        hocd = getView().findViewById(R.id.hocd);
        faculty = getView().findViewById(R.id.faculty);
        team = getView().findViewById(R.id.team);

        fac1 = getView().findViewById(R.id.fac1Name);
        hocdName = getView().findViewById(R.id.hocdName);
        dirName = getView().findViewById(R.id.dirName);

        fac1Image = getView().findViewById(R.id.fac1Image);
        dirImage = getView().findViewById(R.id.dirImage);
        hocdImage = getView().findViewById(R.id.hocdImage);

        director.setTypeface(bebas);
        hocd.setTypeface(bebas);
        faculty.setTypeface(bebas);
        team.setTypeface(bebas);
        fac1.setTypeface(bebas);
        hocdName.setTypeface(bebas);
        dirName.setTypeface(bebas);


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
                        studentList.addAll(jsonResponse.getStudent());
                        adapter.notifyDataSetChanged();

                        facultyList.addAll(jsonResponse.getFaculty());
                        setDetails();
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

    private void setDetails() {
        for (TeamDetails detail : facultyList) {
            if (detail.getMemberType().equals("Dir")) {

                if (detail.getImage() != null)
                    Glide.with(getContext())
                            .load(detail.getImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(dirImage);

                dirName.setText(detail.getName());

            } else if (detail.getMemberType().equals("HCD")) {

                if (detail.getImage() != null)
                    Glide.with(getContext())
                            .load(detail.getImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(hocdImage);

                hocdName.setText(detail.getName());

            } else if (detail.getMemberType().equals("Fclty")) {

                if (detail.getImage() != null)
                    Glide.with(getContext())
                            .load(detail.getImage())
                            .apply(RequestOptions.circleCropTransform())
                            .into(fac1Image);

                fac1.setText(detail.getName());

            }
        }
    }
}
