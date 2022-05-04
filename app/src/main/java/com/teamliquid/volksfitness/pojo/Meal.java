package com.teamliquid.volksfitness.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "meal_table")
public class Meal {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "meal_type")
    @NonNull
    public String mealType;

    @ColumnInfo(name = "meal_food")
    @NonNull
    public String mealFood;

    @ColumnInfo(name = "meal_calories")
    @NonNull
    public int mealCalories;

    @ColumnInfo(name = "meal_time")
    @NotNull
    public long mealTime;

    public Meal(@NonNull String mealType, @NonNull String mealFood, int mealCalories, long mealTime) {
        this.mealType = mealType;
        this.mealFood = mealFood;
        this.mealCalories = mealCalories;
        this.mealTime = mealTime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public long getMealTime() {
        return mealTime;
    }

    public void setMealTime(long mealTime) {
        this.mealTime = mealTime;
    }

    @NonNull
    public String getMealType() {
        return mealType;
    }

    public void setMealType(@NonNull String mealType) {
        this.mealType = mealType;
    }

    @NonNull
    public String getMealFood() {
        return mealFood;
    }

    public void setMealFood(@NonNull String mealFood) {
        this.mealFood = mealFood;
    }

    public int getMealCalories() {
        return mealCalories;
    }

    public void setMealCalories(int mealCalories) {
        this.mealCalories = mealCalories;
    }
}
