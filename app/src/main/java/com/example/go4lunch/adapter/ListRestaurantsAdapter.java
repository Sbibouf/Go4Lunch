package com.example.go4lunch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;

import java.util.ArrayList;
import java.util.List;

public class ListRestaurantsAdapter extends RecyclerView.Adapter<ListRestaurantsAdapter.ViewHolder> {

    private List<Restaurant> mRestaurantList;

    public ListRestaurantsAdapter() {
        mRestaurantList = new ArrayList<>();
    }

    public void updateRestaurant(List<Restaurant> restaurants){
        this.mRestaurantList.clear();
        this.mRestaurantList.addAll(restaurants);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_restaurants, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTitre, mAdress, mOpeningHours, mDistance;
        ImageView mIcone, mFav, mPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitre = itemView.findViewById(R.id.titre_resto);
            mAdress = itemView.findViewById(R.id.adress_resto);
            mOpeningHours = itemView.findViewById(R.id.heure_resto);
            mDistance = itemView.findViewById(R.id.distance_resto);
            mIcone = itemView.findViewById(R.id.icone_resto);
            mFav = itemView.findViewById(R.id.fav_resto);
            mPhoto = itemView.findViewById(R.id.iv_restaurant);
        }
        public void bind(Restaurant restaurant){

            mTitre.setText(restaurant.getName());

        }
    }


}
