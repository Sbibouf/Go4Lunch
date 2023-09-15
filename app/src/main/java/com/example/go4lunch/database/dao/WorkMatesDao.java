package com.example.go4lunch.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.go4lunch.model.WorkMates;

import java.util.List;

@Dao
public interface WorkMatesDao {

    @Query("SELECT * FROM WorkMates WHERE id = :WorkMatesId")
    LiveData<WorkMates> getWorkMatesById(Long WorkMatesId);


    @Query("SELECT * FROM WorkMates")
    LiveData<List<WorkMates>> getAllWorkMates();







}
