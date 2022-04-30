package com.teamliquid.volksfitness.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teamliquid.volksfitness.databinding.FragmentAccountSettingBinding;

public class AccountSettingFragment extends Fragment {
    private FragmentAccountSettingBinding binding;

    public AccountSettingFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountSettingBinding.inflate(inflater,container,false);
        View view = binding.getRoot();






        return view;
    }
}
