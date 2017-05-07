package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nadin on 5/4/2017.
 */
public class Studio {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("owner")
    private String owner;
    @SerializedName("type")
    private String type;
    @SerializedName("address")
    private String address;
    @SerializedName("contact_info")
    private String contactInfo;
    @SerializedName("availability")
    private String availability;
    @SerializedName("accessibility")
    private String accessibility;
    @SerializedName("description")
    private String description;
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lng")
    private Double lng;

    public Studio(String name, String owner, String type, String address, String contactInfo,
                  String availability, String accessibility, String description, Double lat, Double lng) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.address = address;
        this.contactInfo = contactInfo;
        this.availability = availability;
        this.accessibility = accessibility;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    public Studio(int id, String name, String owner, String type, String address, String contactInfo,
           String availability, String accessibility, String description, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.address = address;
        this.contactInfo = contactInfo;
        this.availability = availability;
        this.accessibility = accessibility;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner =  owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}


