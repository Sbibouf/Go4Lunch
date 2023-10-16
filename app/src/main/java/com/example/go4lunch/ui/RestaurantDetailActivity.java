package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.go4lunch.R;
import com.example.go4lunch.adapter.UsersChoiceAdapter;
import com.example.go4lunch.databinding.ActivityDetailRestaurantBinding;
import com.example.go4lunch.model.RestaurantDetail;
import com.example.go4lunch.model.User;
import com.example.go4lunch.viewModel.DetailRestaurantViewModel;

import java.util.List;

public class RestaurantDetailActivity extends AppCompatActivity {

    private ActivityDetailRestaurantBinding mActivityDetailRestaurantBinding;
    private String placeId ="";
    private String placeName = "";
    private String phone_number = "";
    private String website = "";
    private DetailRestaurantViewModel mDetailRestaurantViewModel;
    private UsersChoiceAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailRestaurantBinding = ActivityDetailRestaurantBinding.inflate(getLayoutInflater());
        setContentView(mActivityDetailRestaurantBinding.getRoot());
        getExtras();
        initViewModel();
        initRecyclerView();
        getDetailRestaurant();
        getRestaurantUsers();
        getCurrentUserData();



        mActivityDetailRestaurantBinding.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone_number!=""){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+phone_number));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),""+placeName+" n'a pas fournit de numéro", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mActivityDetailRestaurantBinding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailRestaurantViewModel.addLikedRestaurants(placeName);
                getCurrentUserData();

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
                    Toast.makeText(getApplicationContext(),""+ placeName+" n'a pas fournit de siteweb", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mActivityDetailRestaurantBinding.addRestaurantDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDetailRestaurantViewModel.updateUserChoiceId(placeId);
                mDetailRestaurantViewModel.updateUserChoice(placeName);
                Toast.makeText(getApplicationContext(),"Vous avez choisi "+placeName+" aujourd'hui", Toast.LENGTH_SHORT).show();
                mActivityDetailRestaurantBinding.addRestaurantDetail.setImageResource(R.drawable.ic_choose_restaurant);
                mDetailRestaurantViewModel.fetchRestaurantUsers(placeId);
                getRestaurantUsers();
            }
        });

    }


    public void initViewModel(){

        mDetailRestaurantViewModel = new ViewModelProvider(this).get(DetailRestaurantViewModel.class);
        mDetailRestaurantViewModel.fetchDatas(placeId);
        mDetailRestaurantViewModel.fetchRestaurantUsers(placeId);
    }


    public void initRecyclerView(){

        mAdapter = new UsersChoiceAdapter();
        mActivityDetailRestaurantBinding.usersChoiceList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        mActivityDetailRestaurantBinding.usersChoiceList.setAdapter(mAdapter);

    }


    private void getExtras(){

        if(getIntent().hasExtra("placeId")){
            placeId=getIntent().getStringExtra("placeId");
        }

    }

    public void getDetailRestaurant(){
        mDetailRestaurantViewModel.getMutableLiveData().observe(this, new Observer<RestaurantDetail>() {
            @Override
            public void onChanged(RestaurantDetail restaurantDetail) {
                mActivityDetailRestaurantBinding.titleRestaurantDetail.setText(restaurantDetail.getName());
                mActivityDetailRestaurantBinding.addressRestaurantDetail.setText(restaurantDetail.getAddress());
                Glide.with(getApplicationContext())
                        .load(restaurantDetail.getPhotoUrl())
                        .into(mActivityDetailRestaurantBinding.restaurantDetailPhoto);
                phone_number= restaurantDetail.getPhone_number();
                website = restaurantDetail.getWebsite();
                placeName = restaurantDetail.getName();
            }
        });



    }

    public void getRestaurantUsers(){
        mDetailRestaurantViewModel.getListMutableLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                updateUsersChoice(users);


            }
        });
    }


    private void updateUsersChoice(List<User> users){
        this.mAdapter.updateRestaurant(users);
    }
    private void getCurrentUserData(){
        mDetailRestaurantViewModel.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user.getChoiceId().equals(placeId)){
                    mActivityDetailRestaurantBinding.addRestaurantDetail.setImageResource(R.drawable.ic_choose_restaurant);
                }
                for(int i = 0; i<user.getLikedRestaurants().size(); i++){
                    if(user.getLikedRestaurants().get(i).equals(placeName)){
                        mActivityDetailRestaurantBinding.ratingRestaurantDetail.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentUserData();
    }
}