package customer.dococean.com.patient.network.response.getexpertresponse;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expert {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("expert_detail_id")
    @Expose
    private Integer expertDetailId;
    @SerializedName("profession_id")
    @Expose
    private Integer professionId;
    @SerializedName("profession_code")
    @Expose
    private String professionCode;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("degree_list")
    @Expose
    private List<String> degreeList = new ArrayList<String>();
    @SerializedName("specialization_list")
    @Expose
    private List<String> specializationList = new ArrayList<String>();
    @SerializedName("license_no")
    @Expose
    private String licenseNo;
    @SerializedName("consulting_fee")
    @Expose
    private Integer consultingFee;
    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("service_place")
    @Expose
    private String servicePlace;
    @SerializedName("address")
    @Expose
    private Address address;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The phone_no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The gender
     */
    public Object getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     * The gender
     */
    public void setGender(Object gender) {
        this.gender = gender;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The expertDetailId
     */
    public Integer getExpertDetailId() {
        return expertDetailId;
    }

    /**
     *
     * @param expertDetailId
     * The expert_detail_id
     */
    public void setExpertDetailId(Integer expertDetailId) {
        this.expertDetailId = expertDetailId;
    }

    /**
     *
     * @return
     * The professionId
     */
    public Integer getProfessionId() {
        return professionId;
    }

    /**
     *
     * @param professionId
     * The profession_id
     */
    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    /**
     *
     * @return
     * The professionCode
     */
    public String getProfessionCode() {
        return professionCode;
    }

    /**
     *
     * @param professionCode
     * The profession_code
     */
    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
    }

    /**
     *
     * @return
     * The profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     *
     * @param profession
     * The profession
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     *
     * @return
     * The degreeList
     */
    public List<String> getDegreeList() {
        return degreeList;
    }

    /**
     *
     * @param degreeList
     * The degree_list
     */
    public void setDegreeList(List<String> degreeList) {
        this.degreeList = degreeList;
    }

    /**
     *
     * @return
     * The specializationList
     */
    public List<String> getSpecializationList() {
        return specializationList;
    }

    /**
     *
     * @param specializationList
     * The specialization_list
     */
    public void setSpecializationList(List<String> specializationList) {
        this.specializationList = specializationList;
    }

    /**
     *
     * @return
     * The licenseNo
     */
    public String getLicenseNo() {
        return licenseNo;
    }

    /**
     *
     * @param licenseNo
     * The license_no
     */
    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    /**
     *
     * @return
     * The consultingFee
     */
    public Integer getConsultingFee() {
        return consultingFee;
    }

    /**
     *
     * @param consultingFee
     * The consulting_fee
     */
    public void setConsultingFee(Integer consultingFee) {
        this.consultingFee = consultingFee;
    }

    /**
     *
     * @return
     * The rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     * The rating
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     * The servicePlace
     */
    public String getServicePlace() {
        return servicePlace;
    }

    /**
     *
     * @param servicePlace
     * The service_place
     */
    public void setServicePlace(String servicePlace) {
        this.servicePlace = servicePlace;
    }

    /**
     *
     * @return
     * The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

}