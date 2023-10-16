package com.example.go4lunch.service;


import com.example.go4lunch.pojo.restaurantDetail.RestaurantDetailApi;
import com.example.go4lunch.pojo.restaurants.NearbyRestaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//Interface that is linked retrofit service class for API calls
public interface RestaurantApi {

    @GET("maps/api/place/nearbysearch/json?")
    Call<NearbyRestaurant> getRestaurants(@Query("location") String location,
                                          @Query("rankby") String rankby,
                                          @Query("type") String type, // "restaurant"
                                          @Query("key") String apiKey);

    @GET("maps/api/place/details/json?fields=name%2Crating%2Cinternational_phone_number%2Cwebsite%2Cvicinity%2Cphotos")
    Call<RestaurantDetailApi> getRestaurantDetail(@Query("place_id") String placeId,
                                                  @Query("key") String apiKey);
}

