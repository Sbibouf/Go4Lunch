package com.example.go4lunch.service;

import com.example.go4lunch.model.Restaurant;

import java.util.List;

public interface RestaurantCallback {
    void OnRestaurantListReceived(List<Restaurant>restaurantList);
}
