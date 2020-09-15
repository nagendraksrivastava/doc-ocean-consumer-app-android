package customer.dococean.com.patient.network.response.getexpertresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentCosts {

    @SerializedName("EXPERT_PLACE")
    @Expose
    private Integer eXPERTPLACE;
    @SerializedName("PATIENT_PLACE")
    @Expose
    private Integer pATIENTPLACE;

    /**
     *
     * @return
     * The eXPERTPLACE
     */
    public Integer getEXPERTPLACE() {
        return eXPERTPLACE;
    }

    /**
     *
     * @param eXPERTPLACE
     * The EXPERT_PLACE
     */
    public void setEXPERTPLACE(Integer eXPERTPLACE) {
        this.eXPERTPLACE = eXPERTPLACE;
    }

    /**
     *
     * @return
     * The pATIENTPLACE
     */
    public Integer getPATIENTPLACE() {
        return pATIENTPLACE;
    }

    /**
     *
     * @param pATIENTPLACE
     * The PATIENT_PLACE
     */
    public void setPATIENTPLACE(Integer pATIENTPLACE) {
        this.pATIENTPLACE = pATIENTPLACE;
    }

}