package nitrr.ecell.e_cell.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SplashScreenResponse extends GenericResponse implements Serializable {

    @SerializedName("version")
    @Expose
    private float version;

    @SerializedName("url")
    @Expose
    private String url;

    public float getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

}
