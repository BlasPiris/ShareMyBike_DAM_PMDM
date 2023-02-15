package com.BlasPiris.sharemybike.activities.ui.availableMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentAvailablemapBinding;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AvailableMapFragment extends Fragment {

    private FragmentAvailablemapBinding binding;

    DatabaseReference mDatabase;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            GoogleMap mMap = googleMap;

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            PolylineOptions poly=new PolylineOptions();
            mDatabase= FirebaseDatabase.getInstance().getReference();
            //GENERAMOS LOS RECYCLERVIEW DE LAS BICICLETAS
            mDatabase.child("bikes_list").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot=task.getResult();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Double latitude=ds.child("latitude").getValue(Double.class);
                            Double longitude=ds.child("longitude").getValue(Double.class);
                            String city=ds.child("city").getValue(String.class);
                            String owner=ds.child("owner").getValue(String.class);
                            LatLng ll = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
                            mMap.addMarker(new MarkerOptions().position(ll).title(city)
                                    .snippet(owner));
                            builder.include(ll);
                            poly.add(ll);
                            mMap.addPolyline(poly);
                            LatLngBounds bounds=builder.build();
                            CameraUpdate cu= CameraUpdateFactory.newLatLngBounds(bounds,100);
                            mMap.animateCamera(cu);
                        }

                    }
                }});






        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAvailablemapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}