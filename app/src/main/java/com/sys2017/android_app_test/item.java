package com.sys2017.android_app_test;

/**
 * Created by N552VW on 2017-06-19.
 */

public class item {

    String title;
    String data;
    String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String desc) {
        this.data = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public item(String title, String data, String date) {

        this.title = title;
        this.data = data;
        this.date = date;
    }
}
