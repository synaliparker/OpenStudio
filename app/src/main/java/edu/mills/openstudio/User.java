package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("created_at")
    private String createdAt;

    public User(String name, String email, String createdAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email =  email;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}


