package com.bitpolarity.quicknotes.db;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends AndroidViewModel {

    int NOTE_ADDED = 1;
    int NOTE_UPDATED =2;
    int NOTE_NO_CHANGE =3;


    AppDatabase db;
    List<Note> noteList;

    public DBManager(@NonNull Application application) {
        super(application);
        db = AppDatabase.getDbInstance(application.getApplicationContext());
        noteList = setNoteList(db);
    }


    public AppDatabase getDb(){
        return db;
    }

    private List<Note> setNoteList(AppDatabase db){
        return  db.noteDao().getAllNotes();
    }

   public List<Note> getNoteList(){
        return db.noteDao().getAllNotes();
    }

   public Note getNote(int position){
        return  noteList.get(position);
    }

    public int saveNewNote(Note note,String ntitle, String ndesc, boolean newnote){


        if(newnote) {
            Note new_note = new Note();
            new_note.title = ntitle;
            new_note.desc = ndesc;
            db.noteDao().insertNote(new_note);
            return NOTE_ADDED;

        }else{

            String old_title = note.title;
            String old_note = note.desc;

            if(!old_title.equals(ntitle) || !old_note.equals(ndesc)){
                //Toast.makeText(this, "this is an Updated note ", Toast.LENGTH_SHORT).show();
                note.title = ntitle;
                note.desc = ndesc;
                db.noteDao().update(note.note_id , ntitle, ndesc);
                return NOTE_UPDATED;
            }else{
                //Toast.makeText(this, "No update was made , same note.", Toast.LENGTH_SHORT).show();
                return NOTE_NO_CHANGE;
            }

        }



    }
}
