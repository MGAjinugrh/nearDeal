
package com.asic45.neardeal28.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("Ids")
    @Expose
    private Integer ids;
    @SerializedName("Start_date")
    @Expose
    private String startDate;
    @SerializedName("End_date")
    @Expose
    private String endDate;
    @SerializedName("Discount")
    @Expose
    private Integer discount;
    @SerializedName("Product")
    @Expose
    private Product product;

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
