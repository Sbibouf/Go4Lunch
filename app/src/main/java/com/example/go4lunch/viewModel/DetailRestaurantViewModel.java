package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.example.go4lunch.repositories.UserRepository;
import com.example.go4lunch.service.DetailRestaurantCallback;
import com.example.go4lunch.service.RestaurantCallback;
import com.example.go4lunch.userManager.UserManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DetailRestaurantViewModel extends ViewModel {

    NearByRestaurantRepository mNearByRestaurantRepository;
    private final UserManager mUserManager;
    private final UserRepository mUserRepository;
    MutableLiveData<DetailRestaurant> mMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<User>> mListMutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();

    public DetailRestaurantViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
        mUserManager = UserManager.getInstance();
        mUserRepository= UserRepository.getInstance();

    }

    public void fetchDatas(String placeId){
        mNearByRestaurantRepository.getDetailRestaurantFromApi(placeId, new DetailRestaurantCallback() {
            @Override
            public void OnRestaurantDetailReceived(DetailRestaurant detailRestaurant) {
                mMutableLiveData.setValue(detailRestaurant);

            }
        });
    }

    public void fetchRestaurantUsers(String placeId){
        mUserRepository.getRestaurantUsers(placeId).addOnSuccessListener(users -> {
            mListMutableLiveData.setValue(users);
        }).addOnFailureListener(e -> {});
    }


    public MutableLiveData<DetailRestaurant> getMutableLiveData(){
        return mMutableLiveData;
    }
    public MutableLiveData<List<User>> getListMutableLiveData(){return mListMutableLiveData;}

    public MutableLiveData<User> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public void updateUserChoiceId(String placeId){
        mUserManager.updateUserChoiceId(placeId);
    }
    public void updateUserChoice(String placeName){
        mUserManager.updateUserChoice(placeName);
    }
    public void getCurrentUserData(){mUserManager.getUserData().addOnSuccessListener(user -> {
        mUserMutableLiveData.setValue(user);
    });}
    public void addLikedRestaurants(String placeName){mUserManager.addLikedRestaurant(placeName);}

    public FirebaseUser getCurrentUser(){
        return mUserRepository.getCurrentUser();
    }



}
