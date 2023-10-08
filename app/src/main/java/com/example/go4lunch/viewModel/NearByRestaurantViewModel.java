package com.example.go4lunch.viewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
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

import javax.inject.Inject;




public class NearByRestaurantViewModel extends ViewModel {

    private final NearByRestaurantRepository mNearByRestaurantRepository;
    private final MutableLiveData<List<Restaurant>> mMutableLiveData = new MutableLiveData<>();



    public NearByRestaurantViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
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
}