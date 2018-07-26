package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.TeamDetails;

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
    public void onBindViewHolder(@NonNull TeamRecyclerViewHolder holder, final int position) {
        String designation = "";

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
        return details.size();
    }
}
