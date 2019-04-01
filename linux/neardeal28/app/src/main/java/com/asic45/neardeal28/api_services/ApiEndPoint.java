package com.asic45.neardeal28.api_services;

import com.asic45.neardeal28.api_responses.StoreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiEndPoint {


    @GET("get_store.php")
    Call<StoreResponse> getStore();

    @GET("get_store_by_loc.php")
    Call<StoreResponse> getStoreByLoc(@Query("lat") double lat, @Query("lng") double lng);
}
