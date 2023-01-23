package com.example.sharemybike;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharemybike.bikes.BikesContent;
import com.example.sharemybike.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.sharemybike.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    Context context;
    private final List<BikesContent.Bike> mValues;
    private final String date;
    

    public MyItemRecyclerViewAdapter(List<BikesContent.Bike> items, String selectedDate) {
        mValues = items;
        date=selectedDate;

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
        holder.txtCity.setText(city);
        holder.txtDescription.setText(mValues.get(position).getDescription());
        holder.txtLocation.setText(location);
        holder.txtOwner.setText(owner);

       holder.imgBike.setImageBitmap(mValues.get(position).getPhoto());



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
        i.putExtra(Intent.EXTRA_TEXT   , "Dear Mr/Mrs "+owner+" :\n" +
                "I'd like to use your bike at "+location+" ("+city+")\n" +
                "for the following date: "+date+"\n" +
                "Can you confirm its availability?\n" +
                "Kindest regards");

            context.startActivity(Intent.createChooser(i, "Send mail..."));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton mBtnImg;
        ImageView imgBike;
        TextView txtCity,txtDescription, txtLocation, txtOwner;



        public BikesContent.Bike mItem;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            mBtnImg= binding.btnMail;
            imgBike=binding.imgPhoto;
            txtCity=binding.txtCity;
            txtDescription=binding.txtDescription;
            txtLocation=binding.txtLocation;
            txtOwner=binding.txtOwner;



//            mContentView = binding.content;
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }



}