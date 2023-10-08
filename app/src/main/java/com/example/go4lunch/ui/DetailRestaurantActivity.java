package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityDetailRestaurantBinding;
import com.example.go4lunch.model.Restaurant;

import java.util.Objects;

public class DetailRestaurantActivity extends AppCompatActivity {

    private ActivityDetailRestaurantBinding mActivityDetailRestaurantBinding;
    private Restaurant restaurant;
    private String userchoice;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailRestaurantBinding = ActivityDetailRestaurantBinding.inflate(getLayoutInflater());
        setContentView(mActivityDetailRestaurantBinding.getRoot());
        initViewModel();
        getExtras();
        initRecyclerView();
        getDataRestaurant();
    }

    public void initViewModel(){

    }


    public void initRecyclerView(){


    }


    private void getExtras(){

        if(getIntent().hasExtra("userChoice")){
            userchoice = (String) getIntent().getSerializableExtra("userChoice");
        }
        else if (getIntent().hasExtra("restaurant")) {
            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        }

    }

    public void getDataRestaurant(){

        mActivityDetailRestaurantBinding.titleRestaurantDetail.setText(restaurant.getName());
        mActivityDetailRestaurantBinding.addressRestaurantDetail.setText(restaurant.getAddress());
        Glide.with(getApplicationContext())
                .load(restaurant.getPhotoUrl())
                .into(mActivityDetailRestaurantBinding.restaurantDetailPhoto);


    }
}