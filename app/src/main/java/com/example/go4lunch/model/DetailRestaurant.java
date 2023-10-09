package com.example.go4lunch.model;

public class DetailRestaurant {

    String name, phone_number, website;
    float rating;

    public DetailRestaurant(String name, String phone_number, float rating, String website) {
        this.name = name;
        this.phone_number = phone_number;
        this.rating = rating;
        this.website= website;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
