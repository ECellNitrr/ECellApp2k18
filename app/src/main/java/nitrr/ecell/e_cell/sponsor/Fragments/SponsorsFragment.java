package nitrr.ecell.e_cell.sponsor.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import nitrr.ecell.e_cell.sponsor.model.SponsorType;
import nitrr.ecell.e_cell.sponsor.model.SponsorsResponce;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SponsorsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private SponsorAdapterFirst adapterr;
    private List<SponsorType> sponsdatafirst = new ArrayList<>();

    public SponsorsFragment() {
        // Required empty public constructor
    }

    public static SponsorsFragment newInstance(String param1, String param2) {
        SponsorsFragment fragment = new SponsorsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sponsors, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_sponsor1);
        adapterr = new SponsorAdapterFirst(sponsdatafirst, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterr);

        if(!(sponsdatafirst.size()>0)){
            APICall();
        }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void APICall() {

        Log.e("api callfunction ====","called");
        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<SponsorsResponce> call = services.getSponsorsResponce();
        call.enqueue(new Callback<SponsorsResponce>() {
            @Override
            public void onResponse(Call<SponsorsResponce> call, @NonNull Response<SponsorsResponce> response) {


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
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }


        });


    }

}
