package com.sudhindra.spider_inductions_task_1.utils;

import android.os.Handler;
import android.os.SystemClock;

public class StopwatchUtil {

    public boolean running;
    private Handler handler = new Handler();
    private long startTime = 0L, timeInMillis = 0L, timeSwapBuff = 0L, updateTime = 0L;

    private TimerCallback callback;

    public interface TimerCallback {
        void updateTimer(String s1, String s2);

        void lap();
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMillis;
            int millis = (int) (updateTime % 1000);
            int sec = (int) (updateTime / 1000);
            int min = sec / 60;
            if (sec >= 60) sec = sec % 60;
            callback.updateTimer(getS1(min, sec), getS2(millis));
            handler.postDelayed(this, 0);
        }
    };

    public StopwatchUtil(TimerCallback callback) {
        this.callback = callback;
    }

    public void start() {
        running = true;
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(updateTimerThread, 0);
    }

    public void pause() {
        running = false;
        timeSwapBuff += timeInMillis;
        handler.removeCallbacks(updateTimerThread);
    }

    public void reset() {
        startTime = 0L;
        timeInMillis = 0L;
        timeSwapBuff = 0L;
        updateTime = 0L;
        running = false;
        handler.removeCallbacks(updateTimerThread);
    }

    private String getS1(int min, int sec) {
        String mStr = min >= 10 ? String.valueOf(min) : "0" + min;
        String sStr = sec >= 10 ? String.valueOf(sec) : "0" + sec;
        return min > 0 ? mStr + ":" + sStr : sStr;
    }

    private String getS2(int millis) {
        return millis >= 100 ? String.valueOf(millis) : (millis >= 10 ? "0" + millis : "00" + millis);
    }
}
