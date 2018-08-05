package nitrr.ecell.e_cell.bquiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import nitrr.ecell.e_cell.model.GenericResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BQuizQuestionResponse extends GenericResponse implements Serializable{


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("isImageIncluded")
    @Expose
    private boolean isImageIncluded;

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("time")
    @Expose
    private int time;

    @SerializedName("options")
    @Expose
    private List<QuestionDetailsModel> options;

    public int getId() {
        return id;
    }

    public boolean isImageIncluded() {
        return isImageIncluded;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public int getTime() {
        return time;
    }

    public List<QuestionDetailsModel> getOptions() {
        return options;
    }
}
