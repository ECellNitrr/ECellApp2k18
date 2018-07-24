package nitrr.ecell.e_cell.events.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.example.bhushan.ecell_login.R;

import java.util.ArrayList;

import nitrr.ecell.e_cell.events.Model.EventsResponse;
import nitrr.ecell.e_cell.events.Model.listitem;
import nitrr.ecell.e_cell.restapi.ApiServices;
import nitrr.ecell.e_cell.restapi.AppClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class events extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter<adapter.ViewHolder> adapterr;
    private ArrayList<listitem> data_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
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
            public void onResponse(Call<EventsResponse> call, Response<EventsResponse> response) {
                EventsResponse eventsResponse=response.body();
                assert eventsResponse != null;
                data_events = new ArrayList<>(eventsResponse.getEvents());
                adapterr=new adapter(data_events);
                recyclerView.setAdapter(adapterr);

            }

            @Override
            public void onFailure(Call<EventsResponse> call, Throwable t) {

            }
        });



     /*   Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestEventsdata requestEventsdata=retrofit.create(RequestEventsdata.class);
        Call<EventsResponse> call=requestEventsdata.getJSON();
        call.enqueue(new Callback<EventsResponse>() {
            @Override
            public void onResponse(@NonNull Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {

                EventsResponse eventsResponse=response.body();
                assert eventsResponse != null;
                data_events =new ArrayList<>(Arrays.asList(eventsResponse.geteventsData()));
                adapterr=new adapter(data_events);
                recyclerView.setAdapter(adapterr);

            }

            @Override
            public void onFailure(@NonNull Call<EventsResponse> call, @NonNull Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });


       */
    }


}
