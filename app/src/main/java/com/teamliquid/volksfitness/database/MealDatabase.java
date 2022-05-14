package com.teamliquid.volksfitness.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.teamliquid.volksfitness.dao.MealDao;
import com.teamliquid.volksfitness.pojo.Meal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Meal.class}, version = 1, exportSchema = false)
public abstract class MealDatabase  extends RoomDatabase {
    public abstract MealDao mealDao();
    private static MealDatabase INSTANCE;


    //we create an ExecutorService with a fixed thread pool so we can later run database
    //operations asynchronously on a background thread.
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    //A synchronized method in a multi threaded environment means that two threads are
    // not allowed to access data at the same time
    public static synchronized MealDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MealDatabase.class, "MealDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }


}
