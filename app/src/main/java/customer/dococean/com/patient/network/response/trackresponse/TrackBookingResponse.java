package customer.dococean.com.patient.network.response.trackresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import customer.dococean.com.patient.network.response.Status;

public class TrackBookingResponse implements Parcelable {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("appointment")
    @Expose
    private Appointment appointment;

    /**
     *
     * @return
     * The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The appointment
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     *
     * @param appointment
     * The appointment
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeParcelable(this.appointment, flags);
    }

    public TrackBookingResponse() {
    }

    protected TrackBookingResponse(Parcel in) {
        this.status = in.readParcelable(Status.class.getClassLoader());
        this.appointment = in.readParcelable(Appointment.class.getClassLoader());
    }

    public static final Parcelable.Creator<TrackBookingResponse> CREATOR = new Parcelable.Creator<TrackBookingResponse>() {
        @Override
        public TrackBookingResponse createFromParcel(Parcel source) {
            return new TrackBookingResponse(source);
        }

        @Override
        public TrackBookingResponse[] newArray(int size) {
            return new TrackBookingResponse[size];
        }
    };
}