package customer.dococean.com.patient.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nagendrasrivastava on 10/09/16.
 */
public class AddressModel implements Parcelable {
    private String address;
    private Double latitude;
    private Double longitude;

    public AddressModel() {

    }

    public AddressModel(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    protected AddressModel(Parcel in) {
        this.address = in.readString();
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel source) {
            return new AddressModel(source);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };
}
