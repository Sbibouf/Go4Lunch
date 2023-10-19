package com.example.go4lunch.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//Repository of user that can create update and delete from firestore
public final class UserRepository {

    //Variables

    private static final String COLLECTION_NAME = "users";
    private static final String USERNAME_FIELD = "username";
    private static final String USERCHOICEID_FIELD = "choiceId";
    private static final String USERCHOICE_FIELD = "choice";
    private static final String USERLIKEDRESTAURANT_FIELD="likedRestaurants";
    private static volatile UserRepository instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersCollection = db.collection("users");


    //Constructor
    private UserRepository() { }

    //Singleton
    public static UserRepository getInstance() {
        UserRepository result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserRepository.class) {
            if (instance == null) {
                instance = new UserRepository();
            }
            return instance;
        }
    }

    // Get the Collection Reference
    private CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // Create User in Firestore
    public void createUser() {
        FirebaseUser user = getCurrentUser();
        if(user != null){
            String urlPicture = (user.getPhotoUrl() != null) ? user.getPhotoUrl().toString() : null;
            String username = user.getDisplayName();
            String uid = user.getUid();
            String email = user.getEmail();
            List<String> likedRestaurant = new ArrayList<>();

            User userToCreate = new User(uid, username, urlPicture, email, likedRestaurant);

            Task<DocumentSnapshot> userData = getUserData();
            // If the user already exist in Firestore
            assert userData != null;
            userData.addOnSuccessListener(documentSnapshot -> {
                this.getUsersCollection().document(uid).set(userToCreate);
            });
        }
    }

    // Get User Data from Firestore
    public Task<DocumentSnapshot> getUserData(){
        String uid = this.getCurrentUser().getUid();
        if(uid != null){
            return this.getUsersCollection().document(uid).get();
        }else{
            return null;
        }
    }

    // Update User Username
    public Task<Void> updateUsername(String username) {
        String uid = Objects.requireNonNull(this.getCurrentUser()).getUid();
        return this.getUsersCollection().document(uid).update(USERNAME_FIELD, username);
    }

    //Update User UserchoiceId
    public Task<Void> updateUserChoiceId(String placeId){
        String uid = Objects.requireNonNull(this.getCurrentUser()).getUid();
        return this.getUsersCollection().document(uid).update(USERCHOICEID_FIELD, placeId);
    }

    //Update User Userchoice
    public Task<Void> updateUserChoice(String placeName){
        String uid = Objects.requireNonNull(this.getCurrentUser()).getUid();
        return this.getUsersCollection().document(uid).update(USERCHOICE_FIELD, placeName);
    }
    //Update User UserLikedRestaurants
    public Task<Void> updateUserLikedRestaurant(String placeName){
        String uid = Objects.requireNonNull(this.getCurrentUser()).getUid();
        return this.getUsersCollection().document(uid).update(USERLIKEDRESTAURANT_FIELD, FieldValue.arrayUnion(placeName));
    }

    //Get Current User from Firestore
    @Nullable
    public FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    //SignOut user from Firestore
    public Task<Void> signOut(Context context){
        return AuthUI.getInstance().signOut(context);
    }

    //Delete User from Firestore
    public Task<Void> deleteUser(Context context) {

        return AuthUI.getInstance().delete(context);
    }

    //Get all Users from Firestore

    public Task<List<User>> getAllUsers(){

        TaskCompletionSource<List<User>> taskCompletionSource = new TaskCompletionSource<>();
        List<User> usersList = new ArrayList<>();

        usersCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                User user = documentSnapshot.toObject(User.class);
                usersList.add(user);
            }
            taskCompletionSource.setResult(usersList);
        }).addOnFailureListener(taskCompletionSource::setException);

        return taskCompletionSource.getTask();
    }

    //Get Users restaurant

    public Task<List<User>> getRestaurantUsers(String placeId){

        TaskCompletionSource<List<User>> taskCompletionSource = new TaskCompletionSource<>();
        List<User> userList = new ArrayList<>();
        usersCollection.whereEqualTo("choiceId", placeId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        User user = documentSnapshot.toObject(User.class);
                        userList.add(user);
                    }
                    taskCompletionSource.setResult(userList);
                })
                .addOnFailureListener(taskCompletionSource::setException);
        return taskCompletionSource.getTask();
    }

    public void getRestaurantUSers(String placeId, MutableLiveData<List<User>> userLiveData){

        usersCollection.whereEqualTo("choiceId", placeId)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        return;
                    }

                    List<User> userList = new ArrayList<>();
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            User user = documentSnapshot.toObject(User.class);
                            userList.add(user);
                        }
                    }
                    userLiveData.setValue(userList);
                });
    }


}
