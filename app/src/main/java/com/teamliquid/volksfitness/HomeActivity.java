package com.teamliquid.volksfitness;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.teamliquid.volksfitness.databinding.ActivityHomeBinding;
import com.teamliquid.volksfitness.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.appBarHome);

        mAppBarConfiguration = new AppBarConfiguration
                .Builder(R.id.nav_home_fragment,
                R.id.nav_calendar_fragment,
                R.id.nav_food_intake_fragment,
                R.id.nav_report_fragment)
                .setOpenableLayout(binding.drawerLayoutHome)
                .build();

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBarHome,navController,mAppBarConfiguration);

    }
}
