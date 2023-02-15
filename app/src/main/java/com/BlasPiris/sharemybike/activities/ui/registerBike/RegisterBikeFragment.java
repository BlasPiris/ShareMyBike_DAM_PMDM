package com.BlasPiris.sharemybike.activities.ui.registerBike;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.BlasPiris.sharemybike.pojos.Bike;
import com.BlasPiris.sharemybike.pojos.User;
import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentRegisterbikeBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterBikeFragment extends Fragment {

    private FragmentRegisterbikeBinding binding;

    FirebaseAuth mAuth;
    Location ultimaUbicacion;

    TextView userInfo;
    EditText lat,lon;
    
    int CAMERA_PIC_REQUEST=100;
    boolean changeImage=false;




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
        lat=root.findViewById(R.id.latitude);
        lon=root.findViewById(R.id.longitude);

        ultimaUbicacion=setLocation();

        lat.setText(String.valueOf(ultimaUbicacion.getLatitude()));
        lon.setText(String.valueOf(ultimaUbicacion.getLongitude()));



        //AÑADIR IMAGEN
        binding.imageViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                changeImage=true;
            }
        });



        //GUARDAR BICI
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBike(binding);
                //CAMBIAMOS EL FRAGMENT
                NavHostFragment.findNavController(RegisterBikeFragment.this)
                        .navigate(R.id.AvailableBikes);
            }
        });


        return root;
    }






    private Location setLocation() {
     
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
          return location;
        }else{
            return null;
        }
    }

    private void saveBike(FragmentRegisterbikeBinding binding) {

        Bike newBike=new Bike();
        User user= User.getInstance();

        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        String key= database.child("bikes_list").push().getKey();
        Map<String, Object> childUpdates = new HashMap<>();


        newBike.setOwner(user.getName());
        newBike.setEmail(user.getEmail());
        newBike.setLongitude(Double.valueOf(String.valueOf(binding.longitude.getText())));
        newBike.setLatitude(Double.valueOf(String.valueOf(binding.latitude.getText())));
        newBike.setLocation(String.valueOf(binding.address.getText()));
        newBike.setCity(String.valueOf(binding.city.getText()));
        newBike.setDescription(String.valueOf(binding.description.getText()));


        if(changeImage){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("bikes");
        Bitmap bitmap = ((BitmapDrawable) binding.imageViewRegister.getDrawable()).getBitmap();
        String fileName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child(fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });

        newBike.setImage(fileName);
        }else{
        newBike.setImage(null);
        }
        database.child("bikes_list/"+key).setValue(newBike);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}