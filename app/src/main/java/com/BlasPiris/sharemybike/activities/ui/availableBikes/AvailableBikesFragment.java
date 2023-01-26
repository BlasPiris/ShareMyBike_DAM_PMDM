package com.BlasPiris.sharemybike.activities.ui.availableBikes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.BlasPiris.sharemybike.adapters.MyItemRecyclerViewAdapter;
import com.BlasPiris.sharemybike.bikes.BikesContent;
import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentAvailablebikesBinding;

public class AvailableBikesFragment extends Fragment {

    private FragmentAvailablebikesBinding binding;

    private int mColumnCount = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


//        AvailableBikesViewModel availableViewModel =
//                new ViewModelProvider(this).get(AvailableBikesViewModel.class);
//
//        binding = FragmentAvailablebikesBinding.inflate(inflater, container, false);
      //  View view = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_availablebikes, container, false);

        //GENERAMOS LOS RECYCLERVIEW DE LAS BICICLETAS
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(BikesContent.ITEMS,BikesContent.selectedDate));
        }

        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}