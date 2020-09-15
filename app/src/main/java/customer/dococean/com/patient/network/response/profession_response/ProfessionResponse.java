package customer.dococean.com.patient.network.response.profession_response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class ProfessionResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("professions")
    @Expose
    private List<Profession> professions = new ArrayList<Profession>();

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
     * @return The professions
     */
    public List<Profession> getProfessions() {
        return professions;
    }

    /**
     * @param professions The professions
     */
    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeTypedList(this.professions);
    }

    public ProfessionResponse() {
    }

    protected ProfessionResponse(Parcel in) {
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.professions = in.createTypedArrayList(Profession.CREATOR);
    }

    public static final Parcelable.Creator<ProfessionResponse> CREATOR = new Parcelable.Creator<ProfessionResponse>() {
        @Override
        public ProfessionResponse createFromParcel(Parcel source) {
            return new ProfessionResponse(source);
        }

        @Override
        public ProfessionResponse[] newArray(int size) {
            return new ProfessionResponse[size];
        }
    };
}