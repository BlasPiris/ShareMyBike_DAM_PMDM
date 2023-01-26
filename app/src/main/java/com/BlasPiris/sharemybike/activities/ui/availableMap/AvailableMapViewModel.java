package com.BlasPiris.sharemybike.activities.ui.availableMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AvailableMapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AvailableMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is AVAILABLE BIKES MAP fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}