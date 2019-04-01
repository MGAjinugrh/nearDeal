package id.co.rumahcoding.neardeal.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.rumahcoding.neardeal.R;
import id.co.rumahcoding.neardeal.adapters.StoreItemAdapter;
import id.co.rumahcoding.neardeal.api_services.ApiClient;
import id.co.rumahcoding.neardeal.api_services.ApiEndPoint;
import id.co.rumahcoding.neardeal.responses.Store;
import id.co.rumahcoding.neardeal.responses.StoreResponse;
import id.co.rumahcoding.neardeal.utils.ConnectivityUtil;
import id.co.rumahcoding.neardeal.utils.PopupUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreListFragment extends Fragment {
    public static  final  String KEY_LAT = "lat";
    public static  final  String KEY_LNG = "lng";
    private StoreItemAdapter mAdapter;
    private List<Store> mStoreList;

    public StoreListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv);
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

        ApiEndPoint apiEndPoint = ApiClient.getClient().create(ApiEndPoint.class);
        Call<StoreResponse> call = apiEndPoint.getStore();

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
                PopupUtil.showMsg(getActivity(),"Error", Toast.LENGTH_SHORT);

            }
        });

    }
}