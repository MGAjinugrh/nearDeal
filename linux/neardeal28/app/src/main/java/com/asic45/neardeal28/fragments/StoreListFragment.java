package com.asic45.neardeal28.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asic45.neardeal28.R;
import com.asic45.neardeal28.adapters.StoreItemAdapter;
import com.asic45.neardeal28.api_responses.Store;
import com.asic45.neardeal28.api_responses.StoreResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;
import com.asic45.neardeal28.utils.ConnectivityUtil;
import com.asic45.neardeal28.utils.PopupUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreListFragment extends Fragment {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_list, container, false);
        catchLocation();
        RecyclerView recyclerView = view.findViewById(R.id.rv_store);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mStoreList = new ArrayList<>();
        mAdapter = new StoreItemAdapter(getActivity(), mStoreList);
        //mAdapter.setListener(this);
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

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
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
}