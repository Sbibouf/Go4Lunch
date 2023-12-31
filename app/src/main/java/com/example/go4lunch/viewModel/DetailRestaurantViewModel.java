package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.RestaurantDetail;
import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.example.go4lunch.repositories.UserRepository;
import com.example.go4lunch.service.DetailRestaurantCallback;
import com.example.go4lunch.userManager.UserManager;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DetailRestaurantViewModel extends ViewModel {

    NearByRestaurantRepository mNearByRestaurantRepository;
    private final UserManager mUserManager;
    MutableLiveData<RestaurantDetail> mMutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> mListMutableLiveData = new MutableLiveData<>();

    public DetailRestaurantViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
        mUserManager = UserManager.getInstance();

    }

    public void getRestaurantUser(String placeId){
        mUserManager.getRestaurantUser(placeId, mListMutableLiveData);
    }

    public MutableLiveData<List<User>> getmListLiveData(){
        return mListMutableLiveData;
    }

    public void fetchDatas(String placeId){
        mNearByRestaurantRepository.getDetailRestaurantFromApi(placeId, new DetailRestaurantCallback() {
            @Override
            public void OnRestaurantDetailReceived(RestaurantDetail restaurantDetail) {
                mMutableLiveData.setValue(restaurantDetail);

            }
        });
    }

    public MutableLiveData<RestaurantDetail> getMutableLiveData(){
        return mMutableLiveData;
    }

    public MutableLiveData<User> getUserMutableLiveData() {
        return mUserMutableLiveData;
    }

    public void updateUserChoiceId(String placeId){
        mUserManager.updateUserChoiceId(placeId);
    }
    public void updateUserChoice(String placeName){
        mUserManager.updateUserChoice(placeName);
    }
    public void getCurrentUser(){mUserManager.getUserData().addOnSuccessListener(user -> {
        mUserMutableLiveData.setValue(user);
    });}
    public void addLikedRestaurants(String placeName){mUserManager.addLikedRestaurant(placeName);}

}
