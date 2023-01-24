package com.BlasPiris.sharemybike;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.BlasPiris.sharemybike.bikes.BikesContent;
import com.example.sharemybike.R;
import com.example.sharemybike.databinding.ActivityBikeBinding;

public class BikeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBikeBinding binding;
    private BikesContent bc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBikeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bike);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //CARGA DE LOS DATOS DEL JSON
        bc=new BikesContent();
        bc.loadBikesFromJSON(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bike);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //METODO QUE RECOGE LA FECHA DEL CALENDARIO Y LO GUARDA EN LA CLASE BIKES cONTENT
    public void setDate(String date) {
        bc.setSelectedDate(date);
    }
}