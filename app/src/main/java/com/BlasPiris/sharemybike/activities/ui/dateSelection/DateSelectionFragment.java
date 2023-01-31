package com.BlasPiris.sharemybike.activities.ui.dateSelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.BlasPiris.sharemybike.activities.MainPanelActivity;
import com.example.sharemybike.R;
import com.example.sharemybike.databinding.FragmentDateselectionBinding;

import java.util.Calendar;

public class DateSelectionFragment extends Fragment {

    private FragmentDateselectionBinding binding;
    private  MainPanelActivity mpa;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDateselectionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mpa= (MainPanelActivity) getActivity();




        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mpa.getUserBooking().getBookDate()!=null){
            setDateToCalendar(mpa);

        }



        //EVENTO QUE RECOGE CADA VEZ QUE SE CAMBIA LA FECHA EN EL CALENDARIO
        binding.calendarViewDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //CAMBIAMOS EL TEXTVIEW
                binding.textviewDate.setText("DATE: " + dayOfMonth + " / " + (month + 1) + " / " + year);

                //ACTIVAMOS EL BOTON
                binding.btnDate.setEnabled(true);
                //MANDAMOS LA FECHA SELECCIONADA AL BIKE ACTIVITY
                mpa.getUserBooking().setBookDate(dayOfMonth + " / " + (month + 1) + " / " + year);
            }
        });
        //
        binding.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CAMBIAMOS EL FRAGMENT
                NavHostFragment.findNavController(DateSelectionFragment.this)
                        .navigate(R.id.AvailableBikes);
            }
        });
    }

    //METODO QUE PONE LA FECHA UNA VEZ QUE HAYAMOS SELECIONADO UNA FECHA YA ANTERIORMENTE
    private void setDateToCalendar(MainPanelActivity mpa) {
        String date = mpa.getUserBooking().getBookDate();
        binding.textviewDate.setText("DATE: " +date );

        String parts[] = date.split(" / ");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1])-1;
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        long milliTime = calendar.getTimeInMillis();
        binding.calendarViewDate.setDate (milliTime, true, true);
        binding.btnDate.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}