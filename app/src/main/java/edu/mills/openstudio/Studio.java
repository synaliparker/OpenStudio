package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the format of a studio for Retrofit JSON parsing.
 */
class Studio {
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

    /**
     * Returns the studio's id
     * @return id of the studio
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the studio's id
     * @param id the new studio id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the studio's name
     * @return the studio's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the studio's name to the given name
     * @param name the new studio name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the studio's owner
     * @return the studio's owner
     */
    String getOwner() {
        return owner;
    }

    /**
     * Gets the studio's address
     * @return the studio's address
     */
    String getAddress() {
        return address;
    }

    /**
     * Gets the studio's type
     * @return the studio's type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the studio's contact info (currently just emails)
     * @return the studio's contact info
     */
    String getContactInfo() {
        return contactInfo;
    }

    /**
     * Gets the studio's availability
     * @return the studio's availability
     */
    String getAvailability() {
        return availability;
    }

    /**
     * Gets the studio's accessibility details
     * @return the studio's accessibility
     */
    String getAccessibility() {
        return accessibility;
    }

    /**
     * Gets the studio's description.
     * @return the studio's description
     */
    String getDescription() {
        return description;
    }

    /**
     * Gets the studio's latitude.
     * @return latitude of the studio's location
     */
    Double getLat() {
        return lat;
    }

    /**
     * Gets the studio's longitude.
     * @return longitude of the studio's location
     */
    Double getLng() {
        return lng;
    }
}


