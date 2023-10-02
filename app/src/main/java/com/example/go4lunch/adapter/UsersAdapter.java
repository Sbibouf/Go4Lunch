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
import com.example.go4lunch.model.User;
import com.example.go4lunch.ui.fragments.UsersFragment;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>  {


    private ArrayList<User> usersList;


    public UsersAdapter(ArrayList<User> usersList) {
        this.usersList = usersList;
    }

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

            if(user.getChoice()==null){
                choiceWorkmate.setText(""+user.getName()+" hasn't decided yet");
            }
            else{
                choiceWorkmate.setText(""+user.getName()+" is eating " + user.getChoice());
            }

        }
    }


}