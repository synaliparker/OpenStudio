package edu.mills.openstudio;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class StudioResponse {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("error_msg")
    private String errorMsg;
    @SerializedName("studios")
    private List<Studio> studios;

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;
    }

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
}
