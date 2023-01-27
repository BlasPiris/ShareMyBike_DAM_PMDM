package com.BlasPiris.sharemybike.activities.ui.registerBike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharemybike.databinding.FragmentRegisterbikeBinding;

public class RegisterBikeFragment extends Fragment {

    private FragmentRegisterbikeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegisterBikeViewModel availableViewModel =
                new ViewModelProvider(this).get(RegisterBikeViewModel.class);

        binding = FragmentRegisterbikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        availableViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}