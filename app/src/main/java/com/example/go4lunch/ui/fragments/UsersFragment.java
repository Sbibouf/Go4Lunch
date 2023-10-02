package com.example.go4lunch.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.UsersAdapter;
import com.example.go4lunch.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");
    ArrayList<User> mUsers = new ArrayList<>();



    public UsersFragment() {
        // Required empty public constructor
    }


    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getFirestoreUsers();
        return inflater.inflate(R.layout.fragment_work_mates, container, false);
    }

    private void getFirestoreUsers(){

        usersCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                User user = documentSnapshot.toObject(User.class);
                mUsers.add(user);
            }

            // Mettez à jour la RecyclerView avec les données
            RecyclerView recyclerView = getView().findViewById(R.id.list_workmates); // Remplacez R.id.recycler_view par l'ID de votre RecyclerView
            UsersAdapter adapter = new UsersAdapter(mUsers);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }).addOnFailureListener(e -> {
            // Gérez les erreurs de récupération des données ici
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUsers.clear();
    }
}