package customer.dococean.com.patient.network.response.getpatient;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class GetPatientResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("patients")
    @Expose
    private List<Patient> patients = new ArrayList<Patient>();

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
     * @return The patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * @param patients The patients
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}