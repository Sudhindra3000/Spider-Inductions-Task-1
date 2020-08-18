package com.sudhindra.spider_inductions_task_1.models;

public class Alarm {

    public static final String ALARM_STRING = "alarmString";

    private String title;
    private boolean on;
    private int hour, minute;

    public Alarm(String title, boolean on, int hour, int minute) {
        this.title = title;
        this.on = on;
        this.hour = hour;
        this.minute = minute;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
