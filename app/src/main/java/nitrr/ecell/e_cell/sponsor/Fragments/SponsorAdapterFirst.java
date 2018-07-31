package nitrr.ecell.e_cell.sponsor.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.sponsor.model.SponsorDetail;
import nitrr.ecell.e_cell.sponsor.model.SponsorType;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SponsorAdapterFirst extends RecyclerView.Adapter<SponsorAdapterFirst.ViewHolder> {

    private List<SponsorType> listspons1;
    private Context context;
    private RecyclerView recyclerView;
    SponsorType sponsorType;
    private SponsorAdapterSecond adapterr;
    private List<SponsorDetail> sponsordatasecond=new ArrayList<>();

    public SponsorAdapterFirst(List<SponsorType> listspons1, Context context) {
      //  Log.e("size====", listspons1.size()+"");
        this.listspons1 = listspons1;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlayout_sponsors,parent,false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_sponsor2);
        adapterr = new SponsorAdapterSecond(sponsordatasecond,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapterr);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         SponsorType listitem1=listspons1.get(position);
         holder.SponsHeading.setText(listitem1.getSponsortypename());
         // 2nd recyclerviewcall
        if(null != listitem1.getSponserslist()){
            if(0 != listitem1.getSponserslist().size()){
                sponsordatasecond.addAll(listitem1.getSponserslist());
                adapterr.notifyDataSetChanged();
            }
        }

    }

    @Override
    public int getItemCount() {
        Log.e("size====", listspons1.size()+"");
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
