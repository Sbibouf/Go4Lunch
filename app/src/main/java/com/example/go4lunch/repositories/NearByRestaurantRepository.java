package com.example.go4lunch.repositories;

import static com.example.go4lunch.BuildConfig.MAPS_API_KEY;

import android.location.Location;
import android.util.Log;

import com.example.go4lunch.model.RestaurantDetail;
import com.example.go4lunch.pojo.restaurantDetail.RestaurantDetailApi;
import com.example.go4lunch.pojo.restaurantDetail.PhotoDetail;
import com.example.go4lunch.pojo.restaurants.NearbyRestaurant;
import com.example.go4lunch.pojo.restaurants.OpeningHours;
import com.example.go4lunch.pojo.restaurants.Photo;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.service.DetailRestaurantCallback;
import com.example.go4lunch.service.RestaurantApi;
import com.example.go4lunch.service.RestaurantCallback;
import com.example.go4lunch.service.RetrofitService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Repository that makes API calls to get restaurants datas
public class NearByRestaurantRepository {

    //Variables
    private final RestaurantApi mRestaurantApi;
    private static volatile NearByRestaurantRepository instance;
    private List<Restaurant> mRestaurantList = new ArrayList<>();
    private RestaurantDetail mRestaurantDetail;


    // Constructor

    public NearByRestaurantRepository(){
        mRestaurantApi = RetrofitService.getInterface();

    }

    //Singleton

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

    //Methods that make API calls

    //Get all nearby restaurant depending on latitude and longitude location

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
                        int rating = restaurants.getResults().get(i).getRating().intValue();


                        Restaurant restaurant = new Restaurant(restaurantLat,restaurantLong,restaurantName,restaurantID, restaurantAddress, restaurantIsOpen, distanceRestaurantString, photoUrl, rating);
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

    //Get all detail from one restaurant

    public void getDetailRestaurantFromApi(String placeId, DetailRestaurantCallback callback){

        Call<RestaurantDetailApi> detailRestaurantCall = mRestaurantApi.getRestaurantDetail(""+placeId,MAPS_API_KEY);
        String url = detailRestaurantCall.request().url().toString();
        Log.d("DetailRestaurantCall", "Request URL: " + url);
        detailRestaurantCall.enqueue(new Callback<RestaurantDetailApi>() {
            @Override
            public void onResponse(Call<RestaurantDetailApi> call, Response<RestaurantDetailApi> response) {
                RestaurantDetailApi restaurantDetailApi = response.body();
                String name = restaurantDetailApi.getResult().getName();
                String id = restaurantDetailApi.getResult().getPlace_id();
                String phone = "";
                String website = "";
                String address = restaurantDetailApi.getResult().getVicinity();
                List<PhotoDetail> photoRef = new ArrayList<>();
                String photoReference ="";
                String photoUrl = "";
                photoRef = restaurantDetailApi.getResult().getPhotos();
                if(restaurantDetailApi.getResult().getInternationalPhoneNumber()!=null){
                    phone = restaurantDetailApi.getResult().getInternationalPhoneNumber();
                }else if (restaurantDetailApi.getResult().getWebsite()!=null){
                    website = restaurantDetailApi.getResult().getWebsite();
                }
                if(photoRef!=null){
                    photoReference = photoRef.get(0).getPhotoReference();
                    photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+ photoReference + "&key="+MAPS_API_KEY;
                }


                float rating = restaurantDetailApi.getResult().getRating();
                mRestaurantDetail = new RestaurantDetail(name, phone, rating, website, id, address, photoUrl);
                callback.OnRestaurantDetailReceived(mRestaurantDetail);
            }

            @Override
            public void onFailure(Call<RestaurantDetailApi> call, Throwable t) {

                Log.e("DetailRestaurantCall", "Call failed: " + t.getMessage());
            }
        });

    }

}
