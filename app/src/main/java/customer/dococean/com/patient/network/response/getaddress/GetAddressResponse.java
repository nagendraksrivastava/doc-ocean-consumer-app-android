package customer.dococean.com.patient.network.response.getaddress;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class GetAddressResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = new ArrayList<Address>();

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
     * @return The addresses
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * @param addresses The addresses
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}