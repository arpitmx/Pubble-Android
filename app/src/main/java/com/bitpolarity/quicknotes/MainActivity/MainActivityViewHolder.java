package com.bitpolarity.quicknotes.MainActivity;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;

import java.util.List;

public class MainActivityViewHolder extends ViewModel {


    List<Note> getList(AppDatabase db){
        return db.noteDao().getAllNotes();
    }




}



