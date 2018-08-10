package nitrr.ecell.e_cell.otp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationVerifyOtpResponse extends VerifyOtp implements Serializable {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

}
