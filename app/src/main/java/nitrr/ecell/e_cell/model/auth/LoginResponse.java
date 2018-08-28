package nitrr.ecell.e_cell.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse extends GenericResponse implements Serializable {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("first_name")
    @Expose
    private String first_name;

    public String getFirst_name() {
        return first_name;
    }

    public String getToken() {
        return token;
    }
}
