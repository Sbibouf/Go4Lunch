package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.example.go4lunch.service.DetailRestaurantCallback;

public class DetailRestaurantViewModel extends ViewModel {

    NearByRestaurantRepository mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
    MutableLiveData<DetailRestaurant> mMutableLiveData = new MutableLiveData<>();


    private void fetchDatas(String placeId){
        mNearByRestaurantRepository.getDetailRestaurantFromApi(placeId, new DetailRestaurantCallback() {
            @Override
            public void OnRestaurantDetailReceived(DetailRestaurant detailRestaurant) {
                mMutableLiveData.setValue(detailRestaurant);
            }
        });
    }
}
