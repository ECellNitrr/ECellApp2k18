package nitrr.ecell.e_cell.model.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import nitrr.ecell.e_cell.model.auth.GenericResponse;


@JsonIgnoreProperties(ignoreUnknown = true)
public class EventsResponse extends GenericResponse implements Serializable {

    @SerializedName("events")
    @Expose
    private List<EventsData> Events;

    public List<EventsData> getEvents() {
        return Events;
    }
}


