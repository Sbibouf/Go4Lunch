package com.example.go4lunch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.R;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;

import java.util.ArrayList;
import java.util.List;
//Adapter of the recylcer view that shows the list of co workers that choose a specific place
public class UsersChoiceAdapter extends RecyclerView.Adapter<UsersChoiceAdapter.ViewHolder> {

    //Variables
    private List<User> mUserChoice;

    //Constructor
    public UsersChoiceAdapter() {
        mUserChoice = new ArrayList<>();
    }


    //Override methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_mates, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUserChoice.get(position);
        holder.bind(user);

    }

    @Override
    public int getItemCount() {
        return mUserChoice.size();
    }

    //Methods to bind update and get the elements

    public class ViewHolder extends RecyclerView.ViewHolder{
        /**
         * The avatar image of a workmate
         */
        private final AppCompatImageView imgAvatar;

        /**
         * The TextView displaying the choice made by the workmates
         */
        private final TextView choiceWorkmate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.workmates_avatar);
            choiceWorkmate = itemView.findViewById(R.id.workmates_choice);
        }
        public void bind(User user){

            if(user.getAvatar()==null){
                imgAvatar.setImageResource(R.mipmap.ic_avatar);
            }
            else {
                Glide.with(itemView.getContext())
                        .load(user.getAvatar())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imgAvatar);
            }

                choiceWorkmate.setText(""+user.getName()+" is eating here");

        }
    }
    public void updateRestaurant(List<User> users){
        this.mUserChoice.clear();
        this.mUserChoice.addAll(users);
        notifyDataSetChanged();
    }
}
