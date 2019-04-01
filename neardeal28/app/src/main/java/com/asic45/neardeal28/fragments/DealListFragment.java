package com.asic45.neardeal28.fragments;

import android.content.Intent;
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
import com.asic45.neardeal28.adapters.DealItemAdapter;
import com.asic45.neardeal28.api_responses.Deal;
import com.asic45.neardeal28.api_responses.DealResponse;
import com.asic45.neardeal28.api_services.ApiClient;
import com.asic45.neardeal28.api_services.ApiEndPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealListFragment extends Fragment implements DealItemAdapter.OnItemClickListener {
    public static  final  String KEY_STORE_ID = "ID Toko";
    String mStoreId;
    List<Deal> mDealList;
    DealItemAdapter mDealItemAdapter;
    public DealListFragment() {
        Bundle argument = getArguments();

        if(argument != null){
            mStoreId = argument.getString(KEY_STORE_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deal_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        mStoreId=getArguments().getString(KEY_STORE_ID);

        mDealList = new ArrayList<>();
        mDealItemAdapter = new DealItemAdapter(getActivity(),mDealList);
        mDealItemAdapter.setListener(this);
        recyclerView.setAdapter(mDealItemAdapter);
        loadDeals();
        // Inflate the layout for this fragment
        return view;
    }

    private void loadDeals(){
        ApiEndPoint apiEndPoint = ApiClient.getClient(getActivity()).create(ApiEndPoint.class);
        Call<DealResponse> call = apiEndPoint.getDeal(mStoreId);

        call.enqueue(new Callback<DealResponse>() {
            @Override
            public void onResponse(Call<DealResponse> call, Response<DealResponse> response) {
                DealResponse dealResponse = response.body();

                if (dealResponse != null){
                    if (dealResponse.getSuccess()){
                        Log.d("StoreListFragment", "Jumlah store:" + dealResponse.getDeal().size());
                        mDealList.addAll(dealResponse.getDeal());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDealItemAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                else {
                    Log.d("StoreListFragment", "response is null");
                }
            }

            @Override
            public void onFailure(Call<DealResponse> call, Throwable t) {
                System.out.println("Failed");
            }
        });

    }

    @Override
    public void onItemClick(String productId, int newPrice) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product_id", productId);
        System.out.println("Deal Fragment Product Id : "+productId);
        intent.putExtra("new_price", newPrice);
        System.out.println("Deal Fragment Product newPrice : "+newPrice);
        startActivity(intent);
    }

}