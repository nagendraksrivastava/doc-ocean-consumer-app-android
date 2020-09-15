package customer.dococean.com.patient.network.response.saveaddress;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class SaveAddressResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;

    /**
     * @return The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return The addressId
     */
    public Integer getAddressId() {
        return addressId;
    }

    /**
     * @param addressId The address_id
     */
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

}