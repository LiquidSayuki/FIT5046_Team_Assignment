package com.teamliquid.volksfitness.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.teamliquid.volksfitness.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {
    private FragmentLogoutBinding binding;

    public LogoutFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentLogoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        FirebaseAuth.getInstance().signOut();

        return view;
    }
}

