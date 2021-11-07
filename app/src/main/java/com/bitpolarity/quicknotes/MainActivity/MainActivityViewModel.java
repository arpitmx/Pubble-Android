package com.bitpolarity.quicknotes.MainActivity;

import static android.content.Context.MODE_PRIVATE;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;

public class MainActivityViewModel extends AndroidViewModel {

    int grids;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    MainActivityViewModel(int grids, Application application){
        super(application);
        this.grids = grids;
        preferences = application.getApplicationContext().getSharedPreferences("griddb",MODE_PRIVATE);
        editor = preferences.edit();
    }

    public int changeGrid(){

        if (grids ==2){
            grids -= 1;
            editor.putInt("last_grid",grids);
            editor.commit();
            return grids;
        }else{
            grids += 1;
            editor.putInt("last_grid",grids);
            editor.commit();
            return grids;
        }
    }

    int getGrids(){
        return grids;
    }


}

