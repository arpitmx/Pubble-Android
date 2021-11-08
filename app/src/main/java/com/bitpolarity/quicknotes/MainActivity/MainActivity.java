package com.bitpolarity.quicknotes.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bitpolarity.quicknotes.MainActivity.adapter.NoteAdapter;
import com.bitpolarity.quicknotes.db.DBManager;
import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;
import com.bitpolarity.quicknotes.databinding.ActivityMainBinding;
import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements NoteAdapter.ULEventListner   {

    ActivityMainBinding binding;
    private NoteAdapter noteAdapter;
    DBManager dbManager;
    AppDatabase db;
    RecyclerView recyclerView;
    int size = 0;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<Note> noteList;
    MainActivityViewModel MVM;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        recyclerView = binding.notesRV;
        preferences = getSharedPreferences("grid_db", MODE_PRIVATE);
        int last_saved_grid = getLastGrid();

        dbManager =  new ViewModelProvider(this).get(DBManager.class);
        MVM = new ViewModelProvider(this, new MainActivityViewModelFactory(last_saved_grid, getApplication())).get(MainActivityViewModel.class);

        noteList = dbManager.getNoteList();
        db = dbManager.getDb();


        binding.actionbar.changegrid.setOnClickListener(view -> {
                changeGrids();
        });
        binding.actionbar2.floatingActionButton.setOnClickListener(view -> {

            startActivityForResult(new Intent(this, NoteEditorActivity.class), 100);

        });


        initRecyclerView();
        loadNoteList(noteList);

    }


    int getLastGrid(){
        return preferences.getInt("last_grid",3);
    }

    private void initRecyclerView(){
        int g = MVM.getGrids();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(g, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);
        noteAdapter = new NoteAdapter(this, this);
        recyclerView.setAdapter(noteAdapter);
    }

    public void loadNoteList(List<Note> noteList){

        if (noteList.size() > 0) {
            noteAdapter.setNoteList(noteList);
            binding.notesRV.setVisibility(View.VISIBLE);
            binding.emptyTxt.setVisibility(View.GONE);

        }else {
            binding.notesRV.setVisibility(View.GONE);
            binding.emptyTxt.setVisibility(View.VISIBLE);
        }
    }


    void changeGrids(){
            staggeredGridLayoutManager.setSpanCount(MVM.changeGrid());
    }

    boolean checkIfUpdated(List<Note> noteList){

        if (size != noteList.size()){
            size = noteList.size();
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        noteList = dbManager.getNoteList();
            loadNoteList(noteList);
            recyclerView.scrollToPosition(noteList.size()-1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode ==100){
           loadNoteList(noteList);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra("data",position);
         startActivity(intent);
    }



}