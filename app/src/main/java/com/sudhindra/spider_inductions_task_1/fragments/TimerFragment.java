package com.sudhindra.spider_inductions_task_1.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.sudhindra.spider_inductions_task_1.R;
import com.sudhindra.spider_inductions_task_1.databinding.FragmentTimerBinding;

import java.util.Calendar;

public class TimerFragment extends Fragment implements NumberPicker.OnScrollListener {

    private static final String TAG = "TimerFragment";
    private FragmentTimerBinding binding;

    private CountDownTimer countDownTimer;
    private int s = 0, m = 0, h = 0;

    private boolean running = false;
    private long timeLeftInMillis = 0, totalTime = 0;
    private int numberOfSeconds = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimerBinding.inflate(inflater, container, false);

        binding.secondsPicker.setMinValue(0);
        binding.secondsPicker.setMaxValue(60);
        binding.minutesPicker.setMinValue(0);
        binding.minutesPicker.setMaxValue(60);
        binding.hoursPicker.setMinValue(0);
        binding.hoursPicker.setMaxValue(24);

        binding.secondsPicker.setOnScrollListener(this);
        binding.minutesPicker.setOnScrollListener(this);
        binding.hoursPicker.setOnScrollListener(this);

        binding.resetBt.setOnClickListener(this::reset);
        binding.startPauseBt.setOnClickListener(this::startPause);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.timerToolbar);
        binding.timerToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void startPause(View view) {
        if (!running) {
            binding.startPauseBt.setImageResource(R.drawable.pause_icon);
            binding.resetBt.setVisibility(View.VISIBLE);
            if (timeLeftInMillis == 0) {
                timeLeftInMillis = getSetTimeInMillis();
                totalTime = timeLeftInMillis;
                numberOfSeconds = (int) (totalTime / 1000);
                binding.hoursPicker.setVisibility(View.GONE);
                binding.minutesPicker.setVisibility(View.GONE);
                binding.secondsPicker.setVisibility(View.GONE);
                binding.textView2.setVisibility(View.GONE);
                binding.textView3.setVisibility(View.GONE);
                binding.textView4.setVisibility(View.GONE);
                binding.timerPb.setVisibility(View.VISIBLE);
            }
            startTimer();
        } else {
            binding.startPauseBt.setImageResource(R.drawable.play_icon);
            pauseTimer();
        }
        running = !running;
    }

    private void reset(View view) {
        binding.resetBt.setVisibility(View.GONE);
        binding.startPauseBt.setImageResource(R.drawable.play_icon);
        s = 0;
        m = 0;
        h = 0;
        timeLeftInMillis = 0;
        running = false;
        binding.hoursPicker.setVisibility(View.VISIBLE);
        binding.minutesPicker.setVisibility(View.VISIBLE);
        binding.secondsPicker.setVisibility(View.VISIBLE);
        binding.textView2.setVisibility(View.VISIBLE);
        binding.textView3.setVisibility(View.VISIBLE);
        binding.textView4.setVisibility(View.VISIBLE);
        binding.timerPb.setVisibility(View.GONE);
        resetTimer();
    }

    private long getSetTimeInMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, h);
        calendar.set(Calendar.MINUTE, m);
        calendar.set(Calendar.SECOND, s);
        return calendar.getTimeInMillis();
    }

    // Timer Logic
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                int progressPercentage = (numberOfSeconds - secondsRemaining) * (100 / numberOfSeconds);
                Log.i(TAG, "onTick: " + progressPercentage);
                binding.timerPb.setProgress(progressPercentage);
//                updateTimePb();
            }

            @Override
            public void onFinish() {
                running = false;
//                mButtonStartPause.setText("Start");
//                mButtonStartPause.setVisibility(View.INVISIBLE);
//                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        running = false;
    }

    private void resetTimer() {
//        timeLeftInMillis = 0;
//        totalTime = 0;
//        updateTimePb();
    }

    private void updateTimePb() {
        int progress = (int) (timeLeftInMillis * 100 / totalTime);
        Log.i(TAG, "updateTimePb: " + progress);
        binding.timerPb.setProgress(progress);
    }

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {
        switch (numberPicker.getTag().toString()) {
            case "S":
                s = i;
                break;
            case "M":
                m = i;
                break;
            case "H":
                h = i;
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}