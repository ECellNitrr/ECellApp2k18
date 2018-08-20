package nitrr.ecell.e_cell.model.otp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import nitrr.ecell.e_cell.model.auth.GenericResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyOtp extends GenericResponse implements Serializable {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
