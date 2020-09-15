package customer.dococean.com.patient.network.response.myappointment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptedService {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cost")
    @Expose
    private Double cost;

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
     * The cost
     */
    public Double getCost() {
        return cost;
    }

    /**
     *
     * @param cost
     * The cost
     */
    public void setCost(Double cost) {
        this.cost = cost;
    }

}