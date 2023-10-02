package com.example.go4lunch.viewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.userManager.UserManager;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class DashboardViewModel extends ViewModel {

    private final UserManager mUserManager = UserManager.getInstance();
    MutableLiveData<FirebaseUser> mUserMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
        return mUserMutableLiveData;
    }

    public void fetchUser(){

        FirebaseUser user = mUserManager.getCurrentUser();
        mUserMutableLiveData.setValue(user);
    }

    public Task<Void> signOut(Context context){
        return mUserManager.signOut(context);
    }
}
