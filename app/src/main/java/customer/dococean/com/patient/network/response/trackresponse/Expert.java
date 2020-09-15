package customer.dococean.com.patient.network.response.trackresponse;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Expert implements Parcelable {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("degree_list")
    @Expose
    private String degreeList;
    @SerializedName("specialization_list")
    @Expose
    private String specializationList;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("current_location")
    @Expose
    private CurrentLocation currentLocation;

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The degreeList
     */
    public String getDegreeList() {
        return degreeList;
    }

    /**
     *
     * @param degreeList
     * The degree_list
     */
    public void setDegreeList(String degreeList) {
        this.degreeList = degreeList;
    }

    /**
     *
     * @return
     * The specializationList
     */
    public String getSpecializationList() {
        return specializationList;
    }

    /**
     *
     * @param specializationList
     * The specialization_list
     */
    public void setSpecializationList(String specializationList) {
        this.specializationList = specializationList;
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
     * The address
     */
    public Address getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The currentLocation
     */
    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    /**
     *
     * @param currentLocation
     * The current_location
     */
    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.degreeList);
        dest.writeString(this.specializationList);
        dest.writeString(this.name);
        dest.writeString(this.phoneNo);
        dest.writeParcelable(this.address, flags);
        dest.writeParcelable(this.currentLocation, flags);
    }

    public Expert() {
    }

    protected Expert(Parcel in) {
        this.imageUrl = in.readString();
        this.degreeList = in.readString();
        this.specializationList = in.readString();
        this.name = in.readString();
        this.phoneNo = in.readString();
        this.address = in.readParcelable(Address.class.getClassLoader());
        this.currentLocation = in.readParcelable(CurrentLocation.class.getClassLoader());
    }

    public static final Parcelable.Creator<Expert> CREATOR = new Parcelable.Creator<Expert>() {
        @Override
        public Expert createFromParcel(Parcel source) {
            return new Expert(source);
        }

        @Override
        public Expert[] newArray(int size) {
            return new Expert[size];
        }
    };
}