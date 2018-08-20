package nitrr.ecell.e_cell.model.esummit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeakerResponse implements Serializable {

    @SerializedName("speakers")
    @Expose
    private List<SpeakerList> list;

    public List<SpeakerList> getList() {
        return list;
    }
}
