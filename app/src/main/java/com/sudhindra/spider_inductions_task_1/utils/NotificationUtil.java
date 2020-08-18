package com.sudhindra.spider_inductions_task_1.utils;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.sudhindra.spider_inductions_task_1.R;
import com.sudhindra.spider_inductions_task_1.app.MyApp;
import com.sudhindra.spider_inductions_task_1.models.Alarm;
import com.sudhindra.spider_inductions_task_1.receivers.AlertReceiver;
import com.sudhindra.spider_inductions_task_1.receivers.NotificationReceiver;

public class NotificationUtil {

    public static final int ALARM_NOTIFICATION_ID = 0;

    public static final String NOTIFICATION_ACTION = "notificationAction";
    public static final int ACTION_DISMISS_ALARM = 10;

    private Context context;
    private NotificationManagerCompat notificationManager;

    public NotificationUtil(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);
    }

    public void handleAlert(Intent intent) {
        Alarm alarm = new Gson().fromJson(intent.getStringExtra(Alarm.ALARM_STRING), Alarm.class);
        switch (intent.getIntExtra(AlertReceiver.ALERT_TYPE, -1)) {
            case AlertReceiver.ALERT_ALARM:
                sendAlarmNotification(alarm);
                break;
            default:
                Toast.makeText(context, "Unidentified Alert from Time Manager", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendAlarmNotification(Alarm alarm) {
        String title = alarm.getTitle();
        String body = AlarmUtil.getAlarmTime(alarm);

        Intent broadcastIntent = new Intent(context, NotificationReceiver.class);
        broadcastIntent.putExtra(NOTIFICATION_ACTION, ACTION_DISMISS_ALARM);
        PendingIntent actionIntent = PendingIntent.getBroadcast(context, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context, MyApp.ALARM_CHANNEL_ID)
                .setSmallIcon(R.drawable.alarm_icon)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Dismiss", actionIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        notificationManager.notify(ALARM_NOTIFICATION_ID, notification);
    }
}
