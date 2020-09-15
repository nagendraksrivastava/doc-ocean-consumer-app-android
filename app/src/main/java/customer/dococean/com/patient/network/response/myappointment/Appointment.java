package customer.dococean.com.patient.network.response.myappointment;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("patient_gender")
    @Expose
    private String patientGender;
    @SerializedName("service_place")
    @Expose
    private String servicePlace;
    @SerializedName("patient_age")
    @Expose
    private Integer patientAge;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("symptoms")
    @Expose
    private String symptoms;
    @SerializedName("total_cost")
    @Expose
    private String totalCost;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("chronic_health_problems")
    @Expose
    private String chronicHealthProblems;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("opted_services")
    @Expose
    private List<OptedService> optedServices = new ArrayList<OptedService>();
    @SerializedName("confirmed_at")
    @Expose
    private String confirmedAt;
    @SerializedName("expert")
    @Expose
    private Expert expert;
    @SerializedName("your_rating")
    @Expose
    private YourRating yourRating;

    /**
     * @return The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return The patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * @param patientName The patient_name
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    /**
     * @return The patientGender
     */
    public String getPatientGender() {
        return patientGender;
    }

    /**
     * @param patientGender The patient_gender
     */
    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    /**
     * @return The servicePlace
     */
    public String getServicePlace() {
        return servicePlace;
    }

    /**
     * @param servicePlace The service_place
     */
    public void setServicePlace(String servicePlace) {
        this.servicePlace = servicePlace;
    }

    /**
     * @return The patientAge
     */
    public Integer getPatientAge() {
        return patientAge;
    }

    /**
     * @param patientAge The patient_age
     */
    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return The chronicHealthProblems
     */
    public String getChronicHealthProblems() {
        return chronicHealthProblems;
    }

    /**
     * @param chronicHealthProblems The chronic_health_problems
     */
    public void setChronicHealthProblems(String chronicHealthProblems) {
        this.chronicHealthProblems = chronicHealthProblems;
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
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The optedServices
     */
    public List<OptedService> getOptedServices() {
        return optedServices;
    }

    /**
     * @param optedServices The opted_services
     */
    public void setOptedServices(List<OptedService> optedServices) {
        this.optedServices = optedServices;
    }

    /**
     * @return The confirmedAt
     */
    public String getConfirmedAt() {
        return confirmedAt;
    }

    /**
     * @param confirmedAt The confirmed_at
     */
    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    /**
     * @return The expert
     */
    public Expert getExpert() {
        return expert;
    }

    /**
     * @param expert The expert
     */
    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    /**
     * @return The yourRating
     */
    public YourRating getYourRating() {
        return yourRating;
    }

    /**
     * @param yourRating The your_rating
     */
    public void setYourRating(YourRating yourRating) {
        this.yourRating = yourRating;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
}