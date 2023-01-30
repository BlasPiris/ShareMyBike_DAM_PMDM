package com.BlasPiris.sharemybike.activities.ui.registerBike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentRegisterbikeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterBikeFragment extends Fragment {

    private FragmentRegisterbikeBinding binding;

    FirebaseAuth mAuth;

    TextView userInfo;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRegisterbikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        userInfo=root.findViewById(R.id.nameUser);
        String name=mAuth.getCurrentUser().getDisplayName();
        String email=mAuth.getCurrentUser().getEmail();
        userInfo.setText("Bike of "+name+" ("+email+")");




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}