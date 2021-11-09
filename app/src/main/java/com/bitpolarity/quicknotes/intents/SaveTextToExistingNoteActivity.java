package com.bitpolarity.quicknotes.intents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitpolarity.quicknotes.MainActivity.adapter.ExistingNoteAdapter;
import com.bitpolarity.quicknotes.MainActivity.adapter.NoteAdapter;
import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;
import com.bitpolarity.quicknotes.R;
import com.bitpolarity.quicknotes.databinding.ActivitySaveToExistingNoteBinding;
import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.DBManager;
import com.bitpolarity.quicknotes.db.Note;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveTextToExistingNoteActivity extends FragmentActivity implements NoteAdapter.ULEventListner {

    DBManager dbManager;
    List<Note> noteList;
    AppDatabase db;
    private ExistingNoteAdapter noteAdapter;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ActivitySaveToExistingNoteBinding binding;



    TextWatcher searchWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length()>0){
                binding.calcClearTxtPrise.setVisibility(View.VISIBLE);
                binding.notesSearchbar.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

            }else{
                binding.calcClearTxtPrise.setVisibility(View.INVISIBLE);
                binding.notesSearchbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_search_24,0,0,0);
            }
            filterResult(editable.toString().toLowerCase().trim());

        }
    };


    void setinitList(){
        noteList = dbManager.getNoteList();
        loadNoteList(noteList);
        staggeredGridLayoutManager.setReverseLayout(true);
        recyclerView.scrollToPosition(noteList.size()-1);
        staggeredGridLayoutManager.setSpanCount(1);
    }


    void filterResult(String str) {

        Log.d("filter", str );
        List<Note> filteredList = new ArrayList<>();
        if (str.equals("")) {

            setinitList();

        } else {

            for (Note i : noteList) {
                if (i.title.toLowerCase().trim().contains(str) || i.desc.toLowerCase().trim().contains(str)) {
                    filteredList.add(i);
                }
            }

            if (filteredList.size()>0){
            noteAdapter.setNotesList(filteredList);
            recyclerView.scrollToPosition(0);
                staggeredGridLayoutManager.setSpanCount(2);
                staggeredGridLayoutManager.setReverseLayout(false);
                binding.notenotfound.setVisibility(View.INVISIBLE);
                binding.searchNotesRv.setVisibility(View.VISIBLE);
            }else{

                binding.notenotfound.setVisibility(View.VISIBLE);
                binding.searchNotesRv.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySaveToExistingNoteBinding.inflate(getLayoutInflater());

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.search_notes_rv);
        dbManager =  new ViewModelProvider(this).get(DBManager.class);
        noteList = dbManager.getNoteList();
        db = dbManager.getDb();

        binding.layoutLin.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        binding.notesSearchbar.addTextChangedListener(searchWatcher);
        binding.bottomsheetCancelButton.setOnClickListener(view -> {
            finish();
        });



        binding.calcClearTxtPrise.setOnClickListener(view -> {
            binding.notesSearchbar.getText().clear();
            setinitList();
        });


        initRecyclerView();
        loadNoteList(noteList);

    }


    private void initRecyclerView(){
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);
        noteAdapter = new ExistingNoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        noteList = dbManager.getNoteList();
        loadNoteList(noteList);
        recyclerView.scrollToPosition(noteList.size()-1);

    }

    public void loadNoteList(List<Note> noteList){
            noteAdapter.setNotesList(noteList);
        }


    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("data",position);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}