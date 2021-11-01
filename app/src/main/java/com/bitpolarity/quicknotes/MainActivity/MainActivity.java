package com.bitpolarity.quicknotes.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bitpolarity.quicknotes.MainActivity.adapter.NoteAdapter;
import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;
import com.bitpolarity.quicknotes.databinding.ActivityMainBinding;
import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements NoteAdapter.ULEventListner {

    ActivityMainBinding binding;
    private NoteAdapter noteAdapter;
    MainActivityViewHolder mainActivityViewHolder;
    AppDatabase db;
    RecyclerView recyclerView;
    int size = 0;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<Note> noteList;
    int grids= 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //startService(new Intent(getApplicationContext(), LockService.class));


        recyclerView = binding.notesRV;
        noteList = AppDatabase.getDbInstance(this).noteDao().getAllNotes();

        db = AppDatabase.getDbInstance(this.getApplicationContext());
        mainActivityViewHolder=  new ViewModelProvider(this).get(MainActivityViewHolder.class);

      //  binding.actionbar.textView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_in));


        binding.actionbar.changegrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(grids==2){
                    grids =1;
                staggeredGridLayoutManager.setSpanCount(grids);
            }else{
                    grids=2;
                staggeredGridLayoutManager.setSpanCount(grids);
                }
            }
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, NoteEditorActivity.class), 100);
        });




        initRecyclerView();
        loadNoteList(noteList);
    }

    private void initRecyclerView(){

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(grids, StaggeredGridLayoutManager.VERTICAL);
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

        noteList = mainActivityViewHolder.getList(db);
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