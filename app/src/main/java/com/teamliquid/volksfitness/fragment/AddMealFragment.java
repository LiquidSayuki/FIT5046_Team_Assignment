package com.teamliquid.volksfitness.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.teamliquid.volksfitness.databinding.FragmentAddMealBinding;
import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.viewmodel.MealViewModel;

public class AddMealFragment extends Fragment {
    private FragmentAddMealBinding binding;
    private MealViewModel mealViewModel;

    public AddMealFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentAddMealBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        mealViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MealViewModel.class);

        binding.buttonAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMeal();
            }
        });


        binding.editTextCalorie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String value = binding.editTextCalorie.getText().toString();
                binding.sliderCalorie.setValue(Integer.parseInt(value));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = binding.editTextCalorie.getText().toString();
                binding.sliderCalorie.setValue(Integer.parseInt(value));
            }
        });

        binding.sliderCalorie.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                int i = (int) binding.sliderCalorie.getValue();
                binding.editTextCalorie.setText(String.valueOf(i));
            }
        });

        return view;
    }

    private boolean checkDish(){
        boolean isNull = binding.textInputFood.getEditText().getText().toString().isEmpty();
        if(isNull){
            binding.textInputFood.setError("Food can't be empty");
            binding.textInputFood.setErrorEnabled(true);
        }else{
            binding.textInputFood.setErrorEnabled(false);
        }
        return !isNull;
    }

    private void addMeal(){
        if (checkDish()){
            int chipID = binding.chipGroup.getCheckedChipId();
            Chip chip = binding.chipGroup.findViewById(chipID);
            String mealType = chip.getText().toString();
            String mealFood = binding.textInputFood.getEditText().getText().toString() + "\n" +
                    binding.textInputDrink.getEditText().getText().toString() + "\n" +
                    binding.textInputDessert.getEditText().getText().toString();
            int calories = Integer.parseInt(binding.editTextCalorie.getText().toString());
            Meal meal = new Meal(mealType,mealFood,calories);
            mealViewModel.insert(meal);
        }
    }

}
