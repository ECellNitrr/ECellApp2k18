package nitrr.ecell.e_cell.model.bquiz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import nitrr.ecell.e_cell.model.auth.GenericResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BQuizStatusResponse extends GenericResponse implements Serializable {

    @SerializedName("isActive")
    @Expose
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }
}
