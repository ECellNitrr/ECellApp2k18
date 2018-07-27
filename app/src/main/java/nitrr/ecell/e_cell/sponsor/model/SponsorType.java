package nitrr.ecell.e_cell.sponsor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SponsorType implements Serializable{

    @SerializedName("section_name")
    @Expose
    private String sponsortypename;

    @SerializedName("sponsors")
    @Expose
    private ArrayList<SponsorDetail> sponserslist;

    public String getSponsortypename() {
        return sponsortypename;
    }

    public ArrayList<SponsorDetail> getSponserslist() {
        return sponserslist;
    }
}
