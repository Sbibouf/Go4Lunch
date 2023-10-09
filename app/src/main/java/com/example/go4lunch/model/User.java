package com.example.go4lunch.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


public class User implements Serializable {

    /**
     * Variables name, lastName, avatar
     */
    private String name, avatar, choice, email, id, choiceId;



    /**
     * Constructors
     */

    public User(String id, String name, String avatar, String email ) {
        this.name = name;
        this.avatar = avatar;
        this.id = id;
        this.email = email;
        choice = "";
        choiceId = "";

    }

    public User() {
    }

    /**
     * Getters and setters
     */


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }
}
