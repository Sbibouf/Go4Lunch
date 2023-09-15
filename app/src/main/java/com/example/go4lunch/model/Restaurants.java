package com.example.go4lunch.model;

public class Restaurants {

    /**
     * Variable name, adress, type, grade
     */
    private String name, adress, type, grade;

    private int id;

    /**
     * Constructor
     */

    public Restaurants(String name, String adress, String type, String grade, int id) {
        this.name = name;
        this.adress = adress;
        this.type = type;
        this.grade = grade;
        this.id = id;
    }

    /**
     * Getters and setters
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
