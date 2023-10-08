package com.example.go4lunch.service;


import com.example.go4lunch.pojo.detailRestaurant.DetailRestaurantApi;
import com.example.go4lunch.pojo.restaurants.NearbyRestaurant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantApi {

    @GET("maps/api/place/nearbysearch/json?")
    Call<NearbyRestaurant> getRestaurants(@Query("location") String location,
                                          @Query("rankby") String rankby,
                                          @Query("type") String type, // "restaurant"
                                          @Query("key") String apiKey);

    @GET("maps/api/place/detail/json?fields=name%2Crating%2Cformatted_phone_number%2Cwebsite")
    Call<DetailRestaurantApi> getRestaurantDetail(@Query("placeId") String placeId,
                                                  @Query("key") String apiKey);
}

