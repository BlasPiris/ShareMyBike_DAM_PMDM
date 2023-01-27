package com.BlasPiris.sharemybike.activities.ui.availableBikes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.BlasPiris.sharemybike.adapters.MyItemRecyclerViewAdapter;
import com.BlasPiris.sharemybike.bikes.BikesContent;
import com.example.sharemybike.databinding.FragmentAvailablebikesBinding;

public class AvailableBikesFragment extends Fragment {

    private FragmentAvailablebikesBinding binding;
    private int mColumnCount = 1;
    BikesContent bikesContent;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        bikesContent=new BikesContent();
        bikesContent.loadBikesFromJSON(getContext());
        AvailableBikesViewModel availableViewModel =
                new ViewModelProvider(this).get(AvailableBikesViewModel.class);

        binding = FragmentAvailablebikesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        availableViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(bikesContent.ITEMS));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}