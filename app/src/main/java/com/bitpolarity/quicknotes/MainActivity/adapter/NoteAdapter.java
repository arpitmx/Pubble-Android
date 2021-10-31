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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {


    private Context context;
    private List<Note> noteList;
    private ULEventListner ul;
    int lastPosition = Integer.MAX_VALUE;


    public NoteAdapter(Context context, ULEventListner ul){
        this.context=  context;
        this.ul = ul;
    }

    public void setNoteList(List<Note> noteList){
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.note_row, parent , false);

        return new NoteVH(view,ul);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
            holder.title.setText(noteList.get(position).title);
            holder.desc.setText(noteList.get(position).desc);
            setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }



    class NoteVH extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView desc;
        ULEventListner ulEventListner;

        public NoteVH(@NonNull View itemView,ULEventListner ulEventListner) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            this.ulEventListner = ulEventListner;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            ulEventListner.onClick(getAdapterPosition());
        }
    }

    public interface ULEventListner{
        void onClick(int position);
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
