package com.BlasPiris.sharemybike.activities.ui.availableBikes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AvailableBikesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AvailableBikesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AVAILABLE BIKES fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}