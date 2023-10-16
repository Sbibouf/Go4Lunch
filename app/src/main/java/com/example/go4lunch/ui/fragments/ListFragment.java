package com.example.go4lunch.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.RestaurantsListAdapter;
import com.example.go4lunch.databinding.FragmentListBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.service.ItemClickSupport;
import com.example.go4lunch.ui.RestaurantDetailActivity;
import com.example.go4lunch.viewModel.DashboardListMapViewModel;

import java.util.List;

//A fragment that shares the data of the map fragment and display the list of restaurant in a recyclerview
public class ListFragment extends Fragment {

    //Variables
    DashboardListMapViewModel mDashboardListMapViewModel;
    FragmentListBinding mFragmentListBinding;
    private RestaurantsListAdapter mAdapter;

    //Constructor
    public ListFragment() {
        // Required empty public constructor
    }

    //New Instance
    public static ListFragment newInstance() {
        return new ListFragment();
    }


    //Override methods
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

    }

    //ViewModel initialisation

    public void initViewModel(){
        mDashboardListMapViewModel = new ViewModelProvider(requireActivity()).get(DashboardListMapViewModel.class);


    }

    //Getting datas from viewmodel
    public void getData(){
        //mDashboardListMapViewModel.fetchUsersListRestaurant();
        mDashboardListMapViewModel.getListRestaurantLiveData().observe(getViewLifecycleOwner(), this::updateList);
    }

    //Recyclerview initialisation

    private void initRecyclerView(){
        mAdapter = new RestaurantsListAdapter();
        mFragmentListBinding.listRestaurant.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        mFragmentListBinding.listRestaurant.setAdapter(mAdapter);
    }

    //Adapater method call to update data

    private void updateList(List<Restaurant> restaurants){ this.mAdapter.updateRestaurant(restaurants);}


    //Identify witch element of the recyclerview is clicked on and throw extra for the RestaurantDetail Activity
    private void configureOnRecyclerView(){
        ItemClickSupport.addTo(mFragmentListBinding.listRestaurant, R.layout.fragment_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Restaurant restaurant = mAdapter.getRestaurant(position);
                        Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                        String placeId = restaurant.getId();
                        intent.putExtra("placeId", placeId);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewModel();
        initRecyclerView();
        getData();
        configureOnRecyclerView();
        getActivity().setTitle("I'm Hungry!");

    }

}