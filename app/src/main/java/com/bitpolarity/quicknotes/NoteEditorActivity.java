package com.bitpolarity.quicknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitpolarity.quicknotes.databinding.ActivityNoteEditorBinding;
import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NoteEditorActivity extends AppCompatActivity {

    ActivityNoteEditorBinding binding;
    EditText title;
    EditText desc;
    FloatingActionButton saveBtn;
    AppDatabase db;
    Boolean newnote = true;
    Note note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.getRoot());

        db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Note> noteList = db.noteDao().getAllNotes();


        title = binding.titleET;
        desc  = binding.NoteET;
        saveBtn = binding.saveBTN;

        int position= getIntent().getIntExtra("data", -1);

        if (position != -1){

            note = noteList.get(position);
            title.setText(note.title);
            desc.setText(note.desc);
            newnote = false;

        }else {
            reqFocus(desc);
        }

        binding.actionbarEditor.deletebutton.setOnClickListener(view -> {
            deleteNote(note);
        });

        saveBtn.setOnClickListener(view -> {

            String titext = title.getText().toString();
            String descText = desc.getText().toString();

            if (titext.length()+descText.length() >0) {
                saveNewNote(titext, descText,newnote);
            }else{
                Toast.makeText(this, "Empty note, discarded.", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        binding.actionbarEditor.backbutton.setOnClickListener(view -> {

        onBackPressed();


        });





    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String titext = title.getText().toString();
        String descText = desc.getText().toString();

        if (titext.length()+descText.length()==0){
            Toast.makeText(this, "Empty note discarded", Toast.LENGTH_SHORT).show();
        }

    }



    void reqFocus(View view){
            view.requestFocus();
    }

    private void deleteNote(Note note){


        if (!newnote && note != null){
            db.noteDao().deleteNote(note);
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            finish();
        }

    }

    private void saveNewNote(String title, String desc, boolean newnote){

        Note note = new Note();
        note.title = title;
        note.desc = desc;
        db.noteDao().insertNote(note);
        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
        finish();




}
}