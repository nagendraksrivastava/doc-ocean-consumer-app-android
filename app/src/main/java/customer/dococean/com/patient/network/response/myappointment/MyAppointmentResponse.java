package customer.dococean.com.patient.network.response.myappointment;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class MyAppointmentResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("appointments")
    @Expose
    private List<Appointment> appointments = new ArrayList<Appointment>();

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
     * The appointments
     */
    public List<Appointment> getAppointments() {
        return appointments;
    }

    /**
     *
     * @param appointments
     * The appointments
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

}