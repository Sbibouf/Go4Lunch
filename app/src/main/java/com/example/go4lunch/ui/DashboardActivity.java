package com.example.go4lunch.ui;

import static com.example.go4lunch.BuildConfig.MAPS_API_KEY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityDashboardBinding;
import com.example.go4lunch.model.User;
import com.example.go4lunch.ui.fragments.ListFragment;
import com.example.go4lunch.ui.fragments.MapFragment;
import com.example.go4lunch.ui.fragments.UsersFragment;
import com.example.go4lunch.viewModel.DashboardListMapViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

//Activity that deals with the fragments and the navigation drawer
public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener{

    //Variables
    private DashboardListMapViewModel mDashboardListMapViewModel;
    double latitude, longitude;
    private String userChoiceExtra= "";
    ActivityResultLauncher<Intent> startAutocomplete;

    //Fragment Instance
    MapFragment mMapFragment = MapFragment.newInstance();
    ListFragment mListFragment = ListFragment.newInstance();
    UsersFragment mUsersFragment = UsersFragment.newInstance();

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
        getExtras();
        initViewModel();
        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        updateDrawerWithUserData();
        initPlaces();
        setStartAutocomplete();



    }

    //Get the search button menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Menu navigation drawer element options

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

                mDashboardListMapViewModel.signOut(DashboardActivity.this).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                });
                return true;

            case R.id.your_lunch:

                Intent intentYourLunch = new Intent(this, RestaurantDetailActivity.class);
                if(userChoiceExtra!=""){
                    intentYourLunch.putExtra("placeId", userChoiceExtra);
                    startActivity(intentYourLunch);
                }else{
                    Toast.makeText(getApplicationContext(),"Vous n'avez pas choisi aujourd'hui", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.settings:
                Intent intentSetting = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                intentSetting.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intentSetting);
                return true;

        }
        return false;
    }

    // Handle the Search menu option

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.search_button_menu){
            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .setTypesFilter(Arrays.asList("restaurant", "cafe"))
                    .build(getApplicationContext());
            startAutocomplete.launch(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 0 - ViewModel Initialisation

    private void initViewModel(){

        mDashboardListMapViewModel = new ViewModelProvider(this).get(DashboardListMapViewModel.class);
        mDashboardListMapViewModel.fetchData(latitude,longitude);
        mDashboardListMapViewModel.fetchUsers();
        mDashboardListMapViewModel.fuseLivedata();
        mDashboardListMapViewModel.getCurrentUser();

    }

    // 1 - Toolbar Configuration
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Drawer Layout Configuration
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - NavigationView Configuration
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

        mDashboardListMapViewModel.getCurrentUserLivedata().observe(this, user->{
            userName.setText(user.getName());
            userMail.setText(user.getEmail());
            if(user.getAvatar()!=null){
                setUserPhoto(user.getAvatar());
            }
            else {

                mUserAvatar.setImageResource(R.mipmap.ic_avatar);
            }
            userChoiceExtra=user.getChoiceId();
        });
    }

    // 4.1 Set the User photo into the Avatar UI element
    private void setUserPhoto(String userPhoto){
        Glide.with(this)
                .load(userPhoto)
                .apply(RequestOptions.circleCropTransform())
                .into(mUserAvatar);
    }


    //Refetch datas to update UI and see changes
    @Override
    protected void onResume() {
        super.onResume();
        mDashboardListMapViewModel.fetchUsers();
        mDashboardListMapViewModel.getCurrentUser();
        updateDrawerWithUserData();
    }

    //Get Location Extra from Login Activity
    public void getExtras(){

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
    }


    //Places initialisation
    public void initPlaces(){
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), MAPS_API_KEY, Locale.FRANCE);
        }
    }

    //Handle the result of the search
    public void setStartAutocomplete(){
        startAutocomplete = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Place place = Autocomplete.getPlaceFromIntent(intent);
                            if(mMapFragment.isVisible()){
                                LatLng latLngPlace = place.getLatLng();
                                String title = place.getName();
                                mMapFragment.updateMapOnSearch(latLngPlace, title);
                            } else if (mListFragment.isVisible()){
                                String placeId = place.getId();
                                Intent intent1 = new Intent(getApplicationContext(), RestaurantDetailActivity.class);
                                intent1.putExtra("placeId", placeId);
                                startActivity(intent1);
                            }
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // The user canceled the operation.
                        //Log.i(TAG, "User canceled autocomplete");
                    }
                });
    }

    //Remove new observers from mediatorlivedata
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDashboardListMapViewModel.removeLiveDataObservers();
    }
}