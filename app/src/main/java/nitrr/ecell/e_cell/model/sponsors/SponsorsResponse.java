package nitrr.ecell.e_cell.model.sponsors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import nitrr.ecell.e_cell.model.auth.GenericResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SponsorsResponse extends GenericResponse implements Serializable{


@SerializedName("spons")
@Expose
private List<SponsorType> sponsors;

    public List<SponsorType> getSponsors() {
        return sponsors;
    }
}
