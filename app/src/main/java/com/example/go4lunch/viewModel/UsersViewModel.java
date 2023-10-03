package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.UsersAdapter;
import com.example.go4lunch.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends ViewModel {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");

    MutableLiveData<List<User>> mListMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<User>> getListMutableLiveData(){
        return mListMutableLiveData;
    }

    public void fetchUsersData(){

        List<User> mUsers = new ArrayList<>();

        usersCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                User user = documentSnapshot.toObject(User.class);
                mUsers.add(user);
            }
            mListMutableLiveData.setValue(mUsers);
        }).addOnFailureListener(e -> {
            // Gérez les erreurs de récupération des données ici
        });

    }
}
