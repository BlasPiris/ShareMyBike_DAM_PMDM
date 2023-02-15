package com.BlasPiris.sharemybike.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.BlasPiris.sharemybike.activities.MainPanelActivity;
import com.BlasPiris.sharemybike.pojos.Bike;
import com.BlasPiris.sharemybike.pojos.User;
import com.bumptech.glide.Glide;
import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentItemBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    Context context;
    private final List<Bike> mValues;
    private final String date;
    MainPanelActivity mpa;

    public MyItemRecyclerViewAdapter(List<Bike> items, String selectedDate,MainPanelActivity mpa) {
        mValues = items;
        date=selectedDate;
        this.mpa=mpa;
    }


    public MyItemRecyclerViewAdapter(List<Bike> items) {
        mValues = items;
        date=null;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        context=parent.getContext();
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String owner=mValues.get(position).getOwner();
        String location=mValues.get(position).getLocation();
        String city=mValues.get(position).getCity();
        String email=mValues.get(position).getEmail();
        String image=mValues.get(position).getImage();
        holder.txtCity.setText(city);
        holder.txtDescription.setText(mValues.get(position).getDescription());
        holder.txtLocation.setText(location);
        holder.txtOwner.setText(owner);
            if(image!=null) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("bikes/" + image);

                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Cargar la imagen en una vista de imagen usando Glide
                        ImageView imageView = holder.imgBike;
                        Glide.with(context.getApplicationContext())
                                .load(uri.toString())
                                .into(imageView);
                    }
                });
            }

                if(date!=null){
                    holder.booking.setText("Booking");
                    holder.booking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            User user=User.getInstance();

                        mpa.getUserBooking().setUserId(user.getUid());
                        mpa.getUserBooking().setUserEmail(user.getEmail());
                        mpa.getUserBooking().setBikeEmail(email);
                        mpa.getUserBooking().setBikeCity(city);

                        mpa.getUserBooking().addToDatabase();

                        Toast.makeText(context, "Bike booked successfully", Toast.LENGTH_SHORT).show();

                        }
                    });
                }else{
                    holder.booking.setText("Date");
                    holder.booking.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //CAMBIAMOS EL FRAGMENT
                            Navigation.createNavigateOnClickListener(R.id.action_AvailableBikes_to_DateSelectionFragment).onClick(holder.booking);

                        }
                    });
                }









        //AÑADIMOS EVENTO AL CORREO PARA QUE ENVIE EL CORREO ELECTRÓNICO
        holder.mBtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            sendEmail(email,owner,location,city);
                }

        });


    }

    //METODO QUE NOS ENVIA AL INTENT DEL CORREO ELECTRÓNICO
    private void sendEmail(String email, String owner, String location, String city) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "ShareMyBike");

        if(date!=null){
            i.putExtra(Intent.EXTRA_TEXT   , "Dear Mr/Mrs "+owner+" :\n" +
                    "I'd like to use your bike at "+location+" ("+city+")\n" +
                    "for the following date: "+date+"\n" +
                    "Can you confirm its availability?\n" +
                    "Kindest regards");
        }else{
            i.putExtra(Intent.EXTRA_TEXT   , "Dear Mr/Mrs "+owner+" :\n" +
                    "I'd like to use your bike at "+location+" ("+city+")\n" +
                    "Can you confirm its availability?\n" +
                    "Kindest regards");
        }
            context.startActivity(Intent.createChooser(i, "Send mail..."));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton mBtnImg;
        Button booking;
        ImageView imgBike;
        TextView txtCity,txtDescription, txtLocation, txtOwner;
        Bike mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mBtnImg= binding.btnMail;
            imgBike=binding.imgPhoto;
            txtCity=binding.txtCity;
            txtDescription=binding.txtDescription;
            txtLocation=binding.txtLocation;
            txtOwner=binding.txtOwner;
            booking=binding.booking;





        }

    }



}