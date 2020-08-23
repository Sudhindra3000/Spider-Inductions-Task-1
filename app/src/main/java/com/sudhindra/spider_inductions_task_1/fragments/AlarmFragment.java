package com.sudhindra.spider_inductions_task_1.fragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.sudhindra.spider_inductions_task_1.R;
import com.sudhindra.spider_inductions_task_1.databinding.FragmentAlarmBinding;
import com.sudhindra.spider_inductions_task_1.models.Alarm;
import com.sudhindra.spider_inductions_task_1.utils.AlarmUtil;

public class AlarmFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private FragmentAlarmBinding binding;

    // UI
    private Animation alarmEnterAnim, alarmExitAnim;

    // Saving Alarm locally
    private Gson gson;
    private SharedPreferences sharedPreferences;

    // Alarm Logic
    private AlarmUtil alarmUtil;
    private Alarm alarm;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmEnterAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.alarm_enter_anim);
        alarmExitAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.alarm_exit_anim);

        gson = new Gson();
        sharedPreferences = requireContext().getSharedPreferences("AlarmPref", Context.MODE_PRIVATE);

        alarmUtil = new AlarmUtil(requireContext(), this);
        String alarmJson = sharedPreferences.getString("Alarm", "");
        if (alarmJson != null && !alarmJson.isEmpty())
            alarm = gson.fromJson(alarmJson, Alarm.class);
        else
            alarm = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAlarmBinding.inflate(inflater, container, false);

        binding.alarmCard.setOnClickListener(this::showOrHideAlarmSettings);
        binding.expandAlarmBt.setOnClickListener(this::showOrHideAlarmSettings);
        // Todo: Add Option to Edit Time
//        binding.alarmTimeTv.setOnClickListener(this::onAlarmTimeClicked);
        binding.setAlarmBt.setOnClickListener(this::newAlarm);
        binding.deleteAlarmBt.setOnClickListener(this::deleteAlarm);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.alarmToolbar);
        binding.alarmToolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        if (alarm != null) {
            binding.noAlarmTv.setVisibility(View.GONE);
            binding.setAlarmBt.hide();
            binding.alarmCard.setVisibility(View.VISIBLE);
            binding.setAlarm(alarm);
        }
    }

    private void onAlarmTimeClicked(View view) {
        alarmUtil.showTimePickerDialog(alarm);
    }

    private void showOrHideAlarmSettings(View view) {
        if (binding.deleteAlarmBt.getVisibility() == View.GONE) {
            binding.deleteAlarmBt.setVisibility(View.VISIBLE);
            binding.expandAlarmBt.setImageResource(R.drawable.up_arrrow_icon);
        } else {
            binding.deleteAlarmBt.setVisibility(View.GONE);
            binding.expandAlarmBt.setImageResource(R.drawable.down_arrow_icon);
        }
    }

    private void newAlarm(View view) {
        alarmUtil.showTimePickerDialog();
    }

    private void deleteAlarm(View view) {
        binding.alarmCard.startAnimation(alarmExitAnim);
        binding.alarmCard.setVisibility(View.GONE);
        binding.deleteAlarmBt.setVisibility(View.GONE);
        binding.expandAlarmBt.setImageResource(R.drawable.down_arrow_icon);
        binding.noAlarmTv.setVisibility(View.VISIBLE);
        binding.setAlarmBt.show();
        sharedPreferences.edit().remove("Alarm").apply();
        alarmUtil.deleteAlarm(alarm);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int h, int m) {
        if (alarm == null) {
            alarm = new Alarm("Alarm", true, h, m);
        } else {
            alarm.setHour(h);
            alarm.setMinute(m);
        }
        if (binding.setAlarmBt.getVisibility() == View.VISIBLE) {
            binding.setAlarmBt.hide();
            binding.noAlarmTv.setVisibility(View.GONE);
            binding.alarmCard.startAnimation(alarmEnterAnim);
            binding.alarmCard.setVisibility(View.VISIBLE);
            sharedPreferences.edit().putString("Alarm", gson.toJson(alarm, Alarm.class)).apply();
            alarmUtil.setAlarm(alarm);
        }
        binding.setAlarm(alarm);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}