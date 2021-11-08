package com.bitpolarity.quicknotes.intents;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.bitpolarity.quicknotes.databinding.BottomsheetAddnotesBinding;
import com.bitpolarity.quicknotes.db.DBManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ReadLaterBottomSheet extends BottomSheetDialogFragment {

    BottomsheetAddnotesBinding binding;

    AppCompatButton addbtn;
    ImageButton editbtn;
    AppCompatButton cancelbtn ;
    EditText title ;
    EditText edit_area ;
    static String note;
    String titlestr;
    DBManager dbManager;

    boolean edited = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomsheetAddnotesBinding.inflate(inflater);

        addbtn = binding.bottomsheetAddnoteButton;
        editbtn = binding.editNoteButtonBottomSheet;
        cancelbtn = binding.bottomsheetCancelButton;
        title = binding.bottomsheetTitleTv;
        edit_area = binding.editNoteBottomSheet;
        dbManager = new ViewModelProvider(this).get(DBManager.class);
        setCancelable(false);
        return binding.getRoot();
    }



    public static ReadLaterBottomSheet newInstance(String notes) {
        ReadLaterBottomSheet frag = new ReadLaterBottomSheet();
        note = notes;
        return frag;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edit_area.setText(note);
        showKeyboard(title);



        binding.bottomsheetAddnoteButton.setOnClickListener(view1 -> {
            addNote();
        });

        binding.editNoteButtonBottomSheet.setOnClickListener(view12 -> {
            editNote();


        });

        binding.bottomsheetCancelButton.setOnClickListener(view13 -> {
            dismiss();
            getActivity().finish();
        });


    }


    void saveNewNote(String title, String desc){
        dbManager.quickSaveNote(title, desc);
        Toast.makeText(getContext() , "Added to read later", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    void addNote(){

        titlestr = title.getText().toString();
        if(edited){
            saveNewNote(titlestr, edit_area.getText().toString());
        }else{
            saveNewNote(titlestr, note);
        }
    }


    void editNote(){

        if(edit_area.getVisibility()==View.VISIBLE){
            edit_area.setVisibility(View.GONE);
        }else{
            assert edit_area != null;
            edit_area.setVisibility(View.VISIBLE);
            edit_area.requestFocus();
            edited = true;
        }
    }

    public void showKeyboard(View view){
//        view.requestFocus();
//        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);


        new Handler().postDelayed(() -> {

            title.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
            title.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
        }, 200);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
