package com.sudhindra.spider_inductions_task_1.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sudhindra.spider_inductions_task_1.R;
import com.sudhindra.spider_inductions_task_1.databinding.FragmentStopwatchBinding;
import com.sudhindra.spider_inductions_task_1.utils.StopwatchUtil;

public class StopwatchFragment extends Fragment {

    private FragmentStopwatchBinding binding;

    private StopwatchUtil stopwatchUtil;
    private StopwatchUtil.TimerCallback callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStopwatchBinding.inflate(inflater, container, false);

        binding.startPauseBt.setOnClickListener(this::startPause);
        binding.resetBt.setOnClickListener(this::reset);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.stopwatchToolbar);
        binding.stopwatchToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        binding.setS1("00");
        binding.setS2("000");

        callback = new StopwatchUtil.TimerCallback() {
            @Override
            public void updateTimer(String s1, String s2) {
                binding.setS1(s1);
                binding.setS2(s2);
            }

            @Override
            public void lap() {

            }
        };
        stopwatchUtil = new StopwatchUtil(callback);
    }

    private void startPause(View view) {
        binding.resetBt.setVisibility(View.VISIBLE);
        if (stopwatchUtil.running) {
            binding.startPauseBt.setImageResource(R.drawable.play_icon);
            stopwatchUtil.pause();
        } else {
            binding.startPauseBt.setImageResource(R.drawable.pause_icon);
            stopwatchUtil.start();
        }
    }

    private void reset(View view) {
        binding.resetBt.setVisibility(View.GONE);
        binding.startPauseBt.setImageResource(R.drawable.play_icon);
        stopwatchUtil.reset();
        binding.setS1("00");
        binding.setS2("000");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}