package com.teamliquid.volksfitness.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.adapter.MealAdapter;
import com.teamliquid.volksfitness.databinding.FragmentFoodIntakeBinding;
import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.viewmodel.MealViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodIntakeFragment extends Fragment {
    private FragmentFoodIntakeBinding binding;
    private MealViewModel mealViewModel;
    private MealAdapter adapter;

    public FoodIntakeFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding  = FragmentFoodIntakeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView;

        //Initialization view model at Application level
        mealViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MealViewModel.class);


        //List<Meal> mealList = mealViewModel.getAllMeals().getValue();
        List<Meal> mealList = new ArrayList<>();
        mealList.add(new Meal("Demo","Demo",100,88888888));
        // Make recycler view usable
        // get Activity in Fragment, this in Activity.
        adapter = new MealAdapter(getActivity(),mealList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        // Inform the Adapter when database changed.
        mealViewModel.getAllMeals().observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                adapter.refreshMeal(meals);
            }
        });

        // Delete object in database when click the delete button
        adapter.setOnItemClickListener(new MealAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mealViewModel.delete(adapter.getCurrentMeal(position));
            }
        });

        binding.floatAdd.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_add_meal_fragment,null));

        return view;
    }
}
