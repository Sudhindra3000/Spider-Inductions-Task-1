package com.sudhindra.spider_inductions_task_1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sudhindra.spider_inductions_task_1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}