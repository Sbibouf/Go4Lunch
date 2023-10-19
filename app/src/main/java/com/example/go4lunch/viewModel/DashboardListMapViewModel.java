package com.example.go4lunch.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
    private MutableLiveData<List<User>> mUserMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Restaurant>> mMutableLiveData = new MutableLiveData<>();
    private MediatorLiveData<List<Restaurant>> mListMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<User> mCurrentUserLivedata = new MutableLiveData<>();
    private List<Restaurant> mRestaurantList = new ArrayList<>();
    private List<User>  mUserList = new ArrayList<>();

    public MutableLiveData<User> getCurrentUserLivedata(){
        return mCurrentUserLivedata;
    }
    public LiveData<List<Restaurant>> getListRestaurantLiveData(){return mListMediatorLiveData;}

    public DashboardListMapViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
        mUserManager = UserManager.getInstance();
        fetchUsers();
    }

    public void getCurrentUser(){
        mUserManager.getUserData().addOnSuccessListener(user -> {
            mCurrentUserLivedata.setValue(user);
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
            }
        });

    }

    public void fetchUsers(){
        mUserManager.getAllUsers().addOnSuccessListener(users -> {
            mUserMutableLiveData.setValue(users);
        }).addOnFailureListener(e -> {

        });

    }

    public void fuseLivedata(){

        mListMediatorLiveData.addSource(mMutableLiveData, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurantList) {
                mRestaurantList = restaurantList;
                merge();
            }
        });

        mListMediatorLiveData.addSource(mUserMutableLiveData, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                mUserList = users;
                merge();
            }
        });
    }

    private void merge(){
        List<Restaurant> restaurantList = new ArrayList<>();
                for(Restaurant restaurant : mRestaurantList){
                    int  mUserListRestaurant = 0;
                    for(User user: mUserList){
                        if(restaurant.getName().equals(user.getChoice())){
                            mUserListRestaurant++;
                        }
                    }
                    restaurant.setCustumersNumber(mUserListRestaurant);
                    restaurantList.add(restaurant);
                }
                mListMediatorLiveData.setValue(restaurantList);
    }


}
