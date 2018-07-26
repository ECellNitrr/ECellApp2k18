package nitrr.ecell.e_cell.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nitrr.ecell.e_cell.R;

public class EsRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView speakerImage;
    public TextView speakerName, speakerDesignation, speakerTime;

    public EsRecyclerViewHolder(View itemView) {
        super(itemView);

        speakerImage = itemView.findViewById(R.id.speakerImage);
        speakerName = itemView.findViewById(R.id.speakerName);
        speakerDesignation = itemView.findViewById(R.id.speakerDesignation);
        speakerTime = itemView.findViewById(R.id.speakerTime);
    }
}
