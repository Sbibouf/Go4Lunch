package com.example.go4lunch.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.repositories.NearByRestaurantRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NearByRestaurantViewModel extends ViewModel {

    private final NearByRestaurantRepository mNearByRestaurantRepository = NearByRestaurantRepository.getInstance();
    List<Restaurant> restaurantList;

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
    String dateDeDepart = "01-01-2023";

    private final MutableLiveData<List<Restaurant>> mMutableLiveData = new MutableLiveData<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference restaurantCollection = db.collection("restaurants");

    public NearByRestaurantViewModel() {
    }

    public void getNearByRestaurantFromFirestore(double lat, double longi) throws ParseException {

        restaurantList = new ArrayList<>();
        //Les dates du jour et de départ pour comparer
        Date dateDuJour = new Date();
        Date dateDeDébut = sdf.parse(dateDeDepart);

        Calendar calDepart = Calendar.getInstance();
        calDepart.setTime(dateDeDébut);
        calDepart.add(Calendar.MONTH,6);

        Calendar calDuJour = Calendar.getInstance();
        calDuJour.setTime(dateDuJour);

        if(restaurantCollection==null| calDuJour.compareTo(calDepart)>=0){

            mNearByRestaurantRepository.getNearbyRestaurantFromApi(lat, longi);
            dateDeDébut = dateDuJour;
            getCollection();
        }
        else {

            getCollection();
        }

    }
    public void getCollection(){
        restaurantCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                restaurantList.add(restaurant);
            }
            mMutableLiveData.setValue(restaurantList);
        }).addOnFailureListener(e -> {
            // Erreurs de récupération des données
        });
    }

    public MutableLiveData<List<Restaurant>> getMutableLiveData(){
        return mMutableLiveData;
    }
}
