package nitrr.ecell.e_cell.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeakerList implements Serializable{

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("contact")
    @Expose
    private String contact;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("profile_pic")
    @Expose
    private String profile_pic;

    @SerializedName("year")
    @Expose
    private String year;

    @SerializedName("social_media")
    @Expose
    private String socialMedia;

    @SerializedName("flag")
    @Expose
    private String flag;


    public String getProfile_pic() {
        return profile_pic;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getYear() {
        return year;
    }
}
