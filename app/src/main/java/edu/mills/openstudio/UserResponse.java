package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nadin on 5/4/2017.
 */

public class UserResponse {
    @SerializedName("error")
    private String error;
    @SerializedName("uid")
    private String uid;
    @SerializedName("user")
    private User user;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
