package com.BlasPiris.sharemybike.activities.ui.registerBike;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterBikeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RegisterBikeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is REGISTER BIKES fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}