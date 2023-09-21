package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.PageAdapter;
import com.example.go4lunch.databinding.ActivityDashboardBinding;
import com.example.go4lunch.ui.fragments.MapFragment;
import com.google.android.material.tabs.TabLayoutMediator;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {

    @Override
    ActivityDashboardBinding getViewBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureViewPager();

    }

    private void configureViewPager(){
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),getLifecycle());
        binding.viewpager.setAdapter(adapter);
        int[] iconList = new int[]{R.drawable.ic_baseline_map_24, R.drawable.ic_baseline_view_list_24, R.drawable.ic_baseline_people_24};

        new TabLayoutMediator(binding.tabLayout, binding.viewpager,
                ((tab, position) -> tab.setText(getResources().getStringArray(R.array.tablayout_title)[position]).setIcon(iconList[position])))
                .attach();

    }
}