package nitrr.ecell.e_cell.model.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventsData implements Serializable{

    @SerializedName("id")
    @Expose
    private String id_response;

    @SerializedName("name")
    @Expose
    private String name_response;

    @SerializedName("venue")
    @Expose
    private String venue_response;

    @SerializedName("date")
    @Expose
    private String date_response;

    @SerializedName("time")
    @Expose
    private String time_response;

    @SerializedName("details")
    @Expose
    private String details_response;

    @SerializedName("cover_pic")
    @Expose
    private String cover_pic_response;

    @SerializedName("icon")
    @Expose
    private String icon_response;

    @SerializedName("email")
    @Expose
    private String email_response;

    @SerializedName("flag")
    @Expose
    private String flag_response;

    public String getId_response() {
        return id_response;
    }

    public String getName_response() {
        return name_response;
    }

    public String getVenue_response() {
        return venue_response;
    }

    public String getDate_response() {
        return date_response;
    }

    public String getTime_response() {
        return time_response;
    }

    public String getDetails_response() {
        return details_response;
    }

    public String getCover_pic_response() {
        return cover_pic_response;
    }

    public String getIcon_response() {
        return icon_response;
    }

    public String getEmail_response() {
        return email_response;
    }

    public String getFlag_response() {
        return flag_response;
    }
}
