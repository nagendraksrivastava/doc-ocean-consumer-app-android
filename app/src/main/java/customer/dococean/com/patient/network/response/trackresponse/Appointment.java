package customer.dococean.com.patient.network.response.trackresponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment implements Parcelable {

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
    public Object getChronicHealthProblems() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.number);
        dest.writeString(this.patientName);
        dest.writeString(this.patientGender);
        dest.writeString(this.servicePlace);
        dest.writeValue(this.patientAge);
        dest.writeString(this.status);
        dest.writeString(this.symptoms);
        dest.writeString(this.chronicHealthProblems);
        dest.writeString(this.instructions);
        dest.writeString(this.createdAt);
        dest.writeList(this.optedServices);
        dest.writeString(this.confirmedAt);
        dest.writeParcelable(this.expert, flags);
    }

    public Appointment() {
    }

    protected Appointment(Parcel in) {
        this.number = in.readString();
        this.patientName = in.readString();
        this.patientGender = in.readString();
        this.servicePlace = in.readString();
        this.patientAge = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = in.readString();
        this.symptoms = in.readString();
        this.chronicHealthProblems = in.readString();
        this.instructions = in.readString();
        this.createdAt = in.readString();
        this.optedServices = new ArrayList<OptedService>();
        in.readList(this.optedServices, OptedService.class.getClassLoader());
        this.confirmedAt = in.readString();
        this.expert = in.readParcelable(Expert.class.getClassLoader());
    }

    public static final Parcelable.Creator<Appointment> CREATOR = new Parcelable.Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}