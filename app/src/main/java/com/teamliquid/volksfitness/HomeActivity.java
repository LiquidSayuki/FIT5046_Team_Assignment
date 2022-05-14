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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.teamliquid.volksfitness.databinding.ActivityHomeBinding;
import com.teamliquid.volksfitness.databinding.ActivityMainBinding;
import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.viewmodel.MealViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private MealViewModel mealViewModel;

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


        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        List<Meal> mMealList = mealViewModel.getMealList();
        Map<String,Meal> mealMap = new HashMap<>();
        for(Meal meal : mMealList)
        {
            mealMap.put(String.valueOf(meal.getUid()),meal);
        }

        Gson gson = new Gson();
        String jsonString = gson.toJson(mealMap);

        Data.Builder uploadBuilder = new Data.Builder();
        Map<String,Object> placeMap = new HashMap<>();
        placeMap.put("transfer",jsonString);
        uploadBuilder.putAll(placeMap);
        Data transferToManager = uploadBuilder.build();


        WorkRequest saveRequest =
                new PeriodicWorkRequest.Builder(UploadDataToFirebaseDatabase.class,
                        1, TimeUnit.DAYS,
                        15, TimeUnit.MINUTES)
                        .setInputData(transferToManager)
                        .build();
        WorkManager.getInstance(this).enqueue(saveRequest);


    }
}
