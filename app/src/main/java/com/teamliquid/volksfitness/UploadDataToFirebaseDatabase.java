package com.teamliquid.volksfitness;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.viewmodel.MealViewModel;

import java.lang.reflect.Type;
import java.util.Map;

public class UploadDataToFirebaseDatabase extends Worker {

    public UploadDataToFirebaseDatabase(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        String dataTransfer = getInputData().getString("transfer");
        System.out.println(dataTransfer);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fit5046-a61f5-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference reference = database.getReference("Meal");

        Gson gson = new Gson();
        Type mealMapType = new TypeToken<Map<String, Meal>>() {}.getType();

        Map<String, Meal> mealMap = gson.fromJson(dataTransfer,mealMapType);

        reference.setValue(mealMap);
        return  Result.success();
    }
}
