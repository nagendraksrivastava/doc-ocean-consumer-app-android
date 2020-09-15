package customer.dococean.com.patient.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatePatientRequest {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("relationship")
    @Expose
    private String relationship;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("chronic_health_problem")
    @Expose
    private String chronicHealthProblem;

    @SerializedName("gender")
    @Expose
    private String gender;

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * @param relationship The relationship
     */
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    /**
     * @return The dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth The date_of_birth
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo The phone_no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The chronicHealthProblem
     */
    public String getChronicHealthProblem() {
        return chronicHealthProblem;
    }

    /**
     * @param chronicHealthProblem The chronic_health_problem
     */
    public void setChronicHealthProblem(String chronicHealthProblem) {
        this.chronicHealthProblem = chronicHealthProblem;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}