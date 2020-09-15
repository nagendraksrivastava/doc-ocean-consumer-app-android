package customer.dococean.com.patient.network.response.getexpertresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timing {

    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;

    /**
     *
     * @return
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     * The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}