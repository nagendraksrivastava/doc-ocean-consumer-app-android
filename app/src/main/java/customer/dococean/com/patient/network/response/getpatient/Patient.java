package customer.dococean.com.patient.network.response.getpatient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("myself")
    @Expose
    private Boolean myself;

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
     * @return The myself
     */
    public Boolean getMyself() {
        return myself;
    }

    /**
     * @param myself The myself
     */
    public void setMyself(Boolean myself) {
        this.myself = myself;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}