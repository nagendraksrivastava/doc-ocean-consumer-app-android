package customer.dococean.com.patient.network.response.service_response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class ServiceResponse {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("services")
    @Expose
    private List<Service> services = new ArrayList<Service>();

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
     * @return The services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @param services The services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

}