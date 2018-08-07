package nitrr.ecell.e_cell.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import nitrr.ecell.e_cell.R;

public class TeamRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView teamImage;
    public TextView teamName, teamDesignation, teamURL;
    AVLoadingIndicatorView loadingIndicatorView;

    public TeamRecyclerViewHolder(View itemView) {
        super(itemView);

        teamImage = itemView.findViewById(R.id.teamImage);
        teamName = itemView.findViewById(R.id.teamName);
        teamDesignation = itemView.findViewById(R.id.teamDesignation);
        teamURL = itemView.findViewById(R.id.teamURL);
        loadingIndicatorView = itemView.findViewById(R.id.indicator_progress_bar_team);
    }
}
