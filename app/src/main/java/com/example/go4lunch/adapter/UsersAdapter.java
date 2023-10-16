package com.example.go4lunch.adapter;

import android.graphics.Typeface;
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
import com.example.go4lunch.ui.fragments.UsersFragment;

import java.util.ArrayList;
import java.util.List;
//Adapter of the recycler view that show the list of co workers and their choice
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>  {

    //Variables
    private List<User> usersList;

    //Constructor
    public UsersAdapter() {
        usersList = new ArrayList<>() ;
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
        User user = usersList.get(position);
        holder.bind(user);

    }

    @Override
    public int getItemCount() {
        return usersList.size();
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



        public ViewHolder(View view) {
            super(view);

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

            if(user.getChoiceId()==null){
                choiceWorkmate.setText(""+user.getName()+" hasn't decided yet");
                choiceWorkmate.setTypeface(null, Typeface.ITALIC);


            }
            else{
                choiceWorkmate.setText(""+user.getName()+" is eating at " + user.getChoice()+" today");
                choiceWorkmate.setTypeface(null, Typeface.BOLD);
            }

        }
    }

    public User getUser(int position){
        return usersList.get(position);
    }
    public void updateUsers(List<User> users){
        this.usersList.clear();
        this.usersList.addAll(users);
        notifyDataSetChanged();
    }


}
