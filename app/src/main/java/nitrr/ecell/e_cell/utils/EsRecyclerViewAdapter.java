package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.SpeakerList;

public class EsRecyclerViewAdapter extends RecyclerView.Adapter<EsRecyclerViewHolder> {

    private List<SpeakerList> details;
    private Context context;

    public EsRecyclerViewAdapter(Context context, List<SpeakerList> details) {
        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public EsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EsRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.es_recycler_custom_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EsRecyclerViewHolder holder, int position) {
        Typeface bebas = Typeface.createFromAsset(context.getAssets(), "fonts/BebasNeue.ttf");

        if (details.get(position).getName() != null)
            holder.speakerName.setText(details.get(position).getName());

        if (details.get(position).getCompany() != null)
            holder.speakerDesignation.setText(details.get(position).getCompany());

        if (details.get(position).getYear() != null)
            holder.speakerTime.setText(details.get(position).getYear());

        if(details.get(position).getProfile_pic() != null)
            Glide.with(context)
                    .load(details.get(position).getProfile_pic())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.speakerImage);

        holder.speakerName.setTypeface(bebas);
        holder.speakerDesignation.setTypeface(bebas);
        holder.speakerTime.setTypeface(bebas);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
