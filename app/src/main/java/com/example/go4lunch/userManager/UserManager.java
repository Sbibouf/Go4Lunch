package com.example.go4lunch.userManager;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserManager {

    private static volatile UserManager instance;
    private UserRepository userRepository;
    private MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();

    private UserManager() {
        userRepository = UserRepository.getInstance();
    }

    public static UserManager getInstance() {
        UserManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserRepository.class) {
            if (instance == null) {
                instance = new UserManager();
            }
            return instance;
        }
    }

    public void createUser(){
        userRepository.createUser();
    }

    public Task<User> getUserData(){
        // Get the user from Firestore and cast it to a User model Object
        return userRepository.getUserData().continueWith(task -> task.getResult().toObject(User.class)) ;
    }

    public Task<Void> updateUsername(String username){
        return userRepository.updateUsername(username);
    }

    public Task<Void> updateUserChoice(String placeName){
        return userRepository.updateUserChoice(placeName);
    }

    public Task<Void> updateUserChoiceId(String placeId){
        return userRepository.updateUserChoiceId(placeId);
    }
    public Task<Void> addLikedRestaurant(String placeName){
        return userRepository.updateUserLikedRestaurant(placeName);
    }

    public Boolean isCurrentUserLogged() {
        return (userRepository.getCurrentUser()!=null);
    }

    public FirebaseUser getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public Task<Void> signOut(Context context){
        return userRepository.signOut(context);
    }

    public Task<Void> deleteUser(Context context){
        return userRepository.deleteUser(context);
    }

    public Task<List<User>> getAllUsers(){return userRepository.getAllUsers();}

    public Task<List<User>> getRestaurantUsers(String placeId){return userRepository.getRestaurantUsers(placeId);}

    public MutableLiveData<User> getUserMutableLiveData(){
        return mUserMutableLiveData;
    }
}
