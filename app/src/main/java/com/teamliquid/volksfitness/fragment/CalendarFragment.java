package com.teamliquid.volksfitness.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.teamliquid.volksfitness.HomeActivity;
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

        binding.calenderButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_add_calendar_fragment));


        return view;
    }
}
