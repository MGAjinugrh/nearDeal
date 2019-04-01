package com.asic45.neardeal28.api_services;

import com.asic45.neardeal28.api_responses.CheckoutResponse;
import com.asic45.neardeal28.api_responses.DealResponse;
import com.asic45.neardeal28.api_responses.LoginResponse;
import com.asic45.neardeal28.api_responses.ProductDetailResponse;
import com.asic45.neardeal28.api_responses.ProductResponse;
import com.asic45.neardeal28.api_responses.StoreResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiEndPoint {


    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(@Field("username")String username,
                              @Field("password")String password);

    @GET("get_store.php")
    Call<StoreResponse> getStore();

    @GET("get_store_by_loc.php")
    Call<StoreResponse> getStoreByLoc(@Query("lat") double lat, @Query("lng") double lng);


    @GET("get_product.php")
    Call<ProductResponse> getProduct(@Query("store_id")String id);

    @GET("get_product_detail.php")
    Call<ProductDetailResponse> getProductDetail(@Query("product_id")String productId);

    @GET("get_deal.php")
    Call<DealResponse> getDeal(@Query("store_id")String id);

    @FormUrlEncoded
    @POST("checkout.php")
    Call<CheckoutResponse> checkout(@Field("user_Ids")String userIds,
                                    @Field("Name")String name,
                                    @Field("No_hp")String no_hp,
                                    @Field("Address")String address,
                                    @Field("product_Ids")String product_Ids,
                                    @Field("price")String price);

}
