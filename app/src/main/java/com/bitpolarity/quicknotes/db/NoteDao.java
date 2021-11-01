package com.bitpolarity.quicknotes.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note")
    List<Note> getAllNotes();

    @Insert
    void insertNote(Note...notes);

    @Delete
    void deleteNote(Note note);

    @Query("UPDATE Note SET title = :new_title , `desc` = :new_desc WHERE note_id == :nid")
    void update(int nid , String new_title, String new_desc );





}
