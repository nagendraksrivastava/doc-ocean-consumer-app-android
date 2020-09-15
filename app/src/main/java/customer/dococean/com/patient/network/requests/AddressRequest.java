package customer.dococean.com.patient.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressRequest {

    @SerializedName("address_line")
    @Expose
    private String addressLine;
    @SerializedName("locality_id")
    @Expose
    private Integer localityId;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("tag")
    @Expose
    private String tag;

    /**
     *
     * @return
     * The addressLine
     */
    public String getAddressLine() {
        return addressLine;
    }

    /**
     *
     * @param addressLine
     * The address_line
     */
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    /**
     *
     * @return
     * The localityId
     */
    public Integer getLocalityId() {
        return localityId;
    }

    /**
     *
     * @param localityId
     * The locality_id
     */
    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    /**
     *
     * @return
     * The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The city_id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The landmark
     */
    public String getLandmark() {
        return landmark;
    }

    /**
     *
     * @param landmark
     * The landmark
     */
    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
     * The tag
     */
    public String getTag() {
        return tag;
    }

    /**
     *
     * @param tag
     * The tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

}