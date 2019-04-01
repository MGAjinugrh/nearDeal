
package com.asic45.neardeal28.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("Ids")
    @Expose
    private Integer ids;
    @SerializedName("Store_id")
    @Expose
    private Integer storeId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Photo")
    @Expose
    private String photo;
    @SerializedName("Price")
    @Expose
    private Integer price;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("store_photo")
    @Expose
    private String storePhoto;
    @SerializedName("Description")
    @Expose
    private String description;

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhoto() {
        return storePhoto;
    }

    public void setStorePhoto(String storePhoto) {
        this.storePhoto = storePhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
