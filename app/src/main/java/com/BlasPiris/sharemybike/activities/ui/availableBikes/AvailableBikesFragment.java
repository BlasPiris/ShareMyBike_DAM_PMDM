package com.BlasPiris.sharemybike.activities.ui.availableBikes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.BlasPiris.sharemybike.adapters.MyItemRecyclerViewAdapter;
import com.BlasPiris.sharemybike.bikes.BikesContent;
import com.BlasPiris.sharemybike.pojos.Bike;
import com.example.sharemybike.databinding.FragmentAvailablebikesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AvailableBikesFragment extends Fragment {

    private FragmentAvailablebikesBinding binding;
    private int mColumnCount = 1;
    BikesContent bikesContent;
    DatabaseReference mDatabase;
    List<Bike> bikes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        bikesContent=new BikesContent();
        bikesContent.loadBikesFromJSON(getContext());

        mDatabase= FirebaseDatabase.getInstance().getReference();

        binding = FragmentAvailablebikesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         bikes=new ArrayList<>();
        //GENERAMOS LOS RECYCLERVIEW DE LAS BICICLETAS
        mDatabase.child("bike_list").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {


                    DataSnapshot dataSnapshot=task.getResult();
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Bike newBike=new Bike();
                        newBike.setCity(ds.child("city").getValue(String.class));
                        newBike.setEmail(ds.child("email").getValue(String.class));
                        newBike.setDescription(ds.child("description").getValue(String.class));
                        newBike.setImage(ds.child("image").getValue(String.class));
                        newBike.setLatitude(ds.child("latitude").getValue(Double.class));
                        newBike.setLongitude(ds.child("setLongitude").getValue(Double.class));
                        newBike.setLocation(ds.child("location").getValue(String.class));
                        newBike.setOwner(ds.child("owner").getValue(String.class));
                        newBike.setPhotoInBitmap(ds.child("photo").getValue(String.class),getContext());
                        bikes.add(newBike);
                    }



                }
                else {

                    System.out.println( "Error getting data");
                }
            }
        });
        // Set the adapter
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(bikes));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}