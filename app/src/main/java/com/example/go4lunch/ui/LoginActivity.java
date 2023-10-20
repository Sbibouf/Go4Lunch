package com.example.go4lunch.ui;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityMainBinding;
import com.example.go4lunch.userManager.UserManager;
import com.example.go4lunch.viewModel.LoginViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;

//Starting activity that allow the user to login the app with 3 differents options , google, mail or twitter
public class LoginActivity extends BaseActivity<ActivityMainBinding> {

    //Variables
    private ActivityResultLauncher<Intent> mSignInLauncher;
    private static final int RC_SIGN_IN = 123;
    private LoginViewModel mLoginViewModel;
    private LocationManager mLocationManager;
    double latitude, longitude;
    Location location;

    //Override method for the binding
    @Override
    ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        signInLauncher();
        checkIfUserIsLogged();
        setupListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkIfUserIsLogged();
    }


    //Check if user ig logged and change the button title and its behaviour

    public void checkIfUserIsLogged(){

        if(mLoginViewModel.isCurrentUserLogged()){
            binding.button2.setText("C'est parti");

        }
        else{
            binding.button2.setText("Se connecter");
        }

    }

    public void setupListener(){
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLoginViewModel.isCurrentUserLogged()){
                    obtainLocation();
                    startDashBoardActivity();
                }
                else {
                    startSignInActivity();
                }
            }
        });
    }

    //Create the signInLauncher with registerForActivityResult and create user in firestore if resultCode ok
    public void signInLauncher(){
        mSignInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if(result.getResultCode()==RESULT_OK){
                mLoginViewModel.createUser();
                getPosition();
            }
        });
    }


    //starting the SignIn Activity
    public void startSignInActivity(){


        // Choose authentication providers
        List<AuthUI.IdpConfig> providers =
                Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.TwitterBuilder().build());

        // Launch the activity
        mSignInLauncher.launch(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setTheme(R.style.LoginTheme)
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false, true)
                .setLogo(R.mipmap.ic_bol_foreground)
                .build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }


    // Show Snack Bar with a message
    private void showSnackBar( String message){
        Snackbar.make(binding.mainLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    // Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){


        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            // SUCCESS
            if (resultCode == RESULT_OK) {
                showSnackBar(getString(R.string.connection_succeed));
            } else {
                // ERRORS
                if (response == null) {
                    showSnackBar(getString(R.string.error_authentication_canceled));
                } else if (response.getError()!= null) {
                    if(response.getError().getErrorCode() == ErrorCodes.NO_NETWORK){
                        showSnackBar(getString(R.string.error_no_internet));
                    } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        showSnackBar(getString(R.string.error_unknown_error));
                    }
                }
            }
        }
    }

    //Ask for User permission to get the location of the device he is using

    @SuppressLint("MissingPermission")
    public void getPosition(){
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
            else{
                obtainLocation();
                checkIfUserIsLogged();

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //Handle what to do if the permission is granted or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtainLocation();
            } else {
                Toast.makeText(this, "Permission refusée. L'application ne peut pas accéder à la localisation.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Obtain the location from GPS_PROVIDER
    @SuppressLint("MissingPermission")
    private void obtainLocation() {
        try {

            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitude = location.getLatitude();
            longitude = location.getLongitude();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Start the Dashboard Activity with the extra latitude and longitude
    public void startDashBoardActivity(){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }


}