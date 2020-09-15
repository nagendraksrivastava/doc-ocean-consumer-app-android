package customer.dococean.com.patient.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nagendrasrivastava on 16/07/16.
 */
public class Status implements Parcelable {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     *
     * @param code
     * The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.code);
        dest.writeString(this.message);
    }

    public Status() {
    }

    protected Status(Parcel in) {
        this.code = (Integer) in.readValue(Integer.class.getClassLoader());
        this.message = in.readString();
    }

    public static final Parcelable.Creator<Status> CREATOR = new Parcelable.Creator<Status>() {
        @Override
        public Status createFromParcel(Parcel source) {
            return new Status(source);
        }

        @Override
        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
}
