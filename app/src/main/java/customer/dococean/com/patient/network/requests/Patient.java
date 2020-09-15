package customer.dococean.com.patient.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("age")
    @Expose
    private Integer age;

    @SerializedName("relationship")
    @Expose
    private String relationship;

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
     * @return The age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age The age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}