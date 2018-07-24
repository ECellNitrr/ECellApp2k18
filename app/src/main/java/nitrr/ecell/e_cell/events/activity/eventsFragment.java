package nitrr.ecell.e_cell.events.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.bhushan.ecell_login.R;

import java.util.ArrayList;

import nitrr.ecell.e_cell.events.Model.EventsResponse;
import nitrr.ecell.e_cell.events.Model.listitem;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eventsFragment extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter<eventsFragmentAdapter.ViewHolder> adapterr;
    private ArrayList<listitem> data_events;
    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        activity = this;
        initviews();

    }
    private void initviews(){

        recyclerView=(RecyclerView) findViewById(R.id.recyclerviewid);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        APICall();

    }

    private void APICall() {

        ApiServices services = AppClient.getInstance().createServiceWithAuth(ApiServices.class);
        Call<EventsResponse> call = services.getEventsResponse();
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {



                if (response.isSuccessful()) {
                    EventsResponse eventsResponse=response.body();
                    if (null != eventsResponse) {
                        data_events = new ArrayList<>(eventsResponse.getEvents());
                        adapterr=new eventsFragmentAdapter(data_events, activity);
                        recyclerView.setAdapter(adapterr);

                    }
                } else {
                    Toast.makeText(eventsFragment.this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {
                Toast.makeText(eventsFragment.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });



       }


}
