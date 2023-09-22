package com.example.go4lunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.PageAdapter;
import com.example.go4lunch.databinding.ActivityDashboardBinding;
import com.example.go4lunch.ui.fragments.ListFragment;
import com.example.go4lunch.ui.fragments.MapFragment;
import com.example.go4lunch.ui.fragments.WorkMatesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayoutMediator;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> implements NavigationBarView.OnItemSelectedListener{

    MapFragment mMapFragment = new MapFragment();
    ListFragment mListFragment = new ListFragment();
    WorkMatesFragment mWorkMatesFragment = new WorkMatesFragment();

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    ActivityDashboardBinding getViewBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.bottomNavigationView.setOnItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.map);
      //  this.configureViewPager();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, mMapFragment)
                        .commit();
                return true;

            case R.id.list:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, mListFragment)
                        .commit();
                return true;

            case R.id.workmates:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, mWorkMatesFragment)
                        .commit();
                return true;
        }
        return false;
    }




    // private void configureViewPager(){
   //     PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),getLifecycle());
   //     binding.viewpager.setAdapter(adapter);
   //     int[] iconList = new int[]{R.drawable.ic_baseline_map_24, R.drawable.ic_baseline_view_list_24, R.drawable.ic_baseline_people_24};
    //
      //  new TabLayoutMediator(binding.tabLayout, binding.viewpager,
        //        ((tab, position) -> tab.setText(getResources().getStringArray(R.array.tablayout_title)[position]).setIcon(iconList[position])))
          //      .attach();

    //}
}