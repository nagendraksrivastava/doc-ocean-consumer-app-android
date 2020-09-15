package customer.dococean.com.patient.network.response.myappointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("address_line")
    @Expose
    private String addressLine;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;

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
     * The city
     */
    public Object getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(Object city) {
        this.city = city;
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

}