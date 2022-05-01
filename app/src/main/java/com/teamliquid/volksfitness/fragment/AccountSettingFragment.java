package com.teamliquid.volksfitness.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.databinding.FragmentAccountSettingBinding;

import java.util.ArrayList;
import java.util.List;

public class AccountSettingFragment extends Fragment {
    private FragmentAccountSettingBinding binding;

    public AccountSettingFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountSettingBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        // Put everything in the if user not null
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            // boolean emailVerified = user.isEmailVerified();

            // TODO: Set the username and email

            // Initialize the gender drop down menu
            String[] gender = getResources().getStringArray(R.array.gender);
            ArrayAdapter adapter = new ArrayAdapter(getContext(),R.layout.item_dropdown,gender);
            adapter.setDropDownViewResource(R.layout.item_dropdown);
            binding.autoCompleteGender.setAdapter(adapter);

            // TODO: set the selected gender by data in the firebase

            // TODO: set the value in the status box by data in the firebase

            // TODO: get value of gender box

            // TODO: get value of Status box

            // TODO: submit data
            binding.buttonUserinfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), name+email+photoUrl, Toast.LENGTH_SHORT).show();
                }
            });

            // TODO: discard data


        }
        return view;
    }
}
