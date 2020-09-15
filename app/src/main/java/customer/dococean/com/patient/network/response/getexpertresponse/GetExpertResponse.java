package customer.dococean.com.patient.network.response.getexpertresponse;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class GetExpertResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("experts")
    @Expose
    private List<Expert> experts = new ArrayList<Expert>();
    @SerializedName("appointment_costs")
    @Expose
    private AppointmentCosts appointmentCosts;

    /**
     *
     * @return
     * The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The experts
     */
    public List<Expert> getExperts() {
        return experts;
    }

    /**
     *
     * @param experts
     * The experts
     */
    public void setExperts(List<Expert> experts) {
        this.experts = experts;
    }

    /**
     *
     * @return
     * The appointmentCosts
     */
    public AppointmentCosts getAppointmentCosts() {
        return appointmentCosts;
    }

    /**
     *
     * @param appointmentCosts
     * The appointment_costs
     */
    public void setAppointmentCosts(AppointmentCosts appointmentCosts) {
        this.appointmentCosts = appointmentCosts;
    }

}