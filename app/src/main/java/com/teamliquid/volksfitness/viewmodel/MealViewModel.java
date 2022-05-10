package com.teamliquid.volksfitness.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.repo.MealRepository;

import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private MealRepository mRepository;
    private LiveData<List<Meal>> allMeals;

    public MealViewModel (Application application){
        super(application);
        mRepository = new MealRepository(application);
        allMeals = mRepository.getAllMeals();
    }

    public LiveData<List<Meal>> getAllMeals(){
        return allMeals;
    }

    public List<Meal> getMealList() { return mRepository.getMealList(); }

    public void insert(Meal meal){
        mRepository.insert(meal);
    }

    public void delete(Meal meal){
        mRepository.delete(meal);
    }
}
