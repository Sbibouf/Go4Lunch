package com.example.go4lunch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.ViewHolder>  {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_mates, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
    }


}
