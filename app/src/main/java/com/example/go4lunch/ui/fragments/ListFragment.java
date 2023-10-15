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
import com.example.go4lunch.adapter.ListRestaurantsAdapter;
import com.example.go4lunch.databinding.FragmentListBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.service.ItemClickSupport;
import com.example.go4lunch.ui.DetailRestaurantActivity;
import com.example.go4lunch.viewModel.DashboardListMapViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    DashboardListMapViewModel mDashboardListMapViewModel;
    FragmentListBinding mFragmentListBinding;
    private ListRestaurantsAdapter mAdapter;
    private Boolean shouldRefreshOnResume = false;

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
        initViewModel();
        initRecyclerView();
        getData();
        configureOnRecyclerView();

    }

    public void initViewModel(){
        mDashboardListMapViewModel = new ViewModelProvider(requireActivity()).get(DashboardListMapViewModel.class);


    }
    public void getData(){
        mDashboardListMapViewModel.fetchUsersListRestaurant();
        mDashboardListMapViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), this::updateList);
    }

    private void initRecyclerView(){
        mAdapter = new ListRestaurantsAdapter();
        mFragmentListBinding.listRestaurant.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        mFragmentListBinding.listRestaurant.setAdapter(mAdapter);
    }

    private void updateList(List<Restaurant> restaurants){ this.mAdapter.updateRestaurant(restaurants);}

    private void configureOnRecyclerView(){
        ItemClickSupport.addTo(mFragmentListBinding.listRestaurant, R.layout.fragment_list)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Restaurant restaurant = mAdapter.getRestaurant(position);
                        Intent intent = new Intent(getActivity(), DetailRestaurantActivity.class);
                        String placeId = restaurant.getId();
                        intent.putExtra("placeId", placeId);
                        startActivity(intent);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        shouldRefreshOnResume = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
        getData();
        configureOnRecyclerView();
        getActivity().setTitle("I'm Hungry!");

    }

}