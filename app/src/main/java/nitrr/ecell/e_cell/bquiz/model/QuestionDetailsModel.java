package nitrr.ecell.e_cell.bquiz.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDetailsModel implements Serializable {

    @SerializedName("value")
    private String value;

    @SerializedName("key")
    private int key;

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }
}
