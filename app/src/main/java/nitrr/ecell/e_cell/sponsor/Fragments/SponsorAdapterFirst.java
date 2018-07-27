package nitrr.ecell.e_cell.sponsor.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bhushan.ecell_login.R;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.sponsor.model.SponsorDetail;
import nitrr.ecell.e_cell.sponsor.model.SponsorType;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.security.AccessController.getContext;

public class SponsorAdapterFirst extends RecyclerView.Adapter<SponsorAdapterFirst.ViewHolder> {

    private List<SponsorType> listspons1;
    private Context context;
    private RecyclerView recyclerView;
    SponsorType sponsorType;
    private RecyclerView.Adapter<SponsorAdapterSecond.ViewHolder> adapterr;
    private ArrayList<SponsorDetail> sponsordatasecond;

    public SponsorAdapterFirst(List<SponsorType> listspons1, Context context) {
        this.listspons1 = listspons1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlayout_sponsors,parent,false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_sponsor2);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         SponsorType listitem1=listspons1.get(position);
         holder.SponsHeading.setText(listitem1.getSponsortypename());
         // 2nd recyclerviewcall
        sponsordatasecond=new ArrayList<>(sponsorType.getSponserslist());
        adapterr = new SponsorAdapterSecond(sponsordatasecond,context);
        recyclerView.setAdapter(adapterr);

    }

    @Override
    public int getItemCount() {
        return listspons1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView SponsHeading;
        RecyclerView RecyclerViewSpons1;

        public ViewHolder(View itemView) {
            super(itemView);

            SponsHeading=(TextView)itemView.findViewById(R.id.sponstypename);
            RecyclerViewSpons1=(RecyclerView)itemView.findViewById(R.id.recyclerview_sponsor2);

        }
    }

}
