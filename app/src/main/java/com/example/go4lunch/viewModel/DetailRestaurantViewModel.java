package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.example.go4lunch.service.DetailRestaurantCallback;

import java.util.List;

public class DetailRestaurantViewModel extends ViewModel {

    NearByRestaurantRepository mNearByRestaurantRepository;
    MutableLiveData<DetailRestaurant> mMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Restaurant> mRestaurantMutableLivedata = new MutableLiveData<>();

    public DetailRestaurantViewModel() {
        mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
    }

    public void fetchDatas(String placeId){
        mNearByRestaurantRepository.getDetailRestaurantFromApi(placeId, new DetailRestaurantCallback() {
            @Override
            public void OnRestaurantDetailReceived(DetailRestaurant detailRestaurant) {
                mMutableLiveData.setValue(detailRestaurant);
            }
        });
    }

    public MutableLiveData<DetailRestaurant> getMutableLiveData(){
        return mMutableLiveData;
    }
    public MutableLiveData<Restaurant> getRestaurantMutableLivedata() { return mRestaurantMutableLivedata;}
}
