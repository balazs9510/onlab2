package hu.bme.aut.client.Model;

import java.util.Date;

public class Experiment {
    String _id;
    String name;

    public String getName() {
        return name;
    }

    public Experiment(String _id, String name, String author, String state) {
        this._id = _id;
        this.name = name;
        this.author = author;
        this.state = state;
    }

    public void setName(String name) {
        this.name = name;

    }

    String author;
    String state;
    String description;
    Date startDate;
    Date endDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    Date expectedEndDate;
}
