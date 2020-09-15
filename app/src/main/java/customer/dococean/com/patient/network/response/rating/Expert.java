package customer.dococean.com.patient.network.response.rating;

/**
 * Created by rahul on 04/12/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expert {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("degree_list")
    @Expose
    private String degreeList;
    @SerializedName("specialization_list")
    @Expose
    private String specializationList;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;

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
     * The degreeList
     */
    public String getDegreeList() {
        return degreeList;
    }

    /**
     *
     * @param degreeList
     * The degree_list
     */
    public void setDegreeList(String degreeList) {
        this.degreeList = degreeList;
    }

    /**
     *
     * @return
     * The specializationList
     */
    public String getSpecializationList() {
        return specializationList;
    }

    /**
     *
     * @param specializationList
     * The specialization_list
     */
    public void setSpecializationList(String specializationList) {
        this.specializationList = specializationList;
    }

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

}