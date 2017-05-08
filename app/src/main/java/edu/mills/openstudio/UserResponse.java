package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

/**
 * Defines the format of a response from a studio API call for Retrofit JSON parsing.
 */
class UserResponse {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("uid")
    private String uid;
    @SerializedName("user")
    private User user;

    /**
     * Returns the error status of the API call.
     * @return true if request was successful, false if there was an error
     */
    Boolean getError() {
        return error;
    }

    /**
     * Returns the error message of the request if it was unsuccessful.
     * @return the error message
     */
    String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Returns the unique id of the user
     * @return the user's id
     */
    String getUid() {
        return uid;
    }

    /**
     * Gets the user returned from the API call.
     * @return the user returned in the response
     */
    User getUser() {
        return user;
    }
}
