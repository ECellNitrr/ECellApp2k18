package nitrr.ecell.e_cell.otp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
public class sendOtp implements Serializable {

    @SerializedName("otp")
    @Expose
    private String otpEnterd;

    @SerializedName("token")
    @Expose
    private String token;

    public String getOtpEnterd() {
        return otpEnterd;
    }

    public void setOtpEnterd(String otpEnterd) {
        this.otpEnterd = otpEnterd;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
