package com.example.go4lunch.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.viewModel.NearByRestaurantViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private GoogleMap mMap;
    LocationManager mLocationManager;
    double latitude, longitude;
    Location location;
    NearByRestaurantViewModel mNearByRestaurantViewModel;

    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

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

                mNearByRestaurantViewModel = new ViewModelProvider(requireActivity()).get(NearByRestaurantViewModel.class);
                mNearByRestaurantViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), restaurants -> {
                    for(int i=0; i<restaurants.size(); i++ ){
                        LatLng latLng = new LatLng(restaurants.get(i).getLatitude(), restaurants.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng));
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


}