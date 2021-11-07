package com.bitpolarity.quicknotes.MainActivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    public void setNotesList(ArrayList<Note> notes){
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.existing_note_row , parent , false));
    }


    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
           holder.title.setText(notes.get(position).title);
           holder.desc.setText(notes.get(position).desc);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView desc;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.existing_title);
            desc = itemView.findViewById(R.id.existing_desc);


        }
    }




}
