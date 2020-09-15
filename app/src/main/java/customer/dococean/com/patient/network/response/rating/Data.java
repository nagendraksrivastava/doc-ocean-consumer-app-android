package customer.dococean.com.patient.network.response.rating;

/**
 * Created by rahul on 04/12/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("expert")
    @Expose
    private Expert expert;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    /**
     *
     * @return
     * The number
     */
    public String getNumber() {
        return number;
    }

    /**
     *
     * @param number
     * The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     *
     * @return
     * The patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     *
     * @param patientName
     * The patient_name
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     *
     * @return
     * The expert
     */
    public Expert getExpert() {
        return expert;
    }

    /**
     *
     * @param expert
     * The expert
     */
    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     *
     * @param instructions
     * The instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}