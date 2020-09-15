package customer.dococean.com.patient.network.response.myappointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YourRating {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("comments")
    @Expose
    private Object comments;

    /**
     *
     * @return
     * The value
     */
    public Integer getValue() {
        return value;
    }

    /**
     *
     * @param value
     * The value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     *
     * @return
     * The comments
     */
    public Object getComments() {
        return comments;
    }

    /**
     *
     * @param comments
     * The comments
     */
    public void setComments(Object comments) {
        this.comments = comments;
    }

}