package com.sudhindra.spider_inductions_task_1.models;

public class MenuItem {

    private int resId;
    private String name;

    public MenuItem(int resId, String name) {
        this.resId = resId;
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }
}
