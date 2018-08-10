package nitrr.ecell.e_cell.sponsor.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.activities.HomeActivity;
import nitrr.ecell.e_cell.sponsor.model.SponsorDetail;

public class SponsorAdapterSecond extends RecyclerView.Adapter<SponsorAdapterSecond.ViewHolder> {

    private List<SponsorDetail> sponsorDetailList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    public SponsorAdapterSecond(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setSponsorDetailList(List<SponsorDetail> sponsorDetailList) {
        this.sponsorDetailList = sponsorDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.itemlayout_sponsor2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SponsorDetail itemSponsor = sponsorDetailList.get(position);
        holder.sponsorname.setText(itemSponsor.getName_s());
        Glide.with(context)
                .load(itemSponsor.getPic_s())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.loadingIndicatorView.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.loadingIndicatorView.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(holder.sponsorpic);
        holder.cardSponsorDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity) (context);
                FragmentManager fm = activity.getSupportFragmentManager();
                SponsorsDetailsFragment sponsorsDetailsFragment = SponsorsDetailsFragment.newInstance();
                sponsorsDetailsFragment.setSponsorsData(itemSponsor, context);
                sponsorsDetailsFragment.show(fm, "sponsor details fragment");
                holder.cardSponsorDetail.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (holder.cardSponsorDetail != null) {
                            holder.cardSponsorDetail.setClickable(true);
                        }
                    }
                }, 1000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sponsorDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sponsorname;
        ImageView sponsorpic;
        CardView cardSponsorDetail;
        AVLoadingIndicatorView loadingIndicatorView;

        public ViewHolder(View itemView) {
            super(itemView);
            sponsorname = (TextView) itemView.findViewById(R.id.sponsdetailname);
            sponsorpic = (ImageView) itemView.findViewById(R.id.sponsdetailpic);
            cardSponsorDetail = (CardView) itemView.findViewById(R.id.sponsor_detail_card);
            loadingIndicatorView = (AVLoadingIndicatorView) itemView.findViewById(R.id.indicator_progress_bar_spons_image);
        }
    }


}
