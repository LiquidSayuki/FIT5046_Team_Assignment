package com.teamliquid.volksfitness;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
                .Builder(R.id.nav_account_setting_fragment,
                R.id.nav_home_fragment,
                R.id.nav_calendar_fragment,
                R.id.nav_food_intake_fragment,
                R.id.nav_report_fragment,
                R.id.nav_logout_fragment)
                .setOpenableLayout(binding.drawerLayoutHome)
                .build();

        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBarHome,navController,mAppBarConfiguration);


//        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.item_logout){
//                    FirebaseAuth.getInstance().signOut();
//                }
//                return false;
//            }
//        });

        // Set the username and email at the navigation header
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String nameString = user.getDisplayName();
            String emailString = user.getEmail();

            View header = binding.navigationView.getHeaderView(0);
            TextView usernameTV = header.findViewById(R.id.text_username_in_header);
            TextView emailTV = header.findViewById(R.id.text_email_in_header);
            usernameTV.setText(nameString);
            emailTV.setText(emailString);
        }




    }
}
