package customer.dococean.com.patient.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("device_id")
    @Expose
    private String deviceId;


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    /**
     * @return The user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

}