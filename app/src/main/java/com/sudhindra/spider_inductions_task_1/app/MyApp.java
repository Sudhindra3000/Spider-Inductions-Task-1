package com.sudhindra.spider_inductions_task_1.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class MyApp extends Application {

    public static final String ALARM_CHANNEL_ID = "com.sudhindra.spider_inductions_task_1.alarmChannel";
    private static final String ALARM_CHANNEL_NAME = "Alarms";
    private static final String ALARM_CHANNEL_DESCRIPTION = "Alarm Notifications";

    @Override
    public void onCreate() {
        super.onCreate();
        createChannels();
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel alarmChannel = new NotificationChannel(ALARM_CHANNEL_ID, ALARM_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            alarmChannel.setDescription(ALARM_CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(alarmChannel);
        }
    }
}
