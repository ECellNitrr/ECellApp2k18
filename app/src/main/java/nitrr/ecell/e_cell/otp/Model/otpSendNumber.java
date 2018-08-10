package nitrr.ecell.e_cell.otp.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class otpSendNumber implements Serializable {

    @SerializedName("contact_no")
    @Expose
    private String mobile_no;

    @SerializedName("Authorization")
    @Expose
    private String token;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;


    }

}
