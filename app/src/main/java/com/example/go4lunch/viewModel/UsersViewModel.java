package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.UsersAdapter;
import com.example.go4lunch.model.User;
import com.example.go4lunch.repositories.UserRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    UserRepository mUserRepository = UserRepository.getInstance();

    MutableLiveData<List<User>> mListMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<User>> getListMutableLiveData(){
        return mListMutableLiveData;
    }


    public void fetcUsers(){

        mUserRepository.getAllUsers().addOnSuccessListener(users -> {
            mListMutableLiveData.setValue(users);
        }).addOnFailureListener(e -> {

        });

    }
}
