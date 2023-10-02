package com.example.go4lunch.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.go4lunch.model.User;

import java.util.List;

@Dao
public interface WorkMatesDao {

    @Query("SELECT * FROM User WHERE id = :WorkMatesId")
    LiveData<User> getWorkMatesById(Long WorkMatesId);


    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllWorkMates();







}
