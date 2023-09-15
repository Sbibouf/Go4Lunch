package com.example.go4lunch.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "WorkMates")
public class WorkMates {

    /**
     * Variables name, lastName, avatar
     */
    private String name, lastName, avatar, choice;

    @PrimaryKey
    private Long id;

    /**
     * Constructor
     */

    public WorkMates(String name, String lastName, String avatar, String choice, Long id) {
        this.name = name;
        this.lastName = lastName;
        this.avatar = avatar;
        this.choice = choice;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getChoice(){
        return choice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
