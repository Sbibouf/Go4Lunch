package com.example.go4lunch.service;

import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.model.Restaurant;

import java.util.List;

public interface DetailRestaurantCallback {
    void OnRestaurantDetailReceived(DetailRestaurant detailRestaurant);
}
