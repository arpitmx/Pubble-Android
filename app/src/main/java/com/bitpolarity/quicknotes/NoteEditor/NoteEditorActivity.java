package com.bitpolarity.quicknotes.NoteEditor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitpolarity.quicknotes.R;
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


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            update_through_watcher(charSequence.toString(), desc.getText().toString(), newnote);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {

       // overridePendingTransition(R.anim.slide_up, R.anim.pop_out);

        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        title.addTextChangedListener(textWatcher);

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
        }else{
        saveNewNote(titext,descText, newnote);
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


        if(newnote) {
            Note new_note = new Note();
            new_note.title = title;
            new_note.desc = desc;
            db.noteDao().insertNote(new_note);
            Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
            finish();
        }else{

            String old_title = note.title;
            String old_note = note.desc;

            if(!old_title.equals(title) || !old_note.equals(desc)){
                //Toast.makeText(this, "this is an Updated note ", Toast.LENGTH_SHORT).show();
//                note.title = title;
//                note.desc = desc;
                db.noteDao().update(note.note_id , title, desc);
                finish();
            }else{
                //Toast.makeText(this, "No update was made , same note.", Toast.LENGTH_SHORT).show();
                finish();
            }

        }
}



    private void update_through_watcher(String title, String desc, boolean newnote) {

        if(!newnote) {
            db.noteDao().update(note.note_id, title, desc);

        }
        }
    }



