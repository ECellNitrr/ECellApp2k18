package nitrr.ecell.e_cell.model.sponsors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SponsorType implements Serializable{

    @SerializedName("section_name")
    @Expose
    private String sponsortypename;

    @SerializedName("sponsors")
    @Expose
    private List<SponsorDetail> sponserslist;

    public String getSponsortypename() {
        return sponsortypename;
    }

    public List<SponsorDetail> getSponserslist() {
        return sponserslist;
    }
}
