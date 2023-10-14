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
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DetailRestaurantViewModel extends ViewModel {

    NearByRestaurantRepository mNearByRestaurantRepository;
    UserRepository mUserRepository;
    MutableLiveData<DetailRestaurant> mMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<User>> mListMutableLiveData = new MutableLiveData<>();

    public DetailRestaurantViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
        mUserRepository = UserRepository.getInstance();

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

    public void updateUserChoiceId(String placeId){
        mUserRepository.updateUserChoiceId(placeId);
    }
    public void updateUserChoice(String placeName){
        mUserRepository.updateUserChoice(placeName);
    }

    public FirebaseUser getCurrentUser(){
        return mUserRepository.getCurrentUser();
    }



}
