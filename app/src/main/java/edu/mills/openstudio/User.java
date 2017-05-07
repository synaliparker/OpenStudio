package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nadin on 5/4/2017.
 */
public class User {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public void setPassword(String password) {
        this.password = password;
    }
}


