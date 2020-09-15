package customer.dococean.com.patient.network.response.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

/**
 * Created by rahul on 04/12/16.
 */

public class RatingResponse {
    @SerializedName("status")
    @Expose
    private Status status;

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
}
