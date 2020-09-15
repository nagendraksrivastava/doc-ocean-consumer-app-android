package customer.dococean.com.patient.network.response.get_patient_relationship;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class GetPatientRelationshipResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("relationships")
    @Expose
    private List<String> relationships = new ArrayList<String>();

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
     * The relationships
     */
    public List<String> getRelationships() {
        return relationships;
    }

    /**
     *
     * @param relationships
     * The relationships
     */
    public void setRelationships(List<String> relationships) {
        this.relationships = relationships;
    }

}