package com.bitpolarity.quicknotes.intents;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.bitpolarity.quicknotes.databinding.ActivityNoteAdditionBinding;

public class  ReadLaterActivity extends FragmentActivity {

    ActivityNoteAdditionBinding binding;
    String titlestr;
    boolean edited = false;
    ReadLaterBottomSheet readLaterBottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteAdditionBinding.inflate(getLayoutInflater());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(binding.getRoot());
        CharSequence text = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);
        showBottomSheet(text.toString());
    }


    void showBottomSheet(String note){
       readLaterBottomSheet = ReadLaterBottomSheet.newInstance(note);
       readLaterBottomSheet.show(getSupportFragmentManager(),"e");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}