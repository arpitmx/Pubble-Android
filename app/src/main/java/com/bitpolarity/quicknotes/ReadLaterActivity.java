package com.bitpolarity.quicknotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bitpolarity.quicknotes.databinding.ActivityNoteAdditionBinding;
import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ReadLaterActivity extends AppCompatActivity {

    ActivityNoteAdditionBinding binding;
    String titlestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteAdditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        showBottomSheet(text.toString());



    }


    void showBottomSheet(String note){

        final BottomSheetDialog bottomSheetDialog= new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_addnotes);

        AppCompatButton addbtn= bottomSheetDialog.findViewById(R.id.bottomsheet_addnote_button);
        EditText title = bottomSheetDialog.findViewById(R.id.bottomsheet_title_tv);


        assert addbtn != null;
        addbtn.setOnClickListener(view -> {

            assert title != null;
            titlestr = title.getText().toString();
            saveNewNote(titlestr, note);
        });

        bottomSheetDialog.show();


    }


    private void saveNewNote(String title, String desc){
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        Note note = new Note();

        note.title = title;
        note.desc = desc;
        db.noteDao().insertNote(note);
        Toast.makeText(this, "Added to read later", Toast.LENGTH_SHORT).show();
        finish();
    }
}