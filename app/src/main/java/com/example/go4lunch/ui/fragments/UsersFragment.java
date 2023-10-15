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
import android.widget.Toast;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.UsersAdapter;
import com.example.go4lunch.databinding.FragmentWorkMatesBinding;
import com.example.go4lunch.model.Restaurant;
import com.example.go4lunch.model.User;
import com.example.go4lunch.service.ItemClickSupport;
import com.example.go4lunch.ui.DetailRestaurantActivity;
import com.example.go4lunch.viewModel.UsersViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {

    UsersViewModel mUsersViewModel;
    FragmentWorkMatesBinding mFragmentWorkMatesBinding;
    private UsersAdapter mAdapter;



    public UsersFragment() {
        // Required empty public constructor
    }


    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentWorkMatesBinding = FragmentWorkMatesBinding.inflate(inflater, container, false);
        View view = mFragmentWorkMatesBinding.getRoot();
        //initViewModel();
        //initRecyclerView();
        //getUsers();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initViewModel();
        getUsers();
        configureOnRecyclerView();
    }

    public void initViewModel(){
        mUsersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        mUsersViewModel.fetcUsers();
    }

    private void initRecyclerView(){

        mAdapter = new UsersAdapter();
        mFragmentWorkMatesBinding.listWorkmates.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        mFragmentWorkMatesBinding.listWorkmates.setAdapter(mAdapter);

    }

    private void getUsers(){
        mUsersViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), this::updateUsers);
    }

    private void updateUsers(List<User>users){
        this.mAdapter.updateUsers(users);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //mUsers.clear();
    }

    private void configureOnRecyclerView(){

        ItemClickSupport.addTo(mFragmentWorkMatesBinding.listWorkmates, R.layout.fragment_work_mates)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        User user = mAdapter.getUser(position);
                        Intent intent = new Intent(getActivity(), DetailRestaurantActivity.class);
                        if(user.getChoiceId()!=null){
                            String userChoice = user.getChoiceId();
                            intent.putExtra("placeId", userChoice);
                            startActivity(intent);
                        }else{
                            Toast.makeText(requireContext(),""+user.getName()+" n'a pas choisi aujourd'hui", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Available workmates");
    }
}