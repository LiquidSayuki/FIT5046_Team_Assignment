package com.teamliquid.volksfitness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.databinding.FragmentCalendarBinding;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;

    public CalendarFragment (){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentCalendarBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.calenderButton.setOnClickListener( v ->{
            Calendar cal = Calendar.getInstance();
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("beginTime", cal.getTimeInMillis());
            intent.putExtra("allDay", true);
            intent.putExtra("rrule","FREQ=DAILY");
            intent.putExtra("endTime",cal.getTimeInMillis() + 60*60*1000);
            intent.putExtra("title","test-event");
            startActivity(intent);
        });

        return view;
    }
}
