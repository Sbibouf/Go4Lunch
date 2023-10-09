package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityDetailRestaurantBinding;
import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.viewModel.DetailRestaurantViewModel;

import java.util.Objects;

public class DetailRestaurantActivity extends AppCompatActivity {

    private ActivityDetailRestaurantBinding mActivityDetailRestaurantBinding;
    private Restaurant restaurant;
    private String placeId;
    private String userchoice;
    private String phone_number = "";
    private String website = "";
    private DetailRestaurantViewModel mDetailRestaurantViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailRestaurantBinding = ActivityDetailRestaurantBinding.inflate(getLayoutInflater());
        setContentView(mActivityDetailRestaurantBinding.getRoot());
        getExtras();
        initViewModel();
        initRecyclerView();
        getDataRestaurant();

        mActivityDetailRestaurantBinding.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone_number));
                startActivity(intent);
            }
        });

        mActivityDetailRestaurantBinding.websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(website!=""){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""+website));
                    startActivity(browserIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(),""+restaurant.getName()+" n'a pas fournit de siteweb", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void initViewModel(){

        mDetailRestaurantViewModel = new ViewModelProvider(this).get(DetailRestaurantViewModel.class);
        mDetailRestaurantViewModel.fetchDatas(placeId);
    }


    public void initRecyclerView(){


    }


    private void getExtras(){

        if(getIntent().hasExtra("userChoice")){
            userchoice = (String) getIntent().getSerializableExtra("userChoice");
            placeId = userchoice;
        }
        else if (getIntent().hasExtra("restaurant")) {
            restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
            placeId = restaurant.getId();
        }

    }

    public void getDataRestaurant(){
        mDetailRestaurantViewModel.getMutableLiveData().observe(this, new Observer<DetailRestaurant>() {
            @Override
            public void onChanged(DetailRestaurant detailRestaurant) {
                mActivityDetailRestaurantBinding.titleRestaurantDetail.setText(detailRestaurant.getName());
                mActivityDetailRestaurantBinding.addressRestaurantDetail.setText(restaurant.getAddress());
                Glide.with(getApplicationContext())
                        .load(restaurant.getPhotoUrl())
                        .into(mActivityDetailRestaurantBinding.restaurantDetailPhoto);
                phone_number= detailRestaurant.getPhone_number();
                website = detailRestaurant.getWebsite();
            }
        });


    }



}