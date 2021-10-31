package com.bitpolarity.quicknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bitpolarity.quicknotes.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




    }
}