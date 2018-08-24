package nitrr.ecell.e_cell.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import nitrr.ecell.e_cell.model.auth.GenericResponse;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ESummitRegistrationResponse  extends GenericResponse implements Serializable{

    @SerializedName("link")
    @Expose
    private String link;

    public String getLink() {
        return link;
    }
}
