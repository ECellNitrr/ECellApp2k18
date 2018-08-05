package nitrr.ecell.e_cell.bquiz;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nitrr.ecell.e_cell.R;
import nitrr.ecell.e_cell.bquiz.model.LeaderboardUserDetails;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<LeaderboardUserDetails> leaderboardUserDetailsList = new ArrayList<>();

    public LeaderBoardAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setLeaderboardUserDetailsList(List<LeaderboardUserDetails> leaderboardUserDetailsList) {
        this.leaderboardUserDetailsList = leaderboardUserDetailsList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.leaderboard_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final LeaderboardUserDetails data = leaderboardUserDetailsList.get(position);
        holder.nameLeaderboard.setText(data.getName());
        holder.rankLeaderBoard.setText(context.getResources().getString(R.string.rank_hash) + String.valueOf(data.getRank()));
    }

    @Override
    public int getItemCount() {
        return leaderboardUserDetailsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameLeaderboard, rankLeaderBoard;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameLeaderboard = (TextView) itemView.findViewById(R.id.name_leaderboard);
            rankLeaderBoard = (TextView) itemView.findViewById(R.id.rank_leaderboard);
        }
    }
}
