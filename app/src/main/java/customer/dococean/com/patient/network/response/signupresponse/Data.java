package customer.dococean.com.patient.network.response.signupresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("auth_token")
    @Expose
    private String authToken;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("gcm_reg_id")
    @Expose
    private Object gcmRegId;

    /**
     *
     * @return
     * The authToken
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     *
     * @param authToken
     * The auth_token
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
     * The gcmRegId
     */
    public Object getGcmRegId() {
        return gcmRegId;
    }

    /**
     *
     * @param gcmRegId
     * The gcm_reg_id
     */
    public void setGcmRegId(Object gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

}