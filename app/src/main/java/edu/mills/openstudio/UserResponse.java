package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("uid")
    private String uid;
    @SerializedName("user")
    private User user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
