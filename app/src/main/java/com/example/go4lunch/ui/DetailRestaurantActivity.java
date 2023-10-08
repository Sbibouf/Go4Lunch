package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityDetailRestaurantBinding;

public class DetailRestaurantActivity extends BaseActivity<ActivityDetailRestaurantBinding> {

    @Override
    ActivityDetailRestaurantBinding getViewBinding() {
        return ActivityDetailRestaurantBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        initRecyclerView();
        getDataRestaurant();
    }

    public void initViewModel(){

    }


    public void initRecyclerView(){


    }

    public void getDataRestaurant(){


    }
}