package com.bitpolarity.quicknotes.MainActivity;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitpolarity.quicknotes.MainActivity.adapter.NoteAdapter;
import com.bitpolarity.quicknotes.R;
import com.bitpolarity.quicknotes.databinding.MainNoteFragmentBinding;
import com.bitpolarity.quicknotes.db.DBManager;
import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;
import com.bitpolarity.quicknotes.db.AppDatabase;
import com.bitpolarity.quicknotes.db.Note;

import java.util.ArrayList;
import java.util.List;

public class MainNoteFragment extends Fragment implements NoteAdapter.ULEventListner   {

    MainNoteFragmentBinding binding;
    private NoteAdapter noteAdapter;
    DBManager dbManager;
    AppDatabase db;
    RecyclerView recyclerView;

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    List<Note> noteList;
    MainActivityViewModel MVM;
    SharedPreferences preferences;
    Animation hintanim;
    Context context;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public MainNoteFragment(){
    }

    public static MainNoteFragment newInstance() {
        return new MainNoteFragment();
    }

    void setinitList(){
        noteList = dbManager.getNoteList();
        loadNoteList(noteList);
        staggeredGridLayoutManager.setReverseLayout(true);
        recyclerView.scrollToPosition(noteList.size()-1);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MainNoteFragmentBinding.inflate(getLayoutInflater());
        hintanim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = context.getSharedPreferences("grid_db", MODE_PRIVATE);
        int last_saved_grid = getLastGrid();

        dbManager =  new ViewModelProvider(this).get(DBManager.class);
        MVM = new ViewModelProvider(this, new MainActivityViewModelFactory(last_saved_grid, getActivity().getApplication())).get(MainActivityViewModel.class);

        noteList = dbManager.getNoteList();
        db = dbManager.getDb();



    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.mainNoteRv;

        initRecyclerView();
        loadNoteList(noteList);

    }

    int getLastGrid(){
        return preferences.getInt("last_grid",2);
    }

    private void initRecyclerView(){
        int g = MVM.getGrids();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(g, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.hasFixedSize();
        recyclerView.setNestedScrollingEnabled(false);
        noteAdapter = new NoteAdapter(context, this);
        recyclerView.setAdapter(noteAdapter);
    }

    public void loadNoteList(List<Note> noteList){

        if (noteList.size() > 0) {
            noteAdapter.setNoteList(noteList);
            binding.mainNoteRv.setVisibility(View.VISIBLE);
            binding.emptyTxt.setVisibility(View.GONE);

        }else {
            recyclerView.setVisibility(View.GONE);
            binding.emptyTxt.setVisibility(View.VISIBLE);
        }
    }

    public  void changeGrids(){
        staggeredGridLayoutManager.setSpanCount(MVM.changeGrid());
    }

//    boolean checkIfUpdated(List<Note> noteList){
//
//        if (size != noteList.size()){
//            size = noteList.size();
//            return true;
//        }else{
//            return false;
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();

        noteList = dbManager.getNoteList();
            loadNoteList(noteList);
            recyclerView.scrollToPosition(noteList.size()-1);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode ==100){
           loadNoteList(noteList);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(context, NoteEditorActivity.class);
        intent.putExtra("data",position);
         startActivity(intent);
    }



}