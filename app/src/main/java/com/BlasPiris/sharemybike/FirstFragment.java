package com.BlasPiris.sharemybike;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BikeActivity activity = (BikeActivity) getActivity();

        //EVENTO QUE RECOGE CADA VEZ QUE SE CAMBIA LA FECHA EN EL CALENDARIO
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //CAMBIAMOS EL TEXTVIEW
                binding.textviewFirst.setText("DATE: "+dayOfMonth+" / "+(month+1)+" / "+year);
                //ACTIVAMOS EL BOTON
                binding.btnCalendar.setEnabled(true);
                //MANDAMOS LA FECHA SELECCIONADA AL BIKE ACTIVITY
                activity.setDate(dayOfMonth+" / "+(month+1)+" / "+year);
            }
        });

        binding.btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CAMBIAMOS EL FRAGMENT
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}