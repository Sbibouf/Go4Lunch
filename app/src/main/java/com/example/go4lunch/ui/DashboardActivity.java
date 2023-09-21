package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.PageAdapter;
import com.example.go4lunch.databinding.ActivityDashboardBinding;
import com.example.go4lunch.ui.fragments.MapFragment;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    @Override
    ActivityDashboardBinding getViewBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.configureViewPager();

    }

    private void configureViewPager(){
        binding.viewpager.setAdapter(new PageAdapter(getSupportFragmentManager(),getLifecycle()) {
        });
    }
}