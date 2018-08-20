package nitrr.ecell.e_cell.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import nitrr.ecell.e_cell.R;

public class EsRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView speakerImage;
    public TextView speakerName, speakerDesignation, speakerTime;
    AVLoadingIndicatorView loadingIndicatorView;

    public EsRecyclerViewHolder(View itemView) {
        super(itemView);
        speakerImage = itemView.findViewById(R.id.speakerImage);
        speakerName = itemView.findViewById(R.id.speakerName);
        speakerDesignation = itemView.findViewById(R.id.speakerDesignation);
        speakerTime = itemView.findViewById(R.id.speakerTime);
        loadingIndicatorView =  itemView.findViewById(R.id.indicator_progress_bar_events);
    }
}
