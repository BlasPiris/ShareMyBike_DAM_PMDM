package com.BlasPiris.sharemybike.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.BlasPiris.sharemybike.pojos.UserBooking;
import com.example.sharemybike.R;
import com.example.sharemybike.databinding.ActivityMainPanelBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainPanelActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 100 ;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainPanelBinding binding;
    private UserBooking userBooking;
    ImageView imageView;
    Bitmap image;

    public UserBooking getUserBooking() {
        return userBooking;
    }

    public void setUserBooking(UserBooking userBooking) {
        this.userBooking = userBooking;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPanelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        userBooking=new UserBooking();



        setSupportActionBar(binding.appBarMainPanel.toolbar);
        binding.appBarMainPanel.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.AvailableBikes, R.id.DateSelection, R.id.RegisterMyBike,R.id.BikesMap)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_panel);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_panel, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_panel);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //RECOGIDA FOTO DE LA CAMARA E INSERCION EN IMAGEVIEW
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("PASA");
        if (requestCode == CAMERA_PIC_REQUEST) {
            try
            {
                imageView=findViewById(R.id.imageViewRegister);
                 image = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(image);

            }catch(Exception e){
                System.out.println("ERROR "+e);
                return;
            }
        }
    }



}