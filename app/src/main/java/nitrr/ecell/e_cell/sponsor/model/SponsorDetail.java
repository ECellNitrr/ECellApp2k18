package nitrr.ecell.e_cell.sponsor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SponsorDetail implements Serializable{

    @SerializedName("id")
    @Expose
    private  String id_s;

    @SerializedName("name")
    @Expose
    private  String name_s;

    @SerializedName("details")
    @Expose
    private  String details_s;

    @SerializedName("pic")
    @Expose
    private  String pic_s;

    @SerializedName("contact")
    @Expose
    private  String contact_s;

    @SerializedName("website")
    @Expose
    private  String website_s;

    @SerializedName("spons_type")
    @Expose
    private  String spons_type_s;

    @SerializedName("flag")
    @Expose
    private  String flag_s;

    public String getId_s() {
        return id_s;
    }

    public String getName_s() {
        return name_s;
    }

    public String getDetails_s() {
        return details_s;
    }

    public String getPic_s() {
        return pic_s;
    }

    public String getContact_s() {
        return contact_s;
    }

    public String getWebsite_s() {
        return website_s;
    }

    public String getSpons_type_s() {
        return spons_type_s;
    }

    public String getFlag_s() {
        return flag_s;
    }
}

