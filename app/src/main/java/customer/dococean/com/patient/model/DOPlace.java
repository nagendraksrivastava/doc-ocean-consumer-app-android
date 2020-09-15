package customer.dococean.com.patient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class DOPlace implements Parcelable {

    private String name, address, id, attributions, city;
    private float rating;
    private LatLng latLng;
    private Locale locale;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.id);
        dest.writeFloat(this.rating);
        dest.writeString(this.attributions);
        dest.writeString(this.city);
        dest.writeParcelable(this.latLng, 0);
        dest.writeSerializable(this.locale);
    }

    public DOPlace() {
    }

    protected DOPlace(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.id = in.readString();
        this.rating = in.readFloat();
        this.attributions = in.readString();
        this.city = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.locale = (Locale) in.readSerializable();
    }

    public static final Parcelable.Creator<DOPlace> CREATOR = new Parcelable.Creator<DOPlace>() {
        public DOPlace createFromParcel(Parcel source) {
            return new DOPlace(source);
        }

        public DOPlace[] newArray(int size) {
            return new DOPlace[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
