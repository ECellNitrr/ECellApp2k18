package nitrr.ecell.e_cell.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.model.TeamDetails;

public class AboutUsRecyclerViewAdapter extends RecyclerView.Adapter<AboutUsRecyclerViewHolder> {

    private List<TeamDetails> details;
    private Context context;

    public AboutUsRecyclerViewAdapter(Context context, List<TeamDetails> details) {
        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public AboutUsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AboutUsRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.aboutus_team_top, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AboutUsRecyclerViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
