package com.BlasPiris.sharemybike.activities.ui.dateSelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharemybike.databinding.FragmentDateselectionBinding;

public class DateSelectionFragment extends Fragment {

    private FragmentDateselectionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DateSelectionViewModel availableViewModel =
                new ViewModelProvider(this).get(DateSelectionViewModel.class);

        binding = FragmentDateselectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textGallery;
//        availableViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        BikeActivity activity = (BikeActivity) getActivity();

        //EVENTO QUE RECOGE CADA VEZ QUE SE CAMBIA LA FECHA EN EL CALENDARIO
        binding.calendarViewDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //CAMBIAMOS EL TEXTVIEW
                binding.textviewDate.setText("DATE: " + dayOfMonth + " / " + (month + 1) + " / " + year);
                //ACTIVAMOS EL BOTON
                binding.btnDate.setEnabled(true);
                //MANDAMOS LA FECHA SELECCIONADA AL BIKE ACTIVITY
//                activity.setDate(dayOfMonth + " / " + (month + 1) + " / " + year);
            }
        });

        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CAMBIAMOS EL FRAGMENT
//                NavHostFragment.findNavController(DateSelectionFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}