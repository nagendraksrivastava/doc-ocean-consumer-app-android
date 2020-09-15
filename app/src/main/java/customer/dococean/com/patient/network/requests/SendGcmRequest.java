package customer.dococean.com.patient.network.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendGcmRequest {

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("push_notification_reg_id")
    @Expose
    private String pushNotificationRegId;
    @SerializedName("checksum")
    @Expose
    private String checksum;

    /**
     * @return The deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId The device_id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return The pushNotificationRegId
     */
    public String getPushNotificationRegId() {
        return pushNotificationRegId;
    }

    /**
     * @param pushNotificationRegId The push_notification_reg_id
     */
    public void setPushNotificationRegId(String pushNotificationRegId) {
        this.pushNotificationRegId = pushNotificationRegId;
    }

    /**
     * @return The checksum
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * @param checksum The checksum
     */
    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

}