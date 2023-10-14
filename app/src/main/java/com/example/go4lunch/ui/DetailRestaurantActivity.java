package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.adapter.UsersChoiceAdapter;
import com.example.go4lunch.databinding.ActivityDetailRestaurantBinding;
import com.example.go4lunch.model.DetailRestaurant;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;
import com.example.go4lunch.viewModel.DetailRestaurantViewModel;

import java.util.List;
import java.util.Objects;

public class DetailRestaurantActivity extends AppCompatActivity {

    private ActivityDetailRestaurantBinding mActivityDetailRestaurantBinding;
    private Restaurant actualRestaurant;
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
        mDetailRestaurantViewModel.getMutableLiveData().observe(this, new Observer<DetailRestaurant>() {
            @Override
            public void onChanged(DetailRestaurant detailRestaurant) {
                mActivityDetailRestaurantBinding.titleRestaurantDetail.setText(detailRestaurant.getName());
                mActivityDetailRestaurantBinding.addressRestaurantDetail.setText(detailRestaurant.getAddress());
                Glide.with(getApplicationContext())
                        .load(detailRestaurant.getPhotoUrl())
                        .into(mActivityDetailRestaurantBinding.restaurantDetailPhoto);
                phone_number= detailRestaurant.getPhone_number();
                website = detailRestaurant.getWebsite();
                placeName = detailRestaurant.getName();
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



}