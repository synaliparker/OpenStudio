package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nadin on 5/4/2017.
 */

public class StudioResponse {
    @SerializedName("error")
    private String error;
    @SerializedName("studios")
    private List<Studio> studios;

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
