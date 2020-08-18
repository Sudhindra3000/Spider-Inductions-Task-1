package com.sudhindra.spider_inductions_task_1.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;

import com.google.gson.Gson;
import com.sudhindra.spider_inductions_task_1.R;
import com.sudhindra.spider_inductions_task_1.models.Alarm;
import com.sudhindra.spider_inductions_task_1.receivers.AlertReceiver;

import java.util.Calendar;

public class AlarmUtil {

    private static final int ALARM_REQUEST_CODE = 0;

    private Context context;
    private TimePickerDialog.OnTimeSetListener listener;

    private AlarmManager alarmManager;

    public AlarmUtil(Context context, TimePickerDialog.OnTimeSetListener listener) {
        this.context = context;
        this.listener = listener;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.TimePickerTheme, listener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(context));
        timePickerDialog.show();
    }

    public void showTimePickerDialog(Alarm alarm) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, R.style.TimePickerTheme, listener, alarm.getHour(), alarm.getMinute(), DateFormat.is24HourFormat(context));
        timePickerDialog.show();
    }

    public void setAlarm(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        calendar.set(Calendar.MINUTE, alarm.getMinute());
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra(Alarm.ALARM_STRING, new Gson().toJson(alarm));
        intent.putExtra(AlertReceiver.ALERT_TYPE, AlertReceiver.ALERT_ALARM);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void deleteAlarm(Alarm alarm) {
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    public static String getAlarmTime(Alarm alarm) {
        if (alarm != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
            calendar.set(Calendar.MINUTE, alarm.getMinute());
            calendar.set(Calendar.SECOND, 0);
            return java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(calendar.getTime());
        }
        return "";
    }
}
