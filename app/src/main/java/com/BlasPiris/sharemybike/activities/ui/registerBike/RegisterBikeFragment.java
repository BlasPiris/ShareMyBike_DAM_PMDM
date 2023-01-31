package com.BlasPiris.sharemybike.activities.ui.registerBike;

import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentRegisterbikeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterBikeFragment extends Fragment {

    private FragmentRegisterbikeBinding binding;

    FirebaseAuth mAuth;

    TextView userInfo;

    Location ultimaUbicacion;



    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRegisterbikeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth = FirebaseAuth.getInstance();

        userInfo=root.findViewById(R.id.nameUser);
        String name=mAuth.getCurrentUser().getDisplayName();
        String email=mAuth.getCurrentUser().getEmail();
        userInfo.setText("Bike of "+name+" ("+email+")");

        //OBTENER LOCALIZACION

        solicitaUbicacion();



        //AÑADIR IMAGEN
        binding.imageViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //GUARDAR BICI
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CAMBIAMOS EL FRAGMENT
                NavHostFragment.findNavController(RegisterBikeFragment.this)
                        .navigate(R.id.AvailableBikes);
            }
        });


        return root;
    }

    private void solicitaUbicacion() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}