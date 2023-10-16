package com.example.go4lunch.service;

import com.example.go4lunch.model.RestaurantDetail;
//Callback interface for restaurant detail
public interface DetailRestaurantCallback {
    void OnRestaurantDetailReceived(RestaurantDetail restaurantDetail);
}
