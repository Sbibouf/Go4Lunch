package com.example.go4lunch.repositories;

import static com.example.go4lunch.BuildConfig.MAPS_API_KEY;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Pojo.NearbyRestaurant;
import com.example.go4lunch.service.RestaurantApi;
import com.example.go4lunch.service.RetrofitService;
import com.google.android.gms.maps.model.LatLng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearByRestaurantRepository {

    private final RestaurantApi mRestaurantApi;
    private final RestaurantRepository mRestaurantRepository;
    private final MutableLiveData<NearbyRestaurant> mNearbyRestaurantMutableLiveData = new MutableLiveData<>();
    private static volatile NearByRestaurantRepository instance;


    // Constructor

    public NearByRestaurantRepository(){
        mRestaurantApi = RetrofitService.getInterface();
        mRestaurantRepository = RestaurantRepository.getInstance();
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

    public void getNearbyRestaurantFromApi(double lat, double longi){
        Call<NearbyRestaurant> nearbyRestaurantCall = mRestaurantApi.getRestaurants(""+lat+","+ longi, 2000,"restaurant", MAPS_API_KEY);
        nearbyRestaurantCall.enqueue(new Callback<NearbyRestaurant>() {
            @Override
            public void onResponse(Call<NearbyRestaurant> call, Response<NearbyRestaurant> response) {

                if (response.isSuccessful()) {
                    NearbyRestaurant restaurants = response.body();

                    for(int i =0; i < restaurants.getResults().size(); i++) {

                        double restaurantLat = restaurants.getResults().get(i).getGeometry().getLocation().getLat();
                        double restaurantLong = restaurants.getResults().get(i).getGeometry().getLocation().getLng();
                        String restaurantName = restaurants.getResults().get(i).getName();
                        String restaurantID = restaurants.getResults().get(i).getPlaceId();

                        mRestaurantRepository.createRestaurant(restaurantLat, restaurantLong, restaurantName, restaurantID);

                    }
                }
            }

            @Override
            public void onFailure(Call<NearbyRestaurant> call, Throwable t) {

                mNearbyRestaurantMutableLiveData.postValue(null);
            }
        });
    }
}
