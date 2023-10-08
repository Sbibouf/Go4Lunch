package com.example.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityDashboardBinding;
import com.example.go4lunch.ui.fragments.ListFragment;
import com.example.go4lunch.ui.fragments.MapFragment;
import com.example.go4lunch.ui.fragments.UsersFragment;
import com.example.go4lunch.userManager.UserManager;
import com.example.go4lunch.viewModel.DashboardViewModel;
import com.example.go4lunch.viewModel.NearByRestaurantViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;





public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener{

    private DashboardViewModel mDashboardViewModel;
    MapFragment mMapFragment = new MapFragment();
    ListFragment mListFragment = new ListFragment();
    UsersFragment mUsersFragment = new UsersFragment();
    private NearByRestaurantViewModel mNearByRestaurantViewModel;
    private LocationManager mLocationManager;
    double latitude, longitude;
    Location location;

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    View headerView;
    ImageView mUserAvatar;



    @Override
    ActivityDashboardBinding getViewBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headerView = binding.activityMainNavView.getHeaderView(0);
        binding.bottomNavigationView.setOnItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.map);
        mLocationManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        getPosition();
        initViewModel();
        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        updateDrawerWithUserData();

    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_frame_layout, mMapFragment)
                        .commit();
                return true;

            case R.id.list:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_frame_layout, mListFragment)
                        .commit();
                return true;

            case R.id.workmates:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.activity_main_frame_layout, mUsersFragment)
                        .commit();
                return true;

            case R.id.logout:

                mDashboardViewModel.signOut(DashboardActivity.this).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });

        }
        return false;
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------


    // 0 - Initialisation du ViewModel

    private void initViewModel(){
        mDashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        mDashboardViewModel.fetchUser();
        mNearByRestaurantViewModel = new ViewModelProvider(this).get(NearByRestaurantViewModel.class);
        mNearByRestaurantViewModel.fetchData(latitude,longitude);
    }

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // 4 - Update drawer layout with user name, avatar and mail

    @SuppressLint("UseCompatLoadingForDrawables")
    private void updateDrawerWithUserData(){

        mUserAvatar = headerView.findViewById(R.id.iv_user_avatar);
        TextView userName = headerView.findViewById(R.id.tv_user_name);
        TextView userMail = headerView.findViewById(R.id.tv_user_mail);

        mDashboardViewModel.getUserMutableLiveData().observe(this, user->{
            userName.setText(user.getDisplayName());
            userMail.setText(user.getEmail());
            if(user.getPhotoUrl()!=null){
                setUserPhoto(user.getPhotoUrl());
            }
            else {

                mUserAvatar.setImageResource(R.mipmap.ic_avatar);
            }
        });
    }

    private void setUserPhoto(Uri userPhoto){
        Glide.with(this)
                .load(userPhoto)
                .apply(RequestOptions.circleCropTransform())
                .into(mUserAvatar);
    }

    @SuppressLint("MissingPermission")
    public void getPosition(){
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);



            }
            else{
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}