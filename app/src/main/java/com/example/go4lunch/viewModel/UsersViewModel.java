package com.example.go4lunch.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UsersViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");
    ArrayList<User> mUsers = new ArrayList<>();
}
