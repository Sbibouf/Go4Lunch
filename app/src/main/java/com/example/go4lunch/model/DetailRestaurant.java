package com.example.go4lunch.model;

import java.util.ArrayList;
import java.util.List;

public class DetailRestaurant {

    String name, phone_number, website, address, photoUrl;
    float rating;
    List<User> users;
    String id;

    public DetailRestaurant(String name, String phone_number, float rating, String website, String id, String address, String photoUrl) {
        this.name = name;
        this.phone_number = phone_number;
        this.rating = rating;
        this.website = website;
        this.id = id;
        this.address=address;
        this.photoUrl = photoUrl;
        users = new ArrayList<>();
    }

    public DetailRestaurant() {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
