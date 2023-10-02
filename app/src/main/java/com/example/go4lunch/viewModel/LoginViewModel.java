package com.example.go4lunch.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.repositories.UserRepository;
import com.example.go4lunch.userManager.UserManager;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private final UserManager mUserManager = UserManager.getInstance();
    private MutableLiveData<Boolean> mMutableLiveData = new MutableLiveData<>();

    public LiveData<Boolean> isCurrentUserLogged(){
        return mMutableLiveData;
    }

    public void fetchData(){
        Boolean isLogged = mUserManager.isCurrentUserLogged();
        mMutableLiveData.setValue(isLogged);
    }

    public void createUser(){
        mUserManager.createUser();
    }



}
