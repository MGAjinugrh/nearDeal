package com.asic45.neardeal28.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asic45.neardeal28.ProductDetailActivity;
import com.asic45.neardeal28.R;
import com.asic45.neardeal28.adapters.ProductItemAdapter;
import com.asic45.neardeal28.api_responses.Product;
import com.asic45.neardeal28.api_responses.ProductDetail;
import com.asic45.neardeal28.api_responses.ProductResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductListFragment extends Fragment {
    public static  final  String KEY_STORE_ID = "ID Toko";
    String mStoreId;
    List<Product> mProductList;
    ProductItemAdapter mProductItemAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        mStoreId=getArguments().getString(KEY_STORE_ID);

        mProductList = new ArrayList<>();
        mProductItemAdapter = new ProductItemAdapter(getActivity(),mProductList);
        recyclerView.setAdapter(mProductItemAdapter);
        loadProducts();
        // Inflate the layout for this fragment
        return view;
    }

    private void loadProducts(){
        ApiEndPoint apiEndPoint = ApiClient.getClient(getActivity()).create(ApiEndPoint.class);
        Call<ProductResponse> call = apiEndPoint.getProduct(mStoreId);

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse productResponse = response.body();

                if (productResponse != null){
                    if (productResponse.getSuccess()){
                        Log.d("StoreListFragment", "Jumlah store:" + productResponse.getProduct().size());
                        mProductList.addAll(productResponse.getProduct());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mProductItemAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("StoreListFragment", "response is null");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                System.out.println("Failed");
            }
        });

    }


}
