package customer.dococean.com.patient.network.response.InitAfterLogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ongoing_appointments")
    @Expose
    private Boolean ongoingAppointments;
    @SerializedName("rating_pending")
    @Expose
    private Boolean ratingPending;

    @SerializedName("on_demand_appointment_number")
    @Expose
    private String onDemandAppointmentNumber;

    public String getOnDemandAppointmentNumber() {
        return onDemandAppointmentNumber;
    }

    public void setOnDemandAppointmentNumber(String onDemandAppointmentNumber) {
        this.onDemandAppointmentNumber = onDemandAppointmentNumber;
    }

    /**
     *
     * @return
     * The ongoingAppointments
     */
    public Boolean getOngoingAppointments() {
        return ongoingAppointments;
    }

    /**
     *
     * @param ongoingAppointments
     * The ongoing_appointments
     */
    public void setOngoingAppointments(Boolean ongoingAppointments) {
        this.ongoingAppointments = ongoingAppointments;
    }

    /**
     *
     * @return
     * The ratingPending
     */
    public Boolean getRatingPending() {
        return ratingPending;
    }

    /**
     *
     * @param ratingPending
     * The rating_pending
     */
    public void setRatingPending(Boolean ratingPending) {
        this.ratingPending = ratingPending;
    }

}