package hu.bme.aut.client.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExperimentDTO {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("CreatorUser")
    @Expose
    private Object creatorUser;
    @SerializedName("Author")
    @Expose
    private String author;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private Object endDate;
    @SerializedName("ExpectedEndDate")
    @Expose
    private String expectedEndDate;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("State")
    @Expose
    private Integer state;
    @SerializedName("Images")
    @Expose
    private Object images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(Object creatorUser) {
        this.creatorUser = creatorUser;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Object getImages() {
        return images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public Experiment toExperiment() {
        Experiment exp = new Experiment(id, name, author, state.toString());
        return exp;
    }
}
