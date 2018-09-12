package hu.bme.aut.physicexperiment.Model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("_id")
    public String id;
    public String name;
    public String description;
    public long timestamp;
    public String url;
    public long size;
    public String mimetype;
    public String encoding;
}