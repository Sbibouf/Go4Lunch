package com.example.go4lunch.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkMatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkMatesFragment extends Fragment {


    public WorkMatesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WorkMatesFragment newInstance() {
        WorkMatesFragment fragment = new WorkMatesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_mates, container, false);
    }
}