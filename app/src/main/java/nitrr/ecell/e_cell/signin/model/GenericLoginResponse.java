package nitrr.ecell.e_cell.signin.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericLoginResponse implements Serializable{

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("message")
    @Expose
    private String message;


    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }



}