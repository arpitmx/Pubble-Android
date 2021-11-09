package com.bitpolarity.quicknotes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;
import com.bitpolarity.quicknotes.databinding.FragmentNoteSearchBinding;
import com.bitpolarity.quicknotes.db.Note;

import java.util.ArrayList;
import java.util.List;

public class SearchNoteFragment extends Fragment {


    FragmentNoteSearchBinding binding;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    //    void filterResult(String str) {
//
//        Log.d("filter", str );
//        List<Note> filteredList = new ArrayList<>();
//        if (str.equals("")) {
//            setinitList();
//
//        } else {
//
//            for (Note i : noteList) {
//                if (i.title.toLowerCase().trim().contains(str) || i.desc.toLowerCase().trim().contains(str)) {
//                    filteredList.add(i);
//                }
//            }
//
//            if (filteredList.size()>0){
//                noteAdapter.setNoteList(filteredList);
//                recyclerView.scrollToPosition(0);
//                staggeredGridLayoutManager.setReverseLayout(false);
//                binding.emptyTxt.setVisibility(View.INVISIBLE);
//                binding.notesRV.setVisibility(View.VISIBLE);
//            }else{
//
//                binding.emptyTxt.setText("No note found... :(");
//                binding.emptyTxt.setVisibility(View.VISIBLE);
//                binding.notesRV.setVisibility(View.INVISIBLE);
//            }
//        }
//    }
//



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
                binding.actionbar2.calcClearTxtPrise.setVisibility(View.VISIBLE);
                binding.actionbar2.notesSearchbar.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);

            }else{
                binding.actionbar2.calcClearTxtPrise.setVisibility(View.INVISIBLE);
                binding.actionbar2.notesSearchbar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_search_24,0,0,0);
            }
           // filterResult(editable.toString().toLowerCase().trim());

        }
    };



    public SearchNoteFragment() {
        // Required empty public constructor
    }

    public static SearchNoteFragment newInstance() {
        return new SearchNoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNoteSearchBinding.inflate(inflater,container, false);

        binding.actionbar2.notesSearchbar.addTextChangedListener(searchWatcher);

        binding.actionbar2.calcClearTxtPrise.setOnClickListener(view -> {
            binding.actionbar2.notesSearchbar.getText().clear();
        });



        binding.actionbar2.notesSearchbar.setOnTouchListener((v, event) -> {
            if(MotionEvent.ACTION_UP == event.getAction())
                Toast.makeText(getActivity().getApplicationContext(), "onTouch: Up", Toast.LENGTH_SHORT).show();
            return false;
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}