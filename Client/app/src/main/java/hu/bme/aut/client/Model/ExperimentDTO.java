package hu.bme.aut.client.Model;

import java.util.HashMap;
import java.util.Map;

public class ExperimentDTO {

private String id;
private Object creatorUser;
private String author;
private String startDate;
private String endDate;
private String expectedEndDate;
private String description;
private String name;
private Integer state;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

public void setEndDate(String endDate) {
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


public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}