package com.teamliquid.volksfitness.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.teamliquid.volksfitness.pojo.Meal;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meal_table ORDER BY uid DESC")
    LiveData<List<Meal>> getAllMeals();

    @Insert
    void insert(Meal meal);

    @Delete
    void delete(Meal meal);
}
