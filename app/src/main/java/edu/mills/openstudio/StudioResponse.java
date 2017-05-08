package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Defines the format of a response from a studio API call for Retrofit JSON parsing.
 */
class StudioResponse {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("studios")
    private List<Studio> studios;

    /**
     * Gets a list of studios returned from the API call.
     * @return the studios returned in the response
     */
    List<Studio> getStudios() {
        return studios;
    }

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
}
