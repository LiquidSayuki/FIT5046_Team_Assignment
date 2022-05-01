package com.teamliquid.volksfitness.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.databinding.FragmentReportBinding;

public class ReportFragment extends Fragment {
    private FragmentReportBinding binding;


    public ReportFragment (){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding  = FragmentReportBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        // This is single date picker
        // DialogFragment newFragment = new DatePickerFragment();
        // newFragment.show(getActivity().getSupportFragmentManager(),"date picker");

        // This is date range picker
        MaterialDatePicker picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build();
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());

        binding.textReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), picker.getSelection().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
