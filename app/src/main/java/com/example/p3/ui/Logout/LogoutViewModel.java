package com.example.p3.ui.Logout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public LogoutViewModel(){
        mText=new MutableLiveData<>();
        mText.setValue("This is Logout fragment");
    }
}