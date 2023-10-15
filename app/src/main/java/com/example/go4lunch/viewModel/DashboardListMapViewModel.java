package com.example.go4lunch.viewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.example.go4lunch.service.RestaurantCallback;
import com.example.go4lunch.userManager.UserManager;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class DashboardListMapViewModel extends ViewModel {

    private final NearByRestaurantRepository mNearByRestaurantRepository;
    private final UserManager mUserManager;
    private MutableLiveData<User> mUserMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Restaurant>> mMutableLiveData = new MutableLiveData<>();
    private List<Restaurant> mRestaurantList = new ArrayList<>();
    private List<User>  mUserList = new ArrayList<>();

    public MutableLiveData<User> getUserMutableLiveData(){
        return mUserMutableLiveData;
    }

    public DashboardListMapViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
        mUserManager = UserManager.getInstance();
        fetchCurrentUser();
        fetchUsers();
    }

    public void fetchCurrentUser(){

        mUserManager.getUserData().addOnSuccessListener(user -> {
            mUserMutableLiveData.setValue(user);
        });
    }

    public Task<Void> signOut(Context context){
        return mUserManager.signOut(context);
    }

    public MutableLiveData<List<Restaurant>> getMutableLiveData(){
        return mMutableLiveData;
    }

    public void fetchData(double lat, double longi){
        mNearByRestaurantRepository.getNearbyRestaurantFromApi(lat, longi, new RestaurantCallback() {
            @Override
            public void OnRestaurantListReceived(List<Restaurant> restaurantList) {
                mMutableLiveData.setValue(restaurantList);
                mRestaurantList = restaurantList;
            }
        });

    }

    public void fetchUsers(){
        mUserManager.getAllUsers().addOnSuccessListener(users -> {
            mUserList = users;
        }).addOnFailureListener(e -> {

        });

    }
    public void fetchUsersListRestaurant(){

        for(int i = 0 ; i<mRestaurantList.size(); i++){
            int  mUserListRestaurant = 0;
            for(int y = 0 ; y<mUserList.size(); y++){
                if(mRestaurantList.get(i).getId().equals(mUserList.get(y).getChoiceId())){

                    mUserListRestaurant++;

                }
            }
            mRestaurantList.get(i).setCustumersNumber(mUserListRestaurant);
        }
        mMutableLiveData.setValue(mRestaurantList);

    }


}
