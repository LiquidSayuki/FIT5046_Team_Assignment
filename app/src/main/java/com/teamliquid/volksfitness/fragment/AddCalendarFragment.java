package com.teamliquid.volksfitness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.teamliquid.volksfitness.databinding.FragmentAddCalenderBinding;
import com.teamliquid.volksfitness.databinding.FragmentAddMealBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddCalendarFragment extends Fragment {

    private FragmentAddCalenderBinding binding;

    public AddCalendarFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddCalenderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(datePicker.getSelection());
        binding.textDate.setText(date);

        String frequency = binding.editFrequency.getText().toString();
        String title = binding.editEventTitle.getText().toString();


        binding.buttonEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is single date picker
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getActivity().getSupportFragmentManager(),"date picker");
                datePicker.show(getActivity().getSupportFragmentManager(), "date picker");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override public void onPositiveButtonClick(Long selection) {
                        // parse the value from millis to formatted date, you can use ZonedDateTime
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String eventTime = formatter.format(datePicker.getSelection());
                        binding.textDate.setText(eventTime);
                    }
                });
            }
        });



        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                System.out.println(binding.textDate.getText().toString());

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                Date dateEnd = null;
                try {
                    date = formatter.parse(binding.textDate.getText().toString());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long millisStart = date.getTime();
                long millisEnd = date.getTime() + 3600;
                intent.putExtra("beginTime", millisStart);
                intent.putExtra("allDay", true);
                intent.putExtra("rrule","FREQ=DAILY");
                intent.putExtra("endTime",millisEnd);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });



        return view;
    }

}
