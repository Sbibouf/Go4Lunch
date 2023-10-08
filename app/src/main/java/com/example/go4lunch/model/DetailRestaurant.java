package com.example.go4lunch.model;

public class DetailRestaurant {

    String name, phone_number;
    int rating;

    public DetailRestaurant(String name, String phone_number, int rating) {
        this.name = name;
        this.phone_number = phone_number;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
