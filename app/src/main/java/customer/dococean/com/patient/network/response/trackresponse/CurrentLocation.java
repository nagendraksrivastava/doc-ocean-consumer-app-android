package customer.dococean.com.patient.network.response.trackresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentLocation implements Parcelable {

    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;

    /**
     *
     * @return
     * The latitude
     */
    public Integer getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Integer getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    public CurrentLocation() {
    }

    protected CurrentLocation(Parcel in) {
        this.latitude = (Integer) in.readValue(Integer.class.getClassLoader());
        this.longitude = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<CurrentLocation> CREATOR = new Parcelable.Creator<CurrentLocation>() {
        @Override
        public CurrentLocation createFromParcel(Parcel source) {
            return new CurrentLocation(source);
        }

        @Override
        public CurrentLocation[] newArray(int size) {
            return new CurrentLocation[size];
        }
    };
}