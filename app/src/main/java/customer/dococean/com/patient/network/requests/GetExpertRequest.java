package customer.dococean.com.patient.network.requests;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetExpertRequest {

    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("profession_id")
    @Expose
    private Integer professionId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("opted_service_ids")
    @Expose
    private List<Integer> optedServiceIds = new ArrayList<Integer>();

    @SerializedName("schedule_time")
    @Expose
    private String scheduleTime;

    /**
     * @return The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return The professionId
     */
    public Integer getProfessionId() {
        return professionId;
    }

    /**
     * @param professionId The profession_id
     */
    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

    /**
     * @return The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId The category_id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return The optedServiceIds
     */
    public List<Integer> getOptedServiceIds() {
        return optedServiceIds;
    }

    /**
     * @param optedServiceIds The opted_service_ids
     */
    public void setOptedServiceIds(List<Integer> optedServiceIds) {
        this.optedServiceIds = optedServiceIds;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }
}