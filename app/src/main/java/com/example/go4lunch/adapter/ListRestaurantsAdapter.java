package com.example.go4lunch.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        Restaurant restaurant = mRestaurantList.get(position);
        holder.bind(restaurant);
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
            mAdress.setText(restaurant.getAddress());
            if(restaurant.getOpen()){
                mOpeningHours.setText("Is Open");
                mOpeningHours.setTextColor(Color.GREEN);
            }else{
                mOpeningHours.setText("Closed");
                mOpeningHours.setTextColor(Color.RED);
            }
            mDistance.setText(restaurant.getDistance()+"m");

            if(Objects.equals(restaurant.getPhotoUrl(), "")){
                mPhoto.setImageResource(R.drawable.ic_no_photo_foreground);
            }
            else{
                Glide.with(itemView.getContext())
                        .load(restaurant.getPhotoUrl())
                        .apply(RequestOptions.centerCropTransform())
                        .into(mPhoto);
            }

        }
    }


}
