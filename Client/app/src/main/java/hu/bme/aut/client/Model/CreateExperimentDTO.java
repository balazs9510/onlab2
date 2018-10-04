package hu.bme.aut.client.Model;

import java.util.Date;

public class CreateExperimentDTO {
    private String Name;
    public String Author;
    public Date StartDate ;
    public String Description ;
    public Date ExpectedEndDate;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getExpectedEndDate() {
        return ExpectedEndDate;
    }

    public void setExpectedEndDate(Date expectedEndDate) {
        ExpectedEndDate = expectedEndDate;
    }
}
