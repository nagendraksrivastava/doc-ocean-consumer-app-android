package customer.dococean.com.patient.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import customer.dococean.com.patient.network.response.createappointment.Address;

public class AppointmentRequest {

    @SerializedName("expert_detail_id")
    @Expose
    private Integer expertDetailId;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("profession_id")
    @Expose
    private Integer professionId;

    @SerializedName("category_id")
    @Expose
    private Integer categoryId;

    @SerializedName("patient_address_id")
    @Expose
    private Integer patientAddressId;

    @SerializedName("patient_address")
    @Expose
    private Address patientAddress;

    @SerializedName("patient")
    @Expose
    private Patient patient;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("instructions")
    @Expose
    private String instructions;

    @SerializedName("service_place")
    @Expose
    private String servicePlace;

    @SerializedName("scheduled_at")
    @Expose
    private String scheduleAt;

    @SerializedName("opted_service_ids")
    @Expose
    private List<Integer> optedServiceIds = new ArrayList<Integer>();


    /**
     * @return The expertDetailId
     */
    public Integer getExpertDetailId() {
        return expertDetailId;
    }

    /**
     * @param expertDetailId The expert_detail_id
     */
    public void setExpertDetailId(Integer expertDetailId) {
        this.expertDetailId = expertDetailId;
    }

    /**
     * @return The patientId
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * @param patientId The patient_id
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    /**
     * @return The professionId
     */
    public Integer getProfessionId() {
        return professionId;
    }

    /**
     * @param professionId The profession_id
     */
    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    /**
     * @return The patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * @param patient The patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * @return The symptoms
     */
    public String getSymptoms() {
        return symptoms;
    }

    /**
     * @param symptoms The symptoms
     */
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * @return The instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions The instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * @return The scheduleTime
     */
    public String getScheduleTime() {
        return scheduleAt;
    }

    /**
     * @param scheduleTime The schedule_time
     */
    public void setScheduleTime(String scheduleTime) {
        this.scheduleAt = scheduleTime;
    }

    public String getServicePlace() {
        return servicePlace;
    }

    public void setServicePlace(String servicePlace) {
        this.servicePlace = servicePlace;
    }

    public Integer getPatientAddressId() {
        return patientAddressId;
    }

    public void setPatientAddressId(Integer patientAddressId) {
        this.patientAddressId = patientAddressId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<Integer> getOptedServiceIds() {
        return optedServiceIds;
    }

    public void setOptedServiceIds(List<Integer> optedServiceIds) {
        this.optedServiceIds = optedServiceIds;
    }

    public Address getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(Address patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getScheduleAt() {
        return scheduleAt;
    }

    public void setScheduleAt(String scheduleAt) {
        this.scheduleAt = scheduleAt;
    }
}