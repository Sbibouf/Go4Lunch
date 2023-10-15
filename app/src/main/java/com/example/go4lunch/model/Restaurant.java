package com.example.go4lunch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable {

    /**
     * Variable name, adress, type, grade
     */
    private String name, id, address, distance, photoUrl;
    private double latitude, longitude;
    private int rating;
    private Boolean isOpen;
    private int custumersNumber;



    //Constructor

    public Restaurant(double latitude, double longitude, String name, String id, String address, Boolean isOpen, String distance, String photoUrl, int rating) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.id = id;
        this.address = address;
        this.isOpen = isOpen;
        this.distance = distance;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.custumersNumber = 0;
    }

    public Restaurant(){

    }


    // Getters and setters


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public int getCustumersNumber() {
        return custumersNumber;
    }

    public void setCustumersNumber(int custumersNumber) {
        this.custumersNumber = custumersNumber;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
