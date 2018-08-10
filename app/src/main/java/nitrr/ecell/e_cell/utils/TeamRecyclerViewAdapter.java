package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.aboutus.TeamDetails;

public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewHolder> {

    private List<TeamDetails> details;
    private Context context;

    public TeamRecyclerViewAdapter(Context context, List<TeamDetails> details) {
        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public TeamRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_recycler_custom_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TeamRecyclerViewHolder holder, final int position) {
        String designation = "";
        Typeface bebas = Typeface.createFromAsset(context.getAssets(), "fonts/BebasNeue.ttf");

        holder.teamName.setTypeface(bebas);
        holder.teamDesignation.setTypeface(bebas);
        holder.teamURL.setTypeface(bebas);

        if (details.get(position).getName() != null)
            holder.teamName.setText(details.get(position).getName());

        if (details.get(position).getMemberType() != null)
            switch (details.get(position).getMemberType()) {
                case "OC":
                    designation = "Overall Coordinator";
                    break;

                case "HC":
                    designation = "Head Coordinator";
                    break;

                case "EXEC":
                    designation = "Executive";
                    break;

                case "MNG":
                    designation = "Manager";
                    break;
            }


        holder.teamDesignation.setText(designation);

        if (details.get(position).getImage() != null)
            Glide.with(context)
                    .load(details.get(position).getImage())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.loadingIndicatorView.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.loadingIndicatorView.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.teamImage);

        if (details.get(position).getUrl() != null)
            holder.teamURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(details.get(position).getUrl()));
                    context.startActivity(browserIntent);
                }
            });
    }

    @Override
    public int getItemCount() {
        Log.e("Size ====", details.size() + "");
        return details.size();

    }
}
