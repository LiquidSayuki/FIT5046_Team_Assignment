package com.teamliquid.volksfitness.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.teamliquid.volksfitness.dao.MealDao;
import com.teamliquid.volksfitness.database.MealDatabase;
import com.teamliquid.volksfitness.pojo.Meal;

import java.util.List;

public class MealRepository {
    private MealDao mealDao;
    private LiveData<List<Meal>> allMeals;

    //Initialisation
    public MealRepository (Application application){
        MealDatabase db = MealDatabase.getInstance(application);
        mealDao =db.mealDao();
        allMeals = mealDao.getAllMeals();
    }

    //get all
    public LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public List<Meal> getMealList() { return mealDao.getMealList(); }

    //insert one
    public void insert(final Meal meal) {
        MealDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mealDao.insert(meal);
            }
        });
    }

    //delete one
    public void delete(final Meal meal){
        MealDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mealDao.delete(meal);
            }
        });
    }
}