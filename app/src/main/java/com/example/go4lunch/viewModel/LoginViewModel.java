package com.example.go4lunch.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.repositories.UserRepository;
import com.example.go4lunch.userManager.UserManager;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private final UserManager mUserManager = UserManager.getInstance();

    public Boolean isCurrentUserLogged(){
        return mUserManager.isCurrentUserLogged();
    }


    public void createUser(){
        mUserManager.createUser();
    }



}
