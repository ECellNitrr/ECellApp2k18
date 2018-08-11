package nitrr.ecell.e_cell.model.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EventsResponse implements Serializable {

    @SerializedName("sucess")
    @Expose
    private String success;


    @SerializedName("Events")
    @Expose
    private List<EventsData> Events;

    public String getSuccess() {
        return success;
    }

    public List<EventsData> getEvents() {
        return Events;
    }
}


