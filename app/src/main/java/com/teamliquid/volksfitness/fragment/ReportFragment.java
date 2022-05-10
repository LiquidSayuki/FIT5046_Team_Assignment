package com.teamliquid.volksfitness.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.teamliquid.volksfitness.database.MealDatabase;
import com.teamliquid.volksfitness.databinding.FragmentReportBinding;
import com.teamliquid.volksfitness.pojo.Meal;
import com.teamliquid.volksfitness.viewmodel.MealViewModel;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {
    private FragmentReportBinding binding;
    private MealViewModel mealViewModel;

    public ReportFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentReportBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


//        mealViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MealViewModel.class);


        // This is single date pickerØ
//        MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
//                .setTitleText("Select dates")
//                .build();
//        picker.show(getActivity().getSupportFragmentManager(), picker.toString());

        // This is date range picker
        MaterialDatePicker picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setTheme(com.google.android.material.R.style.ThemeOverlay_Material3_MaterialCalendar)
                .build();
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // Get selected start date & end date
                Pair selectedDates = (Pair) picker.getSelection();
                final Pair<Long, Long> rangeDate = new Pair<>((Long) selectedDates.first, (Long) selectedDates.second);
                Long startDate = rangeDate.first;
                Long endDate = rangeDate.second;
                GenerateReport(startDate, endDate);
            }

        });


//        binding.textReport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), picker.getSelection().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


        return view;
    }

    private void GenerateReport(Long startDate, Long endDate) {
        // As the main tread can not access the database, so use AsyncTask
        // idea from https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Meal> meals = MealDatabase.getInstance(getContext()).mealDao().getMealList();
                generateReport(meals, startDate, endDate);
            }
        });
    }

    private void generateReport(List<Meal> meals, long startDate, long endDate) {
        //Use entry to store the point which both types of attribute are float
        List<Entry> mealList= new ArrayList<>();
        //Check meal list size
        if (meals != null){
            for(int index = 0; index < meals.size(); index ++){
                // find records between selected date
                if (meals.get(index).getMealTime() >= startDate && meals.get(index).getMealTime() <= endDate){
                    if (index != 0) {
                        // records on same day
                        if (meals.get(index).getMealTime() == meals.get(index - 1).getMealTime()) {
                            mealList.get(mealList.size() - 1).setY(mealList.get(mealList.size() - 1).getY() + meals.get(index).getMealCalories());
                        }
                        else {
                            mealList.add(new Entry(meals.get(index).getMealTime(), meals.get(index).getMealCalories()));
                        }
                    }
                    else {
                        mealList.add(new Entry(meals.get(index).getMealTime(), meals.get(index).getMealCalories()));
                    }
                }

            }
            if (mealList.size() == 0){
                binding.textChart.setText("No match record");
            }
            else{
                generateLineChart(mealList);
            }
        }
        // idea from https://learntodroid.com/how-to-display-a-line-chart-in-your-android-app/
        else{
            binding.textChart.setText("No Calories record");
        }
    }

    private void generateLineChart(List<Entry> mealList) {
        DateFormat formatter = new SimpleDateFormat("dd/MM");
        LineDataSet lineDataSet = new LineDataSet(mealList, "Calories");
        LineData lineData = new LineData(lineDataSet);
        binding.lineChart.setData(lineData);
        binding.lineChart.setVisibility(View.VISIBLE);

        Description desc = new Description();
        desc.setText("Calories Report");
        desc.setTextSize(22);
        binding.lineChart.setDescription(desc);

        //idea from https://learntodroid.com/how-to-display-a-line-chart-in-your-android-app/
        // Set X rays to date from float value
        XAxis xAxis = binding.lineChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return formatter.format(value);
            }
        });
        binding.lineChart.invalidate();
    }
}


