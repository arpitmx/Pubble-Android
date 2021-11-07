package com.bitpolarity.quicknotes.MainActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    int grids;
    Application application;

    MainActivityViewModelFactory(int grids,Application application){
        this.grids = grids;
        this.application = application;

    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == MainActivityViewModel.class){
                return (T) new MainActivityViewModel(grids, application);
            }
            return null;
    }
}
