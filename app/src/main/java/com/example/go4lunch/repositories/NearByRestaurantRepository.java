package com.example.go4lunch.repositories;

import static com.example.go4lunch.BuildConfig.MAPS_API_KEY;

import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Pojo.NearbyRestaurant;
import com.example.go4lunch.Pojo.OpeningHours;
import com.example.go4lunch.Pojo.Photo;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.service.RestaurantApi;
import com.example.go4lunch.service.RestaurantCallback;
import com.example.go4lunch.service.RetrofitService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import javax.inject.Inject;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NearByRestaurantRepository {

    private final RestaurantApi mRestaurantApi;
    private static volatile NearByRestaurantRepository instance;
    private List<Restaurant> mRestaurantList = new ArrayList<>();


    // Constructor

    public NearByRestaurantRepository(){
        mRestaurantApi = RetrofitService.getInterface();

    }

    public static NearByRestaurantRepository getInstance() {
        NearByRestaurantRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(NearByRestaurantRepository.class) {
            if (instance == null) {
                instance = new NearByRestaurantRepository();
            }
            return instance;
        }
    }

    public void getNearbyRestaurantFromApi(double lat, double longi, RestaurantCallback callback){

        Location currentLocation = new Location("");
        Location restaurantLocation = new Location("");
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(longi);

        Call<NearbyRestaurant> nearbyRestaurantCall = mRestaurantApi.getRestaurants(""+lat+","+ longi, "distance","cafe", MAPS_API_KEY);
        nearbyRestaurantCall.enqueue(new Callback<NearbyRestaurant>() {
            @Override
            public void onResponse(Call<NearbyRestaurant> call, Response<NearbyRestaurant> response) {

                if (response.isSuccessful()) {
                    NearbyRestaurant restaurants = response.body();

                    for(int i =0; i < restaurants.getResults().size(); i++) {

                        double restaurantLat = restaurants.getResults().get(i).getGeometry().getLocation().getLat();
                        double restaurantLong = restaurants.getResults().get(i).getGeometry().getLocation().getLng();
                        restaurantLocation.setLatitude(restaurantLat);
                        restaurantLocation.setLongitude(restaurantLong);
                        List<Photo> photoRef = new ArrayList<>();
                        String photoReference ="";
                        String photoUrl = "";
                        photoRef = restaurants.getResults().get(i).getPhotos();
                        OpeningHours openingHours = restaurants.getResults().get(i).getOpeningHours();
                        Boolean restaurantIsOpen = true;
                        if(photoRef!=null){
                            photoReference = photoRef.get(0).getPhotoReference();
                            photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ photoReference + "&key="+MAPS_API_KEY;
                        }
                        if(openingHours!=null){
                            restaurantIsOpen = restaurants.getResults().get(i).getOpeningHours().getOpenNow();
                        }
                        String restaurantName = restaurants.getResults().get(i).getName();
                        String restaurantID = restaurants.getResults().get(i).getPlaceId();
                        String restaurantAddress = restaurants.getResults().get(i).getVicinity();
                        float distanceRestaurant = currentLocation.distanceTo(restaurantLocation);
                        DecimalFormat decimalFormat = new DecimalFormat("#");
                        String distanceRestaurantString = decimalFormat.format(distanceRestaurant);


                        Restaurant restaurant = new Restaurant(restaurantLat,restaurantLong,restaurantName,restaurantID, restaurantAddress, restaurantIsOpen, distanceRestaurantString, photoUrl);
                        mRestaurantList.add(restaurant);


                    }
                }
                callback.OnRestaurantListReceived(mRestaurantList);
            }

            @Override
            public void onFailure(Call<NearbyRestaurant> call, Throwable t) {

            }
        });
    }

}
