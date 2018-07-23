package nitrr.ecell.e_cell.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AboutUsResponse extends GenericResponse implements Serializable {

    @SerializedName("Director, NIT Raipur")
    @Expose
    private List<TeamDetails> directorInfo;

    @SerializedName("Head of Career development")
    @Expose
    private List<TeamDetails> headOfCareerDevelopment;

    @SerializedName("Faculty Incharge")
    @Expose
    private List<TeamDetails> facultyIncharge;

    @SerializedName("Head Co-ordinator")
    @Expose
    private List<TeamDetails> headCoordi;

    @SerializedName("Overall Co-ordinator")
    @Expose
    private List<TeamDetails> overallCoordi;

    @SerializedName("manager")
    @Expose
    private List<TeamDetails> manager;

    @SerializedName("executive")
    @Expose
    private List<TeamDetails> executive;

    public List<TeamDetails> getDirectorInfo() {
        return directorInfo;
    }

    public List<TeamDetails> getHeadOfCareerDevelopment() {
        return headOfCareerDevelopment;
    }

    public List<TeamDetails> getFacultyIncharge() {
        return facultyIncharge;
    }

    public List<TeamDetails> getExecutive() {
        return executive;
    }

    public List<TeamDetails> getHeadCoordi() {
        return headCoordi;
    }

    public List<TeamDetails> getManager() {
        return manager;
    }

    public List<TeamDetails> getOverallCoordi() {
        return overallCoordi;
    }
}
