package com.example.go4lunch.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//Retrofit service class that defines element for API call
public class RetrofitService {

    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static RestaurantApi getInterface() {
        return retrofit.create(RestaurantApi.class);
    }
}
