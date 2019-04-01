package id.co.rumahcoding.neardeal.api_services;

import id.co.rumahcoding.neardeal.responses.StoreResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndPoint {
    @GET("get_store.php")
    Call<StoreResponse> getStore();
}
