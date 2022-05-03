package com.teamliquid.volksfitness.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;
import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.databinding.FragmentAddMealBinding;
import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.viewmodel.MealViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
                if (addMeal()){
                    Navigation.findNavController(view).navigate(R.id.nav_food_intake_fragment);
                }

            }
        });


//        binding.editTextCalorie.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                String value = binding.editTextCalorie.getText().toString();
//                if (Integer.parseInt(value)>=3500){
//                    value="3500";
//                }else if (value.isEmpty()){
//                    value="0";
//                }else if (Integer.parseInt(value)<=0){
//                    value="0";
//                }
//                binding.sliderCalorie.setValue(Integer.parseInt(value));
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                String value = binding.editTextCalorie.getText().toString();
//                if (Integer.parseInt(value)>=3500){
//                    value="3500";
//                }else if (value.isEmpty()){
//                    value="0";
//                }else if (Integer.parseInt(value)<=0){
//                    value="0";
//                }
//                binding.sliderCalorie.setValue(Integer.parseInt(value));
//            }
//        });

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

        binding.buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is single date picker
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getActivity().getSupportFragmentManager(),"date picker");
                MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                        .build();
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override public void onPositiveButtonClick(Long selection) {
                        // parse the value from millis to formatted date, you can use ZonedDateTime
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String date = formatter.format(selection);
                        binding.textDate.setText(date);
                    }
                });
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



    private boolean addMeal(){
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

            showDiyToast(getContext(),"New meal added",R.drawable.ic_baseline_check_circle_24);

            return true;
        }else {
            showDiyToast(getContext(),"Adding Meal Failed",R.drawable.ic_baseline_cancel_24);
            return false;
        }
    }

    // https://blog.csdn.net/yinzhijiezhan/article/details/100892184
    // Thanks for "yzjgogo" providing the idea of diy toast
    // And I make it more diy-able.
    private void showDiyToast(Context context,String content,int imageId){
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        View layout = View.inflate(context,R.layout.toast_layout,null);

        ImageView imageView = layout.findViewById(R.id.image_toast);
        imageView.setImageResource(imageId);

        TextView textView = layout.findViewById(R.id.text_toast);
        textView.setText(content);

        toast.setView(layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
