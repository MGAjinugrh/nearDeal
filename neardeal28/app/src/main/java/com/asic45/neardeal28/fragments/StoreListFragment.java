package com.asic45.neardeal28.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asic45.neardeal28.MapsActivity;
import com.asic45.neardeal28.ProductActivity;
import com.asic45.neardeal28.R;
import com.asic45.neardeal28.adapters.StoreItemAdapter;
import com.asic45.neardeal28.api_responses.Product;
import com.asic45.neardeal28.api_responses.Store;
import com.asic45.neardeal28.api_responses.StoreResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;
import com.asic45.neardeal28.utils.ConnectivityUtil;
import com.asic45.neardeal28.utils.PopupUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreListFragment extends Fragment implements StoreItemAdapter.OnItemClickListener {
    public static  final  String KEY_LAT = "lat";
    public static  final  String KEY_LNG = "lng";
    private StoreItemAdapter mAdapter;
    private List<Store> mStoreList;



    double mLat,mLng;

    public StoreListFragment() {
        // Required empty public constructor
    }

    private void catchLocation(){
        Bundle argumennt = getArguments();

        if (argumennt != null){
            mLat = argumennt.getDouble(KEY_LAT);
            mLng = argumennt.getDouble(KEY_LNG);
        }
    }

    private String storeToString(){
        Gson gson = new Gson();
        Type listOfObject = new TypeToken<List<Store>>(){}.getType();
        String list = gson.toJson(mStoreList,listOfObject);
        return list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_list, container, false);
        catchLocation();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra(StoreListFragment.KEY_LAT, mLat);
                intent.putExtra(StoreListFragment.KEY_LNG, mLng);
                intent.putExtra("data",storeToString());
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_store);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mStoreList = new ArrayList<>();
        mAdapter = new StoreItemAdapter(getActivity(), mStoreList);
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);

        if(ConnectivityUtil.isConnected(getActivity())){
            loadStores();
        }
        else{
            PopupUtil.showMsg(getActivity(), "No Internet connection", PopupUtil.SHORT);
        }

        return view;
    }

    private void loadStores(){
        PopupUtil.showLoading(getActivity(), "", "Loading stores....");

        System.out.println("Lat :"+mLat);
        System.out.println("Lng :"+mLng);

        ApiEndPoint apiEndPoint = ApiClient.getClient(getActivity()).create(ApiEndPoint.class);
        Call<StoreResponse> call = apiEndPoint.getStoreByLoc(mLat,mLng);

        call.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                PopupUtil.dismissDialog();
                StoreResponse storeResponse = response.body();

                if (storeResponse != null){
                    if (storeResponse.getSuccess()){
                        Log.d("StoreListFragment", "Jumlah store:" + storeResponse.getStore().size());
                        mStoreList.addAll(storeResponse.getStore());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("StoreListFragment", "response is null");
                }
            }

            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                PopupUtil.dismissDialog();

            }
        });

    }

    @Override
    public void onItemClick(String storeId) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra(ProductListFragment.KEY_STORE_ID,storeId);
        System.out.println("Store Id "+storeId);
        startActivity(intent);
    }
}