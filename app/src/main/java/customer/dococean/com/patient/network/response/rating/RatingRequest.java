package customer.dococean.com.patient.network.response.rating;

/**
 * Created by rahul on 04/12/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequest {

    @SerializedName("value")
    @Expose
    private Float value;
    @SerializedName("comments")
    @Expose
    private String comments;

    /**
     *
     * @return
     * The value
     */
    public Float getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Float value) {
        this.value = value;
    }

    /**
     *
     * @return
     * The comments
     */
    public String getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
