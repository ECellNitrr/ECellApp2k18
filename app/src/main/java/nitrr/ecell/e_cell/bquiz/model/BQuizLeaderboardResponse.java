package nitrr.ecell.e_cell.bquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import nitrr.ecell.e_cell.model.GenericResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BQuizLeaderboardResponse extends GenericResponse implements Serializable{

    @SerializedName("userRank")
    @Expose
    private int userRank;

    @SerializedName("leaderboard")
    @Expose
    private List<LeaderboardUserDetails> leaderboard;

    public int getUserRank() {
        return userRank;
    }

    public List<LeaderboardUserDetails> getLeaderboard() {
        return leaderboard;
    }
}
