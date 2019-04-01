
package id.co.rumahcoding.neardeal.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("Ids")
    @Expose
    private Integer ids;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Lat")
    @Expose
    private Double lat;
    @SerializedName("Lng")
    @Expose
    private Double lng;
    @SerializedName("Photo")
    @Expose
    private String photo;
    @SerializedName("Telp")
    @Expose
    private Integer telp;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Open_hour")
    @Expose
    private String openHour;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Created_at")
    @Expose
    private String createdAt;

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getTelp() {
        return telp;
    }

    public void setTelp(Integer telp) {
        this.telp = telp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpenHour() {
        return openHour;
    }

    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
