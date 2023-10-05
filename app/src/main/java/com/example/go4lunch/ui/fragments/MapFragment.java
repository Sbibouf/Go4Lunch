package com.example.go4lunch.ui.fragments;

import static com.example.go4lunch.BuildConfig.MAPS_API_KEY;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.repositories.RestaurantRepository;
import com.example.go4lunch.ui.DashboardActivity;
import com.example.go4lunch.viewModel.NearByRestaurantViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;

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
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear();

                try {
                    if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);



                    }
                    else{
                        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        mMap.setMyLocationEnabled(true);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    mNearByRestaurantViewModel = new ViewModelProvider(requireActivity()).get(NearByRestaurantViewModel.class);
                    mNearByRestaurantViewModel.getNearByRestaurantFromFirestore(latitude,longitude);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                mNearByRestaurantViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), restaurants -> {
                    for(int i=0; i<restaurants.size(); i++ ){
                        LatLng latLng = new LatLng(restaurants.get(i).getLatitude(), restaurants.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                });

                LatLng currentPosition = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
                mMap.setMinZoomPreference(14);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }


}