package com.bitpolarity.quicknotes.MainActivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.quicknotes.R;
import com.bitpolarity.quicknotes.db.Note;
import java.util.ArrayList;

public class ExistingNoteAdapter extends RecyclerView.Adapter<ExistingNoteAdapter.NotesViewHolder> {


    ArrayList<Note> notes;
    Context context;


    ExistingNoteAdapter(Context context , ArrayList<Note> notes){
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_row , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }




}
