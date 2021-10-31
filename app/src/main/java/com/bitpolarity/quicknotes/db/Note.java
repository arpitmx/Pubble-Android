package com.bitpolarity.quicknotes.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int note_id;

    @ColumnInfo(name = "title")
    public String title;

   @ColumnInfo(name = "desc")
    public String desc ;
}
