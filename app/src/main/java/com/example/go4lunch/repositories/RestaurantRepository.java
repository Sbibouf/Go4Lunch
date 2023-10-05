package com.example.go4lunch.repositories;

import com.example.go4lunch.model.Restaurant;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RestaurantRepository {

    private static final String COLLECTION_NAME = "restaurants";
    private static volatile RestaurantRepository instance;


    //Constructor

    private RestaurantRepository(){}


    // Singleton

    public static RestaurantRepository getInstance(){
        RestaurantRepository result = instance;
        if(result!=null){
            return result;
        }
        synchronized (RestaurantRepository.class){
            if(instance==null){
                instance = new RestaurantRepository();
            }
            return instance;
        }

    }


    // Get the collection Reference
    private CollectionReference getRestaurantsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


    // Create Restaurant in Firestore

    public void createRestaurant(double latitude, double longitude, String name, String id){
        Restaurant resto = new Restaurant(latitude,longitude,name, id);
        this.getRestaurantsCollection().document(id).set(resto);


    }

}
