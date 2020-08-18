package com.sudhindra.spider_inductions_task_1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.sudhindra.spider_inductions_task_1.utils.NotificationUtil;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        switch (intent.getIntExtra(NotificationUtil.NOTIFICATION_ACTION, -1)) {
            case NotificationUtil.ACTION_DISMISS_ALARM:
                notificationManager.cancel(NotificationUtil.ALARM_NOTIFICATION_ID);
                break;
            default:
                Toast.makeText(context, "Unidentified Notification Action from Time Manager", Toast.LENGTH_SHORT).show();
        }
    }
}
