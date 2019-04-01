package com.asic45.neardeal28;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.asic45.neardeal28.api_responses.Store;
import com.asic45.neardeal28.fragments.StoreListFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double lat,lng;

    private void catchLocation(){
        lat =  getIntent().getDoubleExtra(StoreListFragment.KEY_LAT,0);
        lng =  getIntent().getDoubleExtra(StoreListFragment.KEY_LNG,0);
    }

    private void drawStore(){
        for (Store store:storeList()){
            addStoreMarker(store);
        }
    }

    private void addStoreMarker(Store store){
        LatLng storeLocation = new LatLng(store.getLat(), store.getLng());
        mMap.addMarker(new MarkerOptions().position(storeLocation).title(store.getName()))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.store));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15f));
    }

    private List<Store> storeList() {
        Gson gson = new Gson();
        Type listOfObject = new TypeToken<List<Store>>() {
        }.getType();
        String list = getIntent().getStringExtra("data");
        return gson.fromJson(list, listOfObject);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        catchLocation();
        drawStore();
        // Add a marker in Sydney and move the camera
        LatLng myLat = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(myLat).title("You are here."));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLat,15f));

    }
}
