package com.example.go4lunch.model;

public class Restaurant {

    /**
     * Variable name, adress, type, grade
     */
    private String name, id;
    private double latitude;
    private double longitude;



    //Constructor

    public Restaurant(double latitude, double longitude, String name, String id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.id = id;
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
}
