package com.example.go4lunch.viewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.example.go4lunch.repositories.UserRepository;
import com.example.go4lunch.service.DetailRestaurantCallback;
import com.example.go4lunch.service.RestaurantCallback;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;




public class NearByRestaurantViewModel extends ViewModel {

    private final NearByRestaurantRepository mNearByRestaurantRepository;
    UserRepository mUserRepository;
    private final MutableLiveData<List<Restaurant>> mMutableLiveData = new MutableLiveData<>();
    private List<Restaurant> mRestaurantList = new ArrayList<>();
    private List<User>  mUserList = new ArrayList<>();




    public NearByRestaurantViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
        mUserRepository = UserRepository.getInstance();
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
        mUserRepository.getAllUsers().addOnSuccessListener(users -> {
            mUserList = users;
        }).addOnFailureListener(e -> {

        });

    }
    public void fetchUsersListRestaurant(){

        for(int i = 0 ; i<mRestaurantList.size(); i++){
            List<User>  mUserListRestaurant = new ArrayList<>();
            for(int y = 0 ; y<mUserList.size(); y++){
                if(mRestaurantList.get(i).getId().equals(mUserList.get(y).getChoiceId())){

                    mUserListRestaurant.add(mUserList.get(y));

                }
            }
            mRestaurantList.get(i).setUsersList(mUserListRestaurant);
        }





    }
}
