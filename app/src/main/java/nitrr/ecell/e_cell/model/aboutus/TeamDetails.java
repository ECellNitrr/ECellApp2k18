package nitrr.ecell.e_cell.model.aboutus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamDetails {

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("url")
    @Expose
    private String url;


    @SerializedName("image")
    @Expose
    private String image;


    @SerializedName("member_type")
    @Expose
    private String memberType;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getMemberType() {
        return memberType;
    }

    public String getImage() {
        return image;
    }
}
