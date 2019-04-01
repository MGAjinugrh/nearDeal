
package com.asic45.neardeal28.api_responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckoutResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("order_Ids")
    @Expose
    private Integer orderIds;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(Integer orderIds) {
        this.orderIds = orderIds;
    }

}
