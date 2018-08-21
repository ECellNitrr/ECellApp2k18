package nitrr.ecell.e_cell.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookSignInUserDetails implements Serializable {

    @SerializedName("isfb")
    @Expose
    Boolean isFacebook;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("email")
    @Expose
    String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getFacebook() {
        return isFacebook;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacebook(Boolean facebook) {
        isFacebook = facebook;
    }
}
