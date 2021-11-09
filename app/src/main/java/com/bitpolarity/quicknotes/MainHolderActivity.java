package com.bitpolarity.quicknotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;

import com.bitpolarity.quicknotes.MainActivity.MainNoteFragment;
import com.bitpolarity.quicknotes.NoteEditor.NoteEditorActivity;
import com.bitpolarity.quicknotes.databinding.ActivityMainHolderBinding;

public class MainHolderActivity extends AppCompatActivity {


    ActivityMainHolderBinding binding;
    Fragment mainNoteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainHolderBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main_holder);

        mainNoteFragment = MainNoteFragment.newInstance();

//        binding.actionbar.changegrid.setOnClickListener(view -> {
//                changeGrids();
//        });


        binding.actionbar2.floatingActionButton.setOnClickListener(view -> {
            startActivityForResult(new Intent(this, NoteEditorActivity.class), 100);
        });

        binding.actionbar2.floatingActionButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_fadein));
        loadFragment(mainNoteFragment);

    }


    void loadFragment(Fragment fragment){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(binding.fragmentHolder.getId() , fragment);
        fragmentTransaction.commit();

    }




}
