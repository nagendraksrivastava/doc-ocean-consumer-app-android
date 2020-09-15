package customer.dococean.com.patient.network.response.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class UserProfileResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("details")
    @Expose
    private Details details;

    /**
     * @return The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return The details
     */
    public Details getDetails() {
        return details;
    }

    /**
     * @param details The details
     */
    public void setDetails(Details details) {
        this.details = details;
    }

}