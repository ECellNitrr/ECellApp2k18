package nitrr.ecell.e_cell.model.bquiz;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDetailsModel implements Serializable {

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("key")
    @Expose
    private int key;

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }
}
