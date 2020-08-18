package com.sudhindra.spider_inductions_task_1.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sudhindra.spider_inductions_task_1.utils.NotificationUtil;

public class AlertReceiver extends BroadcastReceiver {

    public static final String ALERT_TYPE = "alertType";
    public static final int ALERT_ALARM = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        new NotificationUtil(context).handleAlert(intent);
    }
}
