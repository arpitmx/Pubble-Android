package com.bitpolarity.quicknotes.MainActivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitpolarity.quicknotes.R;
import com.bitpolarity.quicknotes.db.Note;

import java.util.List;

public class ExistingNoteAdapter extends RecyclerView.Adapter<ExistingNoteAdapter.NotesViewHolder> {

    List<Note> noteList;
    Context context;

    int lastPosition = Integer.MAX_VALUE;


    public ExistingNoteAdapter(Context context){
        this.context = context;
    }

    public void setNotesList(List<Note> notes){
        this.noteList = notes;
        notifyDataSetChanged();


    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.existing_note_row , parent , false));
    }


    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if (!noteList.get(position).title.isEmpty()){
            holder.title.setVisibility(View.VISIBLE);
            holder.title.setText(noteList.get(position).title);
            holder.desc.setText(noteList.get(position).desc);
        }else{
            holder.title.setVisibility(View.GONE);
            holder.desc.setText(noteList.get(position).desc);
            holder.desc.setTextSize(16f);
        }

       // setAnimation(holder.itemView,position);


    }

    @Override
    public int getItemCount() {
        return noteList.size();
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


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position < lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }




}
