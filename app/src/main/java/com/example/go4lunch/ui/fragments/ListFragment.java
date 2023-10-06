package com.example.go4lunch.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.ListRestaurantsAdapter;
import com.example.go4lunch.databinding.FragmentListBinding;
import com.example.go4lunch.viewModel.NearByRestaurantViewModel;
import com.example.go4lunch.viewModel.UsersViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    NearByRestaurantViewModel mNearByRestaurantViewModel;
    FragmentListBinding mFragmentListBinding;
    private ListRestaurantsAdapter mAdapter;

    public ListFragment() {
        // Required empty public constructor
    }



    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentListBinding = FragmentListBinding.inflate(inflater, container, false);
        View view = mFragmentListBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initRecyclerView();
        initViewModel();
        //getRestaurant();
    }

    public void initViewModel(){
        mNearByRestaurantViewModel = new ViewModelProvider(this).get(NearByRestaurantViewModel.class);
    }
}