package com.BlasPiris.sharemybike.activities.ui.dateSelection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DateSelectionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DateSelectionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is DATE SELECTION fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}