package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the format of a user for Retrofit JSON parsing.
 */
class User {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("created_at")
    private String createdAt;

    /**
     * Creates a new user.
     * @param name the user's full name
     * @param email the user's email
     * @param createdAt the date and time the user was created
     */
    public User(String name, String email, String createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    /**
     * Gets the user's full name.
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     * @param name the new user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user's email.
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email =  email;
    }

    /**
     * Gets the date and time the user was created
     * @return the date and time the user was created
     */
    String getCreatedAt() {
        return createdAt;
    }
}


