package com.example.go4lunch.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.viewModel.DashboardListMapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//A fragment that shares the data of the list fragment and display a google map with colored pins
public class MapFragment extends Fragment {

    //Variables
    private GoogleMap mMap;
    DashboardListMapViewModel mDashboardListMapViewModel;


    //Constructor
    public MapFragment() {
        // Required empty public constructor
    }


    //New Instance
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }


    //Override methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear();
                mMap.setMyLocationEnabled(true);

                mDashboardListMapViewModel = new ViewModelProvider(requireActivity()).get(DashboardListMapViewModel.class);
                mDashboardListMapViewModel.getListRestaurantLiveData().observe(getViewLifecycleOwner(), restaurants -> {
                    for(int i=0; i<restaurants.size(); i++ ){
                        LatLng latLng = new LatLng(restaurants.get(i).getLatitude(), restaurants.get(i).getLongitude());
                        if(restaurants.get(i).getCustumersNumber()==0){
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(restaurants.get(i).getName())
                                    .snippet(restaurants.get(i).getAddress())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_vide)));
                        }
                        else{
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(restaurants.get(i).getName())
                                    .snippet(restaurants.get(i).getAddress())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_plein)));
                        }

                    }
                    LatLng startPoint = new LatLng(restaurants.get(0).getLatitude(), restaurants.get(0).getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));
                });

                mMap.setMinZoomPreference(16);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("I'm Hungry!");
    }

    public void updateMapOnSearch(LatLng latLng, String title){
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                        .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_vide)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
    }
}