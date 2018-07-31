package nitrr.ecell.e_cell.sponsor.Fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.sponsor.model.SponsorDetail;

public class SponsorAdapterSecond extends RecyclerView.Adapter<SponsorAdapterSecond.ViewHolder> {

    private List<SponsorDetail> sponsorDetailList;
    private Context context;

    public SponsorAdapterSecond(List<SponsorDetail> sponsorDetailList, Context context) {
        this.sponsorDetailList = sponsorDetailList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlayout_sponsor2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         final SponsorDetail itemSponsor = sponsorDetailList.get(position);
//         holder.sponsorwebsite.setText(itemSponsor.getWebsite_s());
         holder.sponsorname.setText(itemSponsor.getName_s());
//         holder.sponsordetail.setText(itemSponsor.getDetails_s());
//         holder.sponsorcontact.setText(itemSponsor.getContact_s());
        Glide.with(context)
                .load(itemSponsor.getPic_s()).into(holder.sponsorpic);
        holder.cardSponsorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SponsorsDetailsFragment sponsorsDetailsFragment = new SponsorsDetailsFragment();
                sponsorsDetailsFragment.setSponsorsData(itemSponsor,context);
                ((HomeActivity)context).addFragment(sponsorsDetailsFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("size2====", sponsorDetailList.size()+"");
        return sponsorDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView sponsorname,sponsordetail,sponsorwebsite,sponsorcontact;
        ImageView sponsorpic;
        CardView cardSponsorDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            sponsorcontact=(TextView)itemView.findViewById(R.id.sponsdetailcontact);
            sponsordetail=(TextView)itemView.findViewById(R.id.sponsdetaildetails);
            sponsorname=(TextView)itemView.findViewById(R.id.sponsdetailname);
            sponsorwebsite=(TextView)itemView.findViewById(R.id.sponsdetailwebsite);
            sponsorpic=(ImageView)itemView.findViewById(R.id.sponsdetailpic);
            cardSponsorDetail = (CardView) itemView.findViewById(R.id.sponsor_detail_card);

        }
    }


}
