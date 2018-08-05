package nitrr.ecell.e_cell.bquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderboardUserDetails implements Serializable{
    @SerializedName("rank")
    @Expose
    private int rank;

    @SerializedName("name")
    @Expose
    private String name;

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }
}


