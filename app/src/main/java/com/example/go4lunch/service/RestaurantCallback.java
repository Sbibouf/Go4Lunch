package com.example.go4lunch.service;

import com.example.go4lunch.model.Restaurant;

import java.util.List;
//Callback interface for the list of restaurant
public interface RestaurantCallback {
    void OnRestaurantListReceived(List<Restaurant>restaurantList);
}
