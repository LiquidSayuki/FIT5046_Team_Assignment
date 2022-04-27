package com.teamliquid.volksfitness.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamliquid.volksfitness.databinding.FragmentFoodIntakeBinding;

public class FoodIntakeFragment extends Fragment {
    private FragmentFoodIntakeBinding binding;

    public FoodIntakeFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentFoodIntakeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        return view;
    }
}
